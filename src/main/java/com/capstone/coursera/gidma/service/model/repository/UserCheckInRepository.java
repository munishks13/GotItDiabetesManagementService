package com.capstone.coursera.gidma.service.model.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.capstone.coursera.gidma.service.model.UserCheckIn;

public interface UserCheckInRepository extends CrudRepository<UserCheckIn, Long> {

    @Query("SELECT t FROM UserCheckIn t WHERE t.userId = :userId AND t.checkInDateTime = :checkInDateTime")
    public UserCheckIn findByUserIdAndDateTime(@Param("userId") String userId, @Param("checkInDateTime") Date checkInDateTime);

    @Query("SELECT t FROM UserCheckIn t WHERE t.userId = :userId AND t.checkInDateTime BETWEEN :fromDate AND :toDate ")
    public Collection<UserCheckIn> findByUserIdAndDateRange(@Param("userId") String userId, @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

    @Query("SELECT t FROM UserCheckIn t WHERE t.userId = :userId AND t.checkInDateTime >= :fromDate ")
    public Collection<UserCheckIn> findByUserIdAndFromDate(@Param("userId") String userId, @Param("fromDate") Date fromDate);

    @Query("SELECT t FROM UserCheckIn t WHERE t.userId = :userId AND t.checkInDateTime <= :toDate ")
    public Collection<UserCheckIn> findByUserIdAndUpToDate(@Param("userId") String userId, @Param("toDate") Date toDate);

    public Collection<UserCheckIn> findByUserId(String userId);

}
