package com.exam.carrenter.e2e;

import com.exam.carrenter.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;


import java.net.URI;
import java.util.Map;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.datasource.url=jdbc:h2:mem:e2e;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
        "spring.datasource.username=sa",
        "spring.datasource.password="
})
@Import(TestSecurityConfig.class)
class ApiE2ETest {


    @LocalServerPort int port;
    @Autowired TestRestTemplate rest;


    private String url(String path) { return "http://localhost:" + port + path; }


  /*  @Test
    void fullFlow_carLifecycle_and_rentalCreation() {
// 1) Car létrehozás
        HttpHeaders h = new HttpHeaders(); h.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Map> postCar = rest.exchange(URI.create(url("/api/cars")), HttpMethod.POST,
                new HttpEntity<>(Map.of("licensePlate","FORD-FOCUS"), h), Map.class);
        assertThat(postCar.getStatusCode()).isEqualTo(HttpStatus.OK);
        String carId = (String) postCar.getBody().get("id");


// 2) Car lekérése
        ResponseEntity<Map> getCar = rest.getForEntity(url("/api/cars/"), Map.class);
        assertThat(getCar.getStatusCode()).isEqualTo(HttpStatus.OK);


// 3) Rental létrehozás (auth megkerülve a TestSecurityConfig miatt)
        ResponseEntity<Map> postRent = rest.exchange(URI.create(url("/api/rentals")), HttpMethod.POST,
                new HttpEntity<>(Map.of(
                        "carId", carId,
                        "start", "2030-01-01T10:00:00",
                        "end", "2030-01-02T10:00:00"
                ), h), Map.class);
        assertThat(postRent.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postRent.getBody()).containsKey("id");
    } */
}
//TODO ez még rohadtul nem működik
