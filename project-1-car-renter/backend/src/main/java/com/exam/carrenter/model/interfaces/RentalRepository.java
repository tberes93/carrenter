package com.exam.carrenter.model.interfaces;

import com.exam.carrenter.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {

    @Query("select r from Rental r where r.carId = :rid and r.end > :start and r.start < :end")
    List<Rental> findOverlaps(@Param("rid") UUID carId,
                               @Param("start") LocalDateTime start,
                               @Param("end") LocalDateTime end);

    @Query("select r from Rental r where (:from is null or r.end > :from) and (:to is null or r.start < :to)")
    List<Rental> findInRange(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
