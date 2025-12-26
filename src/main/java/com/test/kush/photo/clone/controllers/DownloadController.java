package com.test.kush.photo.clone.controllers;

import com.test.kush.photo.clone.model.Photo;
import com.test.kush.photo.clone.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class DownloadController {

    private final PhotoService photoService;

    @GetMapping("/photos/{id}/content")
    public ResponseEntity<byte[]> content(@PathVariable Long id) {
        Photo photo = photoService.getOrThrow(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getContentType()));
        headers.setContentDisposition(
                ContentDisposition.inline()
                        .filename(photo.getOriginalName(), StandardCharsets.UTF_8)
                        .build()
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(photo.getData());
    }
}
