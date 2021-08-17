package com.project.repository;

import com.project.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface BannerRepository extends JpaRepository<Banner , Integer> {
    @Query(value = "select * from banner where title = ?1", nativeQuery = true)
    public Optional<Banner> findByTitle(String title);
}
