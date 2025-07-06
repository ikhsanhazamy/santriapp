package com.zamy.santriapp.repository;

import java.util.List; // 

import org.springframework.data.jpa.repository.JpaRepository;

import com.zamy.santriapp.entity.Santri;

public interface SantriRepository extends JpaRepository<Santri, Long> {
    List<Santri> findByNamaSantriContainingIgnoreCase(String keyword);
    List<Santri> findByKelas(String kelas);
    long countByKelas(String kelas);
}
