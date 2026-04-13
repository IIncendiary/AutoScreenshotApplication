package com.example.AutoScreenshotApplication;

import lombok.Data;

@Data
public class ApplicationConstants {
    public static final String SELECTOR_SUBMIT = "button:has-text('Знайти')";
    public static final String SELECTOR_EDIT = "button:has-text('Редагувати пошук')";
    public static final String SELECTOR_NO_SEATS = "button:has-text('без вільних місць')";
    public static final String SELECTOR_WITH_TRANSFERS = "button:has-text('з пересадками')";
    public static final String CITY_FROM = "Звідки";
    public static final String CITY_WHERE = "Куди";
    public static final String ENTER = "Enter";
    public static final String BLANCK_SPACE = "";
    public static final String BOOKING_PAGE = "a[href*='booking']";
    public static final String ARCHIVE_DIR = "archive";
    public static final String LIST_BOX = "ul[role='listbox'] li";
    public static final boolean SUBSEQUENT = true;
    public static final boolean NON_SUBSEQUENT = false;
    public static final int LONG_DELAY = 2000;
    public static final int MEDIUM_DELAY = 500;
    public static final int SHORT_DELAY = 500;
    public static final int KEYBOARD_IMITATION_DELAY = 10;
}
