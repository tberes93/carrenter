package com.exam.carrenter.e2e;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl = System.getProperty("baseUrl", "http://localhost:5173");

    @BeforeEach
    void setupDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--remote-debugging-port=8337");
        // fej nélküli mód CI-hez: állítsd true-ra ha kell
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) opts.addArguments("--headless=new");
        opts.addArguments("--window-size=1400,900");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(8));
    }

    @AfterEach
    void teardown() {
        if (driver != null) driver.quit();
    }
}
