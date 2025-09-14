package com.exam.carrenter.e2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class AdminCarsPage {
    private final WebDriver d;
    private final WebDriverWait w;

    public AdminCarsPage(WebDriver d, WebDriverWait w) { this.d = d; this.w = w; }

    public void open(String baseUrl) { d.get(baseUrl + "/admin/cars"); }

    public void createCar(String plate, String type, String seats, String fuel) {
        w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(.,'Admin')]")));
        // 4 mező sorrendben
        d.findElement(By.cssSelector("input[placeholder='ABC-123']")).sendKeys(plate);
        d.findElement(By.cssSelector("input[placeholder*='Toyota']")).sendKeys(type);
        d.findElement(By.cssSelector("input[type='number']")).sendKeys(seats);
        d.findElement(By.cssSelector("input[placeholder*='Benzin']")).sendKeys(fuel);
        d.findElement(By.xpath("//button[.//span[contains(.,'Autó hozzáadása')] or contains(.,'Mentés')]")).click();
        // táblázat frissül
        w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(.,'" + plate + "')]")));
    }

    public void deleteCarByPlate(String plate) {
        WebElement row = d.findElement(By.xpath("//tr[td[contains(.,'" + plate + "')]]"));
        row.findElement(By.xpath(".//button[contains(.,'Törlés')]")).click();
        // eltűnés
        new WebDriverWait(d, java.time.Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOf(row));
    }
}
