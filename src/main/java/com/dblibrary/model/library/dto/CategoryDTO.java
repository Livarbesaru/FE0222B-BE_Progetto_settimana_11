package com.dblibrary.model.library.dto;

import java.util.HashMap;
import java.util.Map;

import com.dblibrary.model.library.Book;
import com.dblibrary.model.library.Category;

import lombok.Getter;

@Getter
public class CategoryDTO {
	private Integer idCategory;
	private String name;
	private Map<Long,String> books;
	
	public CategoryDTO() {
	}
	
	public CategoryDTO(Category category) {
		this.idCategory=category.getIdCategory();
		this.name=category.getName();
		this.books=new HashMap<>();
		if(category.getBooks()!=null) {
			for(Book b:category.getBooks()) {
				books.put(b.getIdBook(), b.getName());
			}
		}
	}
}
