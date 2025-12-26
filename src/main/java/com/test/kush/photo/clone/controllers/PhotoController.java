package com.test.kush.photo.clone.controllers;

import com.test.kush.photo.clone.dto.PhotoDto;
import com.test.kush.photo.clone.model.Photo;
import com.test.kush.photo.clone.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PhotoDto upload(@RequestPart("file") MultipartFile file) throws IOException {
        Photo saved = photoService.save(file);
        return toDto(saved);
    }

    @GetMapping
    public List<PhotoDto> list() {
        return photoService.list().stream().map(this::toDto).toList();
    }

    @DeleteMapping("/photos/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            photoService.deleteById(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();  // 404
        }
    }

    private PhotoDto toDto(Photo p) {
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String url = baseUrl + "/photos/" + p.getId() + "/content"; // <-- вот ссылка на картинку
        return new PhotoDto(
                p.getId(),
                p.getFilename(),
                p.getOriginalName(),
                p.getContentType(),
                p.getData() != null ? p.getData().length : 0,
                url
        );
    }
}
