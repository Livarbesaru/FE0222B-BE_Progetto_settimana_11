package com.dblibrary.model.library;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idBook;
	
	@Column(nullable = false)
	private String name;
	
	
	@ManyToMany
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCategory")
	@JoinTable(name="category_book",
	joinColumns = @JoinColumn(name="book_id", referencedColumnName = "idbook"),
	inverseJoinColumns = @JoinColumn(name="category_id", referencedColumnName = "idcategory"))
	private Set<Category> categories;
	
	@ManyToMany
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idAuthor")
	@JoinTable(name="author_book",
	joinColumns = @JoinColumn(name="book_id", referencedColumnName = "idbook"),
	inverseJoinColumns = @JoinColumn(name="author_id", referencedColumnName = "idauthor"))
	private Set<Author> authors;
	
	@Column(nullable = false)
	private double price;
	
	@Column
	private Date date;
}
