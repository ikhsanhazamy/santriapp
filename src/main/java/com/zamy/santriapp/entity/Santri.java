package com.zamy.santriapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "santri")
public class Santri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaSantri;
    private String kelas;
    private String jenisKelamin;
    private String asalDaerah;
    private String namaOrtu;
    private String noTelpOrtu;
    

    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaSantri() {
        return namaSantri;
    }

    public void setNamaSantri(String namaSantri) {
        this.namaSantri = namaSantri;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAsalDaerah() {
        return asalDaerah;
    }

    public void setAsalDaerah(String asalDaerah) {
        this.asalDaerah = asalDaerah;
    }

    public String getNamaOrtu() {
        return namaOrtu;
    }

    public void setNamaOrtu(String namaOrtu) {
        this.namaOrtu = namaOrtu;
    }

    public String getNoTelpOrtu() {
        return noTelpOrtu;
    }

    public void setNoTelpOrtu(String noTelpOrtu) {
        this.noTelpOrtu = noTelpOrtu;
    }

    private String foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
