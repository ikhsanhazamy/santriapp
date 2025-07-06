package com.zamy.santriapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.zamy.santriapp.entity.Santri;
import com.zamy.santriapp.repository.SantriRepository;

@Controller
@RequestMapping("/santri")
public class SantriController {

    @Autowired
    private SantriRepository santriRepo;

    private final String uploadDir = "src/main/resources/static/uploads/foto";

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listSantri", santriRepo.findAll());
        model.addAttribute("judul", "Semua Santri");
        return "index";
    }

    @GetMapping("/kategori/{kode}")
    public String filterByKelas(@PathVariable String kode, Model model) {
        String fullName = switch (kode.toUpperCase()) {
            case "MI" -> "Madrasah Ibtidaiyah";
            case "MTS" -> "Madrasah Tsanawiyah";
            case "MA" -> "Madrasah Aliyah";
            default -> kode;
        };
        List<Santri> hasil = santriRepo.findByKelas(fullName);
        model.addAttribute("listSantri", hasil);
        model.addAttribute("judul", "Santri " + fullName);
        return "index";
    }

    @GetMapping("/tambah")
    public String tambah(Model model) {
        model.addAttribute("santri", new Santri());
        return "form";
    }

 @PostMapping("/simpan")
public String simpan(
        @RequestParam("foto") MultipartFile file,
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam("namaSantri") String namaSantri,
        @RequestParam("kelas") String kelas,
        @RequestParam("jenisKelamin") String jenisKelamin,
        @RequestParam("asalDaerah") String asalDaerah,
        @RequestParam("namaOrtu") String namaOrtu,
        @RequestParam("noTelpOrtu") String noTelpOrtu
) {
    Santri s = (id != null) ? santriRepo.findById(id).orElse(new Santri()) : new Santri();
    s.setNamaSantri(namaSantri);
    s.setKelas(kelas);
    s.setJenisKelamin(jenisKelamin);
    s.setAsalDaerah(asalDaerah);
    s.setNamaOrtu(namaOrtu);
    s.setNoTelpOrtu(noTelpOrtu);

    String oldFoto = s.getFoto();

    if (!file.isEmpty()) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File saveFile = new File(uploadDir, fileName);
            file.transferTo(saveFile);

            // delete old
            if (oldFoto != null && !oldFoto.equals("default.jpg")) {
                File oldFile = new File(uploadDir, oldFoto);
                if (oldFile.exists()) oldFile.delete();
            }

            s.setFoto(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        if (oldFoto != null) {
            s.setFoto(oldFoto);
        } else {
            s.setFoto("default.jpg");
        }
    }

    santriRepo.save(s);
    return "redirect:/santri";
}


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Santri s = santriRepo.findById(id).orElse(null);
        model.addAttribute("santri", s);
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        Santri santri = santriRepo.findById(id).orElse(null);
        if (santri != null && santri.getFoto() != null && !santri.getFoto().equals("default.jpg")) {
            File fotoFile = new File(uploadDir, santri.getFoto());
            if (fotoFile.exists()) fotoFile.delete();
        }
        santriRepo.deleteById(id);
        return "redirect:/santri";
    }

    @GetMapping("/statistik")
    public String statistik(Model model) {
        long jumlahMI = santriRepo.countByKelas("Madrasah Ibtidaiyah");
        long jumlahMTS = santriRepo.countByKelas("Madrasah Tsanawiyah");
        long jumlahMA = santriRepo.countByKelas("Madrasah Aliyah");
        long total = jumlahMI + jumlahMTS + jumlahMA;

        model.addAttribute("jumlahMI", jumlahMI);
        model.addAttribute("jumlahMTS", jumlahMTS);
        model.addAttribute("jumlahMA", jumlahMA);
        model.addAttribute("total", total);

        return "statistik";
    }
}
