package com.zamy.santriapp.controller;

import com.zamy.santriapp.entity.Admin;
import com.zamy.santriapp.repository.AdminRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AdminRepository adminRepo;

    @GetMapping("/login")
    public String formLogin(Model model) {
        model.addAttribute("admin", new Admin());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("admin") Admin admin, HttpSession session, Model model) {
        Admin validAdmin = adminRepo.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());
        if (validAdmin != null) {
            session.setAttribute("adminLogin", true);
            return "redirect:/santri/home";
        } else {
            model.addAttribute("error", "Username atau password salah!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
