package com.example.thuongmai.repository;

import com.example.thuongmai.model.origin.Origin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOriginRepository extends JpaRepository<Origin,Long> {

}
