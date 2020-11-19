package com.mdababi.myboutique.services;

import com.mdababi.myboutique.dto.CategoryDto;
import com.mdababi.myboutique.entities.Category;
import com.mdababi.myboutique.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<CategoryDto> findAll(){
        log.debug("Request to get all Categories");
        return this.categoryRepository.findAll().stream().map(CategoryService::mapToDto).collect(Collectors.toList());
    }

    public CategoryDto findById(Long id) {
        log.debug("Request to get Category : {}", id);
        return this.categoryRepository.findById(id).map(CategoryService::mapToDto)
                .orElseThrow(IllegalStateException::new);
    }

    public CategoryDto create(CategoryDto categoryDto) {
        log.debug("Request to create Category : {}", categoryDto);
        return mapToDto(this.categoryRepository.save(
                new Category(
                        categoryDto.getName(),
                        categoryDto.getDescription(),
                        Collections.emptySet()
                )
        ));
    }

    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        this.categoryRepository.deleteById(id);
    }

    public static CategoryDto mapToDto(Category category) {
        if (category != null) {
            return new CategoryDto(
                    category.getId(),
                    category.getName(),
                    category.getDescription(),
                    category.getProducts()
                            .stream()
                            .map(ProductService::mapToDto)
                            .collect(Collectors.toSet())
            );
        }
        return null;
    }

}
