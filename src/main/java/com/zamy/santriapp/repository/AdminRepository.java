package com.zamy.santriapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zamy.santriapp.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username); // tambahkan ini
}

