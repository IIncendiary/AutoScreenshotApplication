package com.example.AutoScreenShotApplication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScreenShotController {

    private final ScreenShotService screenShotService;

    public ScreenShotController(ScreenShotService screenShotService) {
        this.screenShotService = screenShotService;
    }

    @GetMapping("/generate")
    public String getScreenShot() {
        return screenShotService.makeScreenShot("https://www.uz.gov.ua");
    }
}