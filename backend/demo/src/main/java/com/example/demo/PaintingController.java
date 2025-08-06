package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paintings")
public class PaintingController {
    @Autowired
    private PaintingRepository paintingRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Painting> savePainting(@RequestBody PaintingRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if (user == null) {
            return ResponseEntity.status(404).build();
        }
        Painting painting = paintingRepository.findByUserId(user.getId());
        if (painting == null) {
            painting = new Painting();
            painting.setUserId(user.getId());
        }
        painting.setData(request.getData());
        Painting savedPainting = paintingRepository.save(painting);
        return ResponseEntity.ok(savedPainting);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Painting> getPainting(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(404).build();
        }
        Painting painting = paintingRepository.findByUserId(user.getId());
        if (painting != null) {
            return ResponseEntity.ok(painting);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

// کلاس کمکی برای درخواست
class PaintingRequest {
    private String username;
    private String data;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}