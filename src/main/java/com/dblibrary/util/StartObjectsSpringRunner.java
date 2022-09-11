package com.dblibrary.util;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.dblibrary.model.auth.Role;
import com.dblibrary.model.auth.Roles;
import com.dblibrary.model.auth.User;
import com.dblibrary.model.library.Author;
import com.dblibrary.model.library.Book;
import com.dblibrary.model.library.Category;
import com.dblibrary.repository.RoleRepository;
import com.dblibrary.repository.UserRepository;
import com.dblibrary.service.library.AuthorService;
import com.dblibrary.service.library.BookService;
import com.dblibrary.service.library.CategoryService;

@Component
public class StartObjectsSpringRunner implements ApplicationRunner {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	AuthorService authorService;
	
	@Autowired

	@Override
	public void run(ApplicationArguments args) throws Exception {
		BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
//		Role role = new Role();
//		role.setRoleName(Roles.ROLE_ADMIN);
//		User user = new User();
//		Set<Role> roles = new HashSet<>();
//		roles.add(role);
//		user.setUserName("admin");
//		user.setPassword(bCrypt.encode("admin"));
//		user.setEmail("admin@domain.com");
//		user.setRoles(roles);
//		user.setActive(true);
//
//		roleRepository.save(role);
//		userRepository.save(user);
//
//		Author firstAuthor = new Author();
//		firstAuthor.setName("Gianni");
//		firstAuthor.setSurname("Ciano");
//		authorService.addAuthor(firstAuthor);
//
//		Set<Author> authors = new HashSet<>();
//		authors.add(firstAuthor);
//
//		Category firstCategory = new Category();
//		firstCategory.setName("horror");
//		categoryService.addCategory(firstCategory);
//
//		Set<Category> categories = new HashSet<>();
//		categories.add(firstCategory);
//
//		Book firstBook = new Book();
//		firstBook.setAuthors(authors);
//		firstBook.setCategories(categories);
//		firstBook.setPrice(13.20);
//		firstBook.setName("First");
//		firstBook.setDate(new Date(03, 03, 03));
//		bookService.addBook(firstBook);
	}

}
