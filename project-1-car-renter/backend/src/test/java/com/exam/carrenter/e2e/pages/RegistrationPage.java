package com.exam.carrenter.e2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class RegistrationPage {
    private final WebDriver d;
    private final WebDriverWait w;

    public RegistrationPage(WebDriver d, WebDriverWait w) { this.d = d; this.w = w; }

    public void open(String baseUrl) { d.get(baseUrl + "/registration"); }

    public void register(String email, String password) {
        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Regisztráció')]")));
        d.findElement(By.cssSelector("input[placeholder*='pelda']")).sendKeys(email);
        WebElement pw = d.findElement(By.cssSelector("input[type='password']"));
        pw.clear();
        pw.sendKeys(password);
        d.findElement(By.xpath("//button[contains(.,'Regisztrálás') or contains(.,'Fiók létrehozása')]")).click();
    }
}
