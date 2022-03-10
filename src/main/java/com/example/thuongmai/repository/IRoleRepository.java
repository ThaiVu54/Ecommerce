package com.example.thuongmai.repository;

import com.example.thuongmai.enums.EnumRoles;
import com.example.thuongmai.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(EnumRoles name);
}
