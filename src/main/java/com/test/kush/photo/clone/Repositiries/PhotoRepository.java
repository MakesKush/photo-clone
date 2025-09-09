package com.test.kush.photo.clone.Repositiries;

import com.test.kush.photo.clone.model.Photo;
import org.springframework.data.repository.CrudRepository;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {
}
