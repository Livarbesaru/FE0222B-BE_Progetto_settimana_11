package com.dblibrary.controller.library;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dblibrary.model.library.Category;
import com.dblibrary.model.library.dto.CategoryDTO;
import com.dblibrary.service.library.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To add a category do not specify an id or it will throw an error,"
			+ " there can't be categories with same name")
	public ResponseEntity<CategoryDTO> addCategory(@RequestBody Category category){
		return new ResponseEntity<CategoryDTO>(categoryService.addCategory(category).get(),HttpStatus.OK);
	}
	
	@PutMapping("/edit")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To edit a category do specify an id or it will throw an error,"
			+ "	there can't be categories with same name")
	public ResponseEntity<CategoryDTO> editCategory(@RequestBody Category category){
		return new ResponseEntity<CategoryDTO>(categoryService.editCategory(category).get(),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To delete a category do specify an id or it will throw an error")
	public ResponseEntity<String> editCategory(@PathVariable Integer id){
		return new ResponseEntity<String>(categoryService.deleteCategory(id),HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	@Operation(summary = "To get a category do specify an id or it will throw an error")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer id){
		return new ResponseEntity<CategoryDTO>(categoryService.getById(id).get(),HttpStatus.OK);
	}
	
	@GetMapping("/get/all")
	public ResponseEntity<Set<CategoryDTO>> getAllCategory(){
		return new ResponseEntity<Set<CategoryDTO>>(categoryService.getAll().get(),HttpStatus.OK);
	}
	
}
