package com.tucine.cineclubadministration.Film.controller;

import com.tucine.cineclubadministration.Film.dto.normal.CategoryDto;
import com.tucine.cineclubadministration.Film.dto.receive.CategoryReceiveDto;
import com.tucine.cineclubadministration.Film.service.interf.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/TuCine/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //URL: http://localhost:8080/api/TuCine/v1/categories
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/categories
    //Method: POST
    @Transactional
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryReceiveDto categoryReceiveDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryReceiveDto), HttpStatus.CREATED);
    }

    //URL: http://localhost:8080/api/TuCine/v1/categories/{categoryId}
    //Method: PUT
    @Transactional
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDto> modifyCategory(@PathVariable Long categoryId, @RequestBody CategoryReceiveDto updatedCategory){
        return new ResponseEntity<>(categoryService.modifyCategory(categoryId, updatedCategory), HttpStatus.OK);
    }

    //URL: http://localhost:8080/api/TuCine/v1/categories/{categoryId}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
