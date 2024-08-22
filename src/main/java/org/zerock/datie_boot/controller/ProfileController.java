package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.datie_boot.dto.ProfileDTO;
import org.zerock.datie_boot.service.ProfileService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getUserByUserno(@RequestParam("userno") int userno) {
        ProfileDTO profileDTO = profileService.getUserByUserno(userno);
        if (profileDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profileDTO);
    }

    @PostMapping("/profile/{userno}")
    public ResponseEntity<Void> updateUserProfile(@PathVariable int userno, @RequestBody ProfileDTO profileDTO) {
        System.out.println("profile");
        try {
            profileService.updateUserProfile(userno, profileDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
