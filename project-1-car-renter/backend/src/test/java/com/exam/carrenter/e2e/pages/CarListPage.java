package com.exam.carrenter.e2e.pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class CarListPage {
    private final WebDriver d;
    private final WebDriverWait w;

    public CarListPage(WebDriver d, WebDriverWait w) { this.d = d; this.w = w; }

    public void open(String baseUrl) { d.get(baseUrl + "/"); }

    public boolean isLoaded() {
        try {
            w.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Bérelhető autók')]")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(.,'Jelenleg nincs megjeleníthető autó')]"))
            ));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
