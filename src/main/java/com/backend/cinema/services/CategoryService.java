package com.backend.cinema.services;

import com.backend.cinema.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
}
