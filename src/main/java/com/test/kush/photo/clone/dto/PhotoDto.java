package com.test.kush.photo.clone.dto;

public record PhotoDto(
        Long id,
        String filename,
        String originalName,
        String contentType,
        long size,
        String url
) {}
