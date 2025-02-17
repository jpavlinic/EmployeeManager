package com.company.employeemanagement.repository;

import com.company.employeemanagement.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {

    // Find logs by action type
    List<ActivityLog> findByAction(String action);
}
