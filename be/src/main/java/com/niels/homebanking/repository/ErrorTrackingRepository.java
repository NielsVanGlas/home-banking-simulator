package com.niels.homebanking.repository;

import com.niels.homebanking.entity.ErrorTracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ErrorTrackingRepository  extends JpaRepository<ErrorTracking, UUID> {
}
