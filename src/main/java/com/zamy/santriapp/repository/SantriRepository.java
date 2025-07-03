package com.zamy.santriapp.repository;

import com.zamy.santriapp.entity.Santri; // ✅ BUKAN model.Santri
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SantriRepository extends JpaRepository<Santri, Long> {
    List<Santri> findByNamaSantriContainingIgnoreCase(String keyword);
    List<Santri> findByKelas(String kelas);
    long countByKelas(String kelas);
}
