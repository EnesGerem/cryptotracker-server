package com.enes.cryptotracker.alert;

import com.enes.cryptotracker.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    @Query(value = "SELECT * from alert where status='NEW'", nativeQuery = true)
    List<Alert> findAlertsByStatusIsNew();
}
