package com.zamy.santriapp.controller;

import com.zamy.santriapp.entity.Santri;
import com.zamy.santriapp.repository.SantriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/santri")
public class SantriController {

    @Autowired
    private SantriRepository santriRepo;

    // Lokasi simpan foto
    private static final String UPLOAD_DIR = "src/main/resources/static/foto/";

    // Halaman utama
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // Tampilkan semua santri + pencarian
    @GetMapping
    public String index(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Santri> listSantri;
        if (keyword != null && !keyword.isEmpty()) {
            listSantri = santriRepo.findByNamaSantriContainingIgnoreCase(keyword);
        } else {
            listSantri = santriRepo.findAll();
        }
        model.addAttribute("listSantri", listSantri);
        model.addAttribute("judul", "Semua Santri");
        model.addAttribute("keyword", keyword);
        return "index";
    }

    // Filter berdasarkan kelas
    @GetMapping("/kategori/{kelas}")
    public String filterByKelas(@PathVariable String kelas, Model model) {
        List<Santri> hasil = santriRepo.findByKelas(kelas);
        model.addAttribute("listSantri", hasil);
        model.addAttribute("judul", "Santri Kelas " + kelas);
        return "index";
    }

    // Statistik
    @GetMapping("/statistik")
    public String statistik(Model model) {
        model.addAttribute("jumlahMI", santriRepo.countByKelas("MI"));
        model.addAttribute("jumlahMTS", santriRepo.countByKelas("MTS"));
        model.addAttribute("jumlahMA", santriRepo.countByKelas("MA"));
        return "statistik";
    }

    // Form tambah
    @GetMapping("/tambah")
    public String formTambah(Model model) {
        model.addAttribute("santri", new Santri());
        return "form";
    }

    // Simpan data santri
    @PostMapping("/simpan")
    public String simpan(@ModelAttribute Santri santri,
                         @RequestParam("foto") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String namaFile = file.getOriginalFilename();
            file.transferTo(new File(UPLOAD_DIR + namaFile));
            santri.setFoto(namaFile);
        }

        santriRepo.save(santri);
        return "redirect:/santri";
    }

    // Edit santri
    @GetMapping("/edit/{id}")
    public String formEdit(@PathVariable Long id, Model model) {
        Santri santri = santriRepo.findById(id).orElse(null);
        model.addAttribute("santri", santri);
        return "form";
    }

    // Hapus
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        santriRepo.deleteById(id);
        return "redirect:/santri";
    }
}
