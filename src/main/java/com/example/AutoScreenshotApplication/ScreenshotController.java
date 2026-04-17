package com.example.AutoScreenshotApplication;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class ScreenshotController {

    private final ScreenshotService screenShotService;

    @GetMapping("/generate")
    public String getScreenShot() {
        return screenShotService.makeScreenShot("https://www.uz.gov.ua");
    }
}