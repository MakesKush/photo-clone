package com.test.kush.photo.clone.service;

import com.test.kush.photo.clone.Repositiries.PhotoRepository;
import com.test.kush.photo.clone.kafka.PhotoEventsProducer;
import com.test.kush.photo.clone.model.Photo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository repo;
    private final PhotoEventsProducer eventsProducer;

    @Transactional
    public Photo save(MultipartFile file) throws IOException {
        String originalName = (file.getOriginalFilename() != null && !file.getOriginalFilename().isBlank())
                ? file.getOriginalFilename()
                : "file";

        Photo p = new Photo();
        p.setFilename(originalName);
        p.setOriginalName(originalName);
        p.setContentType(file.getContentType() != null ? file.getContentType() : "application/octet-stream");
        p.setData(file.getBytes());

        Photo saved = repo.save(p);

        // тут мы отправялем событие в Kafka после сохранения
        eventsProducer.photoUploaded(
                saved.getId(),
                saved.getFilename(),
                saved.getContentType(),
                saved.getData().length
        );

        return saved;
    }

    @Transactional
    public void deleteById(Long id) {
        Photo photo = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Photo not found: " + id));

        repo.delete(photo);

        // тут я сообщаю в кафка о событии об удалении
        eventsProducer.photoDeleted(photo.getId());
    }

    public Photo getOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Photo not found: " + id));
    }

    public List<Photo> list() {
        return repo.findAll();
    }
}
