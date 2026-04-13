package com.example.AutoScreenshotApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScreenshotController {

    private final ScreenshotService screenShotService;

    public ScreenshotController(ScreenshotService screenShotService) {
        this.screenShotService = screenShotService;
    }

    @GetMapping("/generate")
    public String getScreenShot() {
        return screenShotService.makeScreenShot("https://www.uz.gov.ua");
    }
}