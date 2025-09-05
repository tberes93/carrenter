package com.exam.carrenter.services;

import com.exam.carrenter.model.Rental;
import com.exam.carrenter.model.interfaces.RentalRepository;
import org.springframework.stereotype.Service;


@Service
public class RentalService {
    private final RentalRepository rentalRepo;
    public RentalService(RentalRepository rentalRepo) { this.rentalRepo = rentalRepo; }


    public Rental create(Rental b) {
        // TODO: a kezdeti dátum korábban van a végdátumnál ---> ezt én írtam most gyorsan
        if (b.getEnd() == null) {
            throw new IllegalArgumentException("Start date is mandatory.");
        }
        if (b.getEnd() != null && !b.getStart().isBefore(b.getEnd())) {
            throw new IllegalArgumentException("End date is before start date.");
        }
        if (!rentalRepo.findOverlaps(b.getCarId(), b.getStart(), b.getEnd()).isEmpty()) {
            throw new IllegalArgumentException("Overlapping renting");
        }
        b.setStatus("CONFIRMED");
        return rentalRepo.save(b);
    }


}
