package com.exam.carrenter.e2e.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NavBar {
    private final WebDriver d;
    private final WebDriverWait w;

    public NavBar(WebDriver d, WebDriverWait w) { this.d = d; this.w = w; }

    public void openMobileMenuIfHidden() {
        // ha a sidebar overlay nyitó gomb látszik, nyissuk ki
        try {
            WebElement btn = d.findElement(By.cssSelector("header button[aria-label='Menü']"));
            if (btn.isDisplayed()) btn.click();
        } catch (NoSuchElementException ignored) {}
    }

    public void goCars() {
        openMobileMenuIfHidden();
        w.until(ExpectedConditions.elementToBeClickable(By.linkText("Bérelhető autók"))).click();
    }

    public void goNewRental() {
        openMobileMenuIfHidden();
        w.until(ExpectedConditions.elementToBeClickable(By.linkText("Autó kölcsönzés"))).click();
    }

    public void goAdminCars() {
        openMobileMenuIfHidden();
        w.until(ExpectedConditions.elementToBeClickable(By.linkText("Nyilvántartó"))).click();
    }

    public void goLogin() {
        openMobileMenuIfHidden();
        // lehet sidebarban vagy felső menüben
        try {
            d.findElement(By.linkText("Belépés")).click();
        } catch (NoSuchElementException e) {
            w.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']"))).click();
        }
    }

    public void goRegistration() {
        openMobileMenuIfHidden();
        w.until(ExpectedConditions.elementToBeClickable(By.linkText("Regisztráció"))).click();
    }

    public void logoutIfPresent() {
        try {
            openMobileMenuIfHidden();
            d.findElement(By.xpath("//*[self::button or self::a][contains(.,'Kilépés')]")).click();
        } catch (NoSuchElementException ignored) {}
    }
}
