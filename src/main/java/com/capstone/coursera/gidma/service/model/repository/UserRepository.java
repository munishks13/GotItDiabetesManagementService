package com.capstone.coursera.gidma.service.model.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.capstone.coursera.gidma.service.model.User;

public interface UserRepository extends CrudRepository<User, String> {

    public Collection<User> findUserByFirstName(String firstName);

    public Collection<User> findUserByLastName(String lastName);

    @Query("SELECT t.userId, t.firstName, t.lastName, t.dateOfBirth, t.medicalRecordNumber, t.followed FROM User t WHERE t.userId = :userId ")
    public User findUserBasicInfo(@Param("userId") String userId);
    
}
