package com.exam.carrenter.e2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class NewRentalPage {
    private final WebDriver d;
    private final WebDriverWait w;

    public NewRentalPage(WebDriver d, WebDriverWait w) { this.d = d; this.w = w; }

    public void open(String baseUrl) { d.get(baseUrl + "/new-rental"); }

    public void createRental(String carLabelPart, String startIsoLocal, String endIsoLocal) {
        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Új foglalás')]")));
        // autó választó
        WebElement sel = d.findElement(By.cssSelector("select"));
        new Select(sel).selectByIndex(1); // legegyszerűbb: az első opció az üres, a 2. egy autó
        // időpontok
        d.findElement(By.cssSelector("input[type='datetime-local']")).sendKeys(startIsoLocal);
        d.findElements(By.cssSelector("input[type='datetime-local']")).get(1).sendKeys(endIsoLocal);
        // mentés
        d.findElement(By.xpath("//button[contains(.,'Bérlés') or contains(.,'Mentés')]")).click();
        // alert elfogadása
        try {
            Alert a = w.until(ExpectedConditions.alertIsPresent());
            a.accept();
        } catch (TimeoutException ignored) {}
    }
}
