package com.exam.carrenter.model.interfaces;

import com.exam.carrenter.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    List<Car> findAll();
}
