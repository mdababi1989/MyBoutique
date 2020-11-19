package com.mdababi.myboutique.resources;

import com.mdababi.myboutique.dto.CategoryDto;
import com.mdababi.myboutique.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mdababi.myboutique.utils.Web.API;

@RequiredArgsConstructor
@RestController
@RequestMapping(API + "/categories")
public class CategoryResource {
private final CategoryService categoryService;
@GetMapping
public List<CategoryDto> findAll() {
return this.categoryService.findAll();
}
@GetMapping("/{id}")
public CategoryDto findById(@PathVariable Long id) {
return this.categoryService.findById(id);
}
@PostMapping
public CategoryDto create(CategoryDto categoryDto) {
return this.categoryService.create(categoryDto);
}
@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
this.categoryService.delete(id);
}
}
