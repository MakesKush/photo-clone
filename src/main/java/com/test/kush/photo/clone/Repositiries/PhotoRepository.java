package com.test.kush.photo.clone.Repositiries;

import com.test.kush.photo.clone.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
