package com.exam.carrenter.e2e;

import com.exam.carrenter.e2e.BaseTest;
import com.exam.carrenter.e2e.pages.NavBar;
import com.exam.carrenter.e2e.pages.LoginPage;
import com.exam.carrenter.e2e.pages.NewRentalPage;
import org.junit.jupiter.api.Test;

public class RentalFlowIT extends BaseTest {

    @Test
    void user_can_create_rental_non_overlapping() {
        var nav = new NavBar(driver, wait);
        var login = new LoginPage(driver, wait);
        var rental = new NewRentalPage(driver, wait);

        // login
        driver.get(baseUrl + "/login");
        login.login("user@example.com", "User123!");
        // új foglalás
        nav.goNewRental();

        // datetime-local mezőknek a billentyűs input formátuma: YYYYMMDDThhmm
        // Példa: 20300101T1000 → 2030-01-01 10:00
        rental.createRental(null, "2030.01.01. 10:00", "2030.01.02. 10:00");
    }
}
