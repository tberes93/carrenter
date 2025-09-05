package com.exam.carrenter.model;



import com.exam.carrenter.model.interfaces.RentalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.flyway.enabled=false")
@ActiveProfiles("test")
class RentalRepositoryTest {


    @Autowired RentalRepository rentals;


    @Test
    void findOverlaps_returnsConflictsOnly() {
        UUID car = UUID.randomUUID();
        LocalDateTime s1 = LocalDateTime.of(2030,1,1,10,0);
        LocalDateTime e1 = LocalDateTime.of(2030,1,1,12,0);
        Rental r1 = new Rental(); r1.setCarId(car); r1.setStart(s1); r1.setEnd(e1);
        r1.setStatus("ordered");
        r1.setUserId(UUID.randomUUID());
        rentals.save(r1);


// átfed (kezdet benne)
        Rental r2 = new Rental(); r2.setCarId(car);
        r2.setStart(LocalDateTime.of(2030,1,1,11,0));
        r2.setEnd(LocalDateTime.of(2030,1,1,13,0));
        r2.setStatus("ordered");
        r2.setUserId(UUID.randomUUID());
        rentals.save(r2);


// másik autó → nem ütközik
        Rental rOther = new Rental(); rOther.setCarId(UUID.randomUUID());
        rOther.setStart(LocalDateTime.of(2030,1,1,11,0));
        rOther.setEnd(LocalDateTime.of(2030,1,1,13,0));
        rOther.setStatus("ordered");
        rOther.setUserId(UUID.randomUUID());
        rentals.save(rOther);


        List<Rental> conflicts = rentals.findOverlaps(car,
                LocalDateTime.of(2030,1,1,9,30), LocalDateTime.of(2030,1,1,10,30));
        assertThat(conflicts).hasSize(1);
    }


    @Test
    void findInRange_respectsNullBounds() {
        UUID car = UUID.randomUUID();
        Rental r = new Rental(); r.setCarId(car);
        r.setStart(LocalDateTime.of(2030,1,1,10,0));
        r.setEnd(LocalDateTime.of(2030,1,2,10,0));
        r.setStatus("ordered");
        r.setUserId(UUID.randomUUID());
        rentals.save(r);


        assertThat(rentals.findInRange(null, null)).hasSize(1);
        assertThat(rentals.findInRange(LocalDateTime.of(2031,1,1,0,0), null)).isEmpty();
        assertThat(rentals.findInRange(null, LocalDateTime.of(2029,12,31,0,0))).isEmpty();
    }
}
