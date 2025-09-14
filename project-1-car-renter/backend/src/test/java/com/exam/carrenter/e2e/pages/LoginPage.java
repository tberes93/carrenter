package com.exam.carrenter.e2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class LoginPage {
    private final WebDriver d;
    private final WebDriverWait w;

    public LoginPage(WebDriver d, WebDriverWait w) { this.d = d; this.w = w; }

    public void open(String baseUrl) { d.get(baseUrl + "/login"); }

    public void login(String email, String password) {
        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Belépés')]")));
        d.findElement(By.cssSelector("input[placeholder*='pelda']")).sendKeys(email);
        d.findElement(By.cssSelector("input[type='password']")).sendKeys(password);
        d.findElement(By.xpath("//button[contains(.,'Belépés')]")).click();
    }
}
