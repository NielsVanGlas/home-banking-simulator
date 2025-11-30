package com.niels.homebanking.repository;

import com.niels.homebanking.dto.error.ShowErrorTracking;
import com.niels.homebanking.entity.ErrorTracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ErrorTrackingRepository  extends JpaRepository<ErrorTracking, UUID> {

    @Query(value = "SELECT new com.niels.homebanking.dto.error.ShowErrorTracking(e.errorMessage, e.status, e.createdAt) FROM ErrorTracking e")
    Page<ShowErrorTracking> findAllErrors(Pageable pagination);

}
