package com.test.kush.photo.clone.service;

import com.test.kush.photo.clone.Repositiries.PhotoRepository;
import com.test.kush.photo.clone.model.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;

    public Iterable<Photo> get() {
        return photoRepository.findAll();
    }

    public Photo get(Integer id) {
        return photoRepository.findById(id).orElse(null);
    }

    public void remove(Integer id) {
        photoRepository.deleteById(id);
    }

    public Photo save(String fileName, String contentType, byte[] data) {
        Photo photo = new Photo();
        photo.setFileName(fileName);
        photo.setData(data);
        photo.setContentType(contentType);
        photoRepository.save(photo);
        return photo;
    }
}
