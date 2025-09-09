package com.test.kush.photo.clone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@Getter
@Setter

@Table("PHOTOS")
public class Photo {

    @Id
    private Integer id;

    @JsonIgnore
    private byte[] data;

    private String ContentType;

    @NotEmpty
    private  String fileName;

}
