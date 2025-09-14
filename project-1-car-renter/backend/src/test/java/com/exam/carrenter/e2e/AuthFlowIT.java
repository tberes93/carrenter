package com.exam.carrenter.e2e;

import com.exam.carrenter.e2e.BaseTest;
import com.exam.carrenter.e2e.pages.CarListPage;
import com.exam.carrenter.e2e.pages.LoginPage;
import com.exam.carrenter.e2e.pages.NavBar;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthFlowIT extends BaseTest {

    @Test
    void user_can_login_and_see_cars() {
        var nav = new NavBar(driver, wait);
        var login = new LoginPage(driver, wait);
        var cars = new CarListPage(driver, wait);

        // kezdőlap
        cars.open(baseUrl);
        assertThat(cars.isLoaded()).isTrue();

        // belépés
        nav.goLogin();
        login.login("user@example.com", "User123!");
        // redirect után autólista látszik
        assertThat(cars.isLoaded()).isTrue();
    }
}
