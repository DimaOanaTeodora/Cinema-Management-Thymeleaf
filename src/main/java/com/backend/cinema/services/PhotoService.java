package com.backend.cinema.services;

import com.backend.cinema.domain.Product;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    void savePhotoFile(Product product, MultipartFile file);
}
