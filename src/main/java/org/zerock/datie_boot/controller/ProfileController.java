package org.zerock.datie_boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.datie_boot.dto.DeleteIdDTO;
import org.zerock.datie_boot.dto.ProfileDTO;
import org.zerock.datie_boot.entity.User;
import org.zerock.datie_boot.repository.UserRepository;
import org.zerock.datie_boot.service.ProfileService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:3000", "http://ec2-13-53-91-123.eu-north-1.compute.amazonaws.com", "http://13.53.91.123"})
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserRepository userRepository;

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

    //프로필 api
    @PostMapping("/profileUpload")
    public ResponseEntity<String> uploadImages(
            @RequestParam("userno") int userno,
            @RequestParam("image") MultipartFile image) {
        try {
            // 다이어리 이미지 저장
            profileService.saveProfile(userno, image);
            return new ResponseEntity<>("Images uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload images", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/profileImage/{userno}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable int userno) {
        Optional<User> optionalUser = userRepository.findByUserno(userno);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // 프로필 이미지 파일 경로 생성
            String imageName = user.getProfileReal();
            Path filePath = Paths.get("src/main/resources/static/upload/profile/" + imageName);

            try {
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                            .body(resource);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            } catch (Exception e) {
                throw new RuntimeException("File not found: " + imageName, e);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("/delete/{userno}")
    public ResponseEntity<String> deleteID(@RequestBody DeleteIdDTO deleteIdDTO) {
        boolean isDeleted = profileService.deleteID(deleteIdDTO);

        if (isDeleted) {
            return new ResponseEntity<>("User or Card status updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update status. User or Card not found.", HttpStatus.NOT_FOUND);
        }
    }
}
