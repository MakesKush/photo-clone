package com.test.kush.photo.clone.controllers;

import com.test.kush.photo.clone.service.PhotoService;
import com.test.kush.photo.clone.model.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;


@RestController
@RequiredArgsConstructor
public class PhotoController {


    private final PhotoService photoService;


    @GetMapping("/")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/photos")
    public Iterable<Photo> get() {
        return photoService.get();
    }

    @GetMapping("/photos/{id}")
    public  Photo get(@PathVariable Integer id) {
        Photo photo = photoService.get(id);
        if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return photo;
    }

    @DeleteMapping("/photos/{id}")
    public  void delete(@PathVariable Integer id) {
        photoService.remove(id);
    }

    @PostMapping("/photos")
    public Photo create(@RequestPart("file") MultipartFile file) throws IOException {
        return photoService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
    }
}
