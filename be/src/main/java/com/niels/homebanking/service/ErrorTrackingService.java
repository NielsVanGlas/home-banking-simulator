package com.niels.homebanking.service;

import com.niels.homebanking.dto.error.ShowErrorTracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ErrorTrackingService {

    Page<ShowErrorTracking> getErrors(Pageable pagination);

}
