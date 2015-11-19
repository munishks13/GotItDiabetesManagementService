package com.capstone.coursera.gidma.service.model.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.capstone.coursera.gidma.service.model.Follower;

public interface FollowerRepository extends CrudRepository<Follower, Long> {

    public Collection<Follower> findByFollowerUserId(String followerUserId);
    
    public Collection<Follower> findByUserId(String userId);

    @Query("SELECT t FROM Follower t WHERE t.userId = :userId  AND t.followerUserId = :followerUserId ")
    public Follower findByUserIdAndFollowerUserId(@Param("userId") String userId, @Param("followerUserId") String followerUserId);
    
}
