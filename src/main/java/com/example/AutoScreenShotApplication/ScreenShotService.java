package com.example.AutoScreenShotApplication;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ScreenShotService {

    private static final String SELECTOR_SUBMIT = "button:has-text('Знайти')";
    private static final String SELECTOR_EDIT = "button:has-text('Редагувати пошук')";
    private static final String SELECTOR_NO_SEATS = "button:has-text('без вільних місць')";
    private static final String SELECTOR_WITH_TRANSFERS = "button:has-text('з пересадками')";
    private static final String CITY_FROM = "Звідки";
    private static final String CITY_WHERE = "Куди";
    private static final String ENTER = "Enter";
    private static final String BLANCK_SPACE = "";
    private static final String BOOKING_PAGE = "a[href*='booking']";
    private static final String ARCHIVE_DIR = "archive";
    private static final String LIST_BOX = "ul[role='listbox'] li";
    private static final boolean SUBSEQUENT = true;
    private static final boolean NON_SUBSEQUENT = false;
    private static final int LONG_DELAY = 2000;
    private static final int MEDIUM_DELAY = 500;//возможно надо будет позже для уменьшения заддержек
    private static final int SHORT_DELAY = 500;
    private static final int KEYBOARD_IMITATION_DELAY = 10;
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
        List<ScreenShotData> screenshots = new ArrayList<>();

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
            return createZipArchiveOfScreenshotSequence(screenshots);
        } catch (Exception e) {
            return "error";
        }
    }

    private Page prepareBookingPage(Page page, String url) {
        page.navigate(url);
        Page popup = page.waitForPopup(() -> page.click(BOOKING_PAGE));
        popup.waitForLoadState(LoadState.DOMCONTENTLOADED);
        return popup;
    }

    private void fillCitySearchFields(Page page, String cityFrom, String cityWhere, boolean isSubsequent) {
        if (isSubsequent) {
            subSequentTypeTextIntoPlaceholder(page, CITY_FROM, cityFrom);
            subSequentTypeTextIntoPlaceholder(page, CITY_WHERE, cityWhere);
        } else {
            firstTypeTextIntoPlaceholder(page, CITY_FROM, cityFrom);
            firstTypeTextIntoPlaceholder(page, CITY_WHERE, cityWhere);
        }
    }

    private void firstTypeTextIntoPlaceholder(Page page, String placeHolder, String value) {
        page.mouse().click(0, 0);
        page.getByPlaceholder(placeHolder).click();
        page.keyboard().type(value, new Keyboard.TypeOptions().setDelay(KEYBOARD_IMITATION_DELAY));
        page.waitForTimeout(SHORT_DELAY);
        page.locator(LIST_BOX).first().click();

    }

    private void subSequentTypeTextIntoPlaceholder(Page page, String placeHolder, String value) {

        page.getByPlaceholder(placeHolder).click();
        page.keyboard().type(value, new Keyboard.TypeOptions().setDelay(KEYBOARD_IMITATION_DELAY));
        page.waitForTimeout(SHORT_DELAY);
        page.locator(LIST_BOX).first().click();
    }

    private void performFirstSearch(Page page, String from, String where) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForTimeout(LONG_DELAY);
        fillCitySearchFields(page, from, where, NON_SUBSEQUENT);
        page.click(SELECTOR_SUBMIT);
        page.waitForTimeout(LONG_DELAY);
        noSeatsPagePopup(page);
    }

    private void performSubsequentSearch(Page page, String from, String where) {
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForTimeout(LONG_DELAY);
        page.click(SELECTOR_EDIT);
        page.waitForTimeout(LONG_DELAY);
        fillCitySearchFields(page, from, where, SUBSEQUENT);
        page.click(SELECTOR_SUBMIT);
        page.waitForTimeout(LONG_DELAY);
        noSeatsPagePopup(page);
    }

    private void noSeatsPagePopup(Page page) {
        Locator noSeatPopup = page.locator(SELECTOR_NO_SEATS);
        if (noSeatPopup.isVisible()) {
            noSeatPopup.click();
            page.waitForTimeout(LONG_DELAY);
        }
        page.waitForTimeout(LONG_DELAY);
    }

    private ScreenShotData captureScreenshot(Page page) {
        page.waitForTimeout(LONG_DELAY);
        page.evaluate("window.stop()");
        byte[] bytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        return new ScreenShotData("scr" + System.currentTimeMillis() + ".png", bytes);
    }

    private String createZipArchiveOfScreenshotSequence(List<ScreenShotData> screenShots) {
        new File(ARCHIVE_DIR).mkdirs();
        String zipPath = String.format("%s/Archive#_%d.zip", ARCHIVE_DIR, System.currentTimeMillis());
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipPath);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {
            for (ScreenShotData entry : screenShots) {
                zipOutputStream.putNextEntry(new ZipEntry(entry.name));
                zipOutputStream.write(entry.bytes);
                zipOutputStream.closeEntry();
            }
            return zipPath;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create an archive", e);
        }

    }

    private record ScreenShotData(String name, byte[] bytes) {

    }

    @PreDestroy
    public void cleanup() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }


}


