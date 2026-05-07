package com.example.AutoScreenshotApplication.Service;

import com.example.AutoScreenshotApplication.Utility.ApplicationConstants;
import com.example.AutoScreenshotApplication.Model.ArchiveMetadata;
import com.example.AutoScreenshotApplication.Model.ScreenshotData;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ScreenshotService {
    AttachmentService attachmentService;
    ArchiveService archiveService;
    private Playwright playwright;
    private Browser browser;

    @PostConstruct
    public void init() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    private final List<String> citiesFrom = (Arrays.asList("Одесса", "Харків", "Київ", "Дніпро", "Тернопіль"));
    private final List<String> citiesWhere = (Arrays.asList("Київ", "Дніпро", "Одесса", "Харків", "Запоріжжя "));

    public String makeScreenShot(String url) {
        List<ScreenshotData> screenshots = new ArrayList<>();

        try (BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080))) {
            Page page = context.newPage();
            Page bookingPage = prepareBookingPage(page, url);
            bookingPage.bringToFront();

            for (int i = 0; i < citiesFrom.size(); i++) {
                String from = citiesFrom.get(i);
                String where = citiesWhere.get(i);
                if (i == 0) {
                    performFirstSearch(bookingPage, from, where);

                } else {
                    performSubsequentSearch(bookingPage, from, where);

                }
                screenshots.add(captureScreenshot(bookingPage));
            }
            ArchiveMetadata archiveMetadata = archiveService.createArchive(screenshots);
            attachmentService.createAttachment(archiveMetadata);

            return archiveMetadata.getFilePath();
        } catch (Exception e) {
            return "error";
        }
    }

    private Page prepareBookingPage(Page page, String url) {
        page.navigate(url);
        Page popup = page.waitForPopup(() -> page.click(ApplicationConstants.BOOKING_PAGE));
        popup.waitForLoadState(LoadState.DOMCONTENTLOADED);
        return popup;
    }

    private void fillCitySearchFields(Page page, String cityFrom, String cityWhere, boolean isSubsequent) {
        if (isSubsequent) {
            subSequentTypeTextIntoPlaceholder(page, ApplicationConstants.CITY_FROM, cityFrom);
            subSequentTypeTextIntoPlaceholder(page, ApplicationConstants.CITY_WHERE, cityWhere);
        } else {
            firstTypeTextIntoPlaceholder(page, ApplicationConstants.CITY_FROM, cityFrom);
            firstTypeTextIntoPlaceholder(page, ApplicationConstants.CITY_WHERE, cityWhere);
        }
    }

    private void firstTypeTextIntoPlaceholder(Page page, String placeHolder, String value) {
        page.mouse().click(0, 0);
        page.getByPlaceholder(placeHolder).click();
        page.keyboard().type(value, new Keyboard.TypeOptions().setDelay(ApplicationConstants.KEYBOARD_IMITATION_DELAY));
        page.waitForTimeout(ApplicationConstants.SHORT_DELAY);
        page.locator(ApplicationConstants.LIST_BOX).first().click();

    }

    private void subSequentTypeTextIntoPlaceholder(Page page, String placeHolder, String value) {

        page.getByPlaceholder(placeHolder).click();
        page.keyboard().type(value, new Keyboard.TypeOptions().setDelay(ApplicationConstants.KEYBOARD_IMITATION_DELAY));
        page.waitForTimeout(ApplicationConstants.SHORT_DELAY);
        page.locator(ApplicationConstants.LIST_BOX).first().click();
    }

    private void performFirstSearch(Page page, String from, String where) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForTimeout(ApplicationConstants.LONG_DELAY);
        fillCitySearchFields(page, from, where, ApplicationConstants.NON_SUBSEQUENT);
        page.click(ApplicationConstants.SELECTOR_SUBMIT);
        page.waitForTimeout(ApplicationConstants.LONG_DELAY);
        noSeatsPagePopup(page);
    }

    private void performSubsequentSearch(Page page, String from, String where) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForTimeout(ApplicationConstants.LONG_DELAY);
        page.click(ApplicationConstants.SELECTOR_EDIT);
        page.waitForTimeout(ApplicationConstants.LONG_DELAY);
        fillCitySearchFields(page, from, where, ApplicationConstants.SUBSEQUENT);
        page.click(ApplicationConstants.SELECTOR_SUBMIT);
        page.waitForTimeout(ApplicationConstants.LONG_DELAY);
        noSeatsPagePopup(page);
    }

    private void noSeatsPagePopup(Page page) {
        Locator noSeatPopup = page.locator(ApplicationConstants.SELECTOR_NO_SEATS);
        if (noSeatPopup.isVisible()) {
            noSeatPopup.click();
            page.waitForTimeout(ApplicationConstants.LONG_DELAY);
        }
        page.waitForTimeout(ApplicationConstants.LONG_DELAY);
    }

    private ScreenshotData captureScreenshot(Page page) {
        page.waitForTimeout(ApplicationConstants.LONG_DELAY);
        page.evaluate("window.stop()");
        byte[] bytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        return new ScreenshotData("scr" + System.currentTimeMillis() + ".png", bytes);
    }


    @PreDestroy
    public void cleanup() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }


}


