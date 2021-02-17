package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from Role r where r.name = 'USER'")
    Role findUserRole();
}
