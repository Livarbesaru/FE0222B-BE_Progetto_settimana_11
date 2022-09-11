package com.dblibrary.service.library;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dblibrary.model.library.Category;
import com.dblibrary.model.library.dto.CategoryDTO;
import com.dblibrary.repository.CategoryRepository;
import com.dblibrary.util.exception.CategoryException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public Optional<CategoryDTO> addCategory(Category category) {
		// si controlla che non sia presente un id cosi da non permettere modifiche
		if (category.getIdCategory() == null) {
			category.setBooks(new HashSet<>());
			category.setName(category.getName().toLowerCase());
			Optional<Category> cat = categoryRepository.findByName(category.getName());
			// se esiste gia' una categoria con il nome che stiamo passando
			// la fuznione viene troncata e viene restituita la categoria gia esistente
			if (cat.isPresent()) {
				return Optional.of(new CategoryDTO(cat.get()));
			} else {
				categoryRepository.save(category);
				return Optional.of(new CategoryDTO(category));
			}
		} else {
			throw new CategoryException("to add a Category you must not specify an id");
		}
	}

	public Optional<CategoryDTO> editCategory(Category category) {
		// si controlla che l'id sia presente cosi da non permettere aggiunte se questo
		// viene a mancare
		if (category.getIdCategory() != null) {
			category.setName(category.getName().toLowerCase());
			Optional<Category> cat = categoryRepository.findById(category.getIdCategory());
			Optional<Category> catName = categoryRepository.findByName(category.getName());
			// se l'id che stiamo passando non ha una corrispondenza viene lanciato un
			// errore
			if (cat.isEmpty()) {
				throw new CategoryException("the category's id you gave is not present");
			}
			if (cat.isPresent() && catName.isEmpty()) {
				// nel caso in cui l'id abbia corrispondenza ed il nome non sia gia' in utilizzo
				// da un altro
				cat.get().setName(category.getName().toLowerCase());
				categoryRepository.save(cat.get());
				return Optional.of(new CategoryDTO(cat.get()));
			}
			// nel caso in cui l'id abbia corrispondenza ed il nome sia gia' in utilizzo,
			// viene controllato se la categoria con nome gia' esistente sia la stessa che
			// stiamo
			// cercando di modificare
			else if (cat.isPresent() && catName.isPresent()) {
				if (cat.get().getIdCategory() == catName.get().getIdCategory()) {
					categoryRepository.save(cat.get());
					return Optional.of(new CategoryDTO(cat.get()));
				}
				throw new CategoryException("the category's name you gave is already present");
			}
		}
		throw new CategoryException("you must specify an id");
	}

	public String deleteCategory(Integer id) {
		Optional<Category> category = categoryRepository.findById(id);
		// viene controllato che l'id passato abbia una corrispondenza
		if (category.isPresent()) {
			if (categoryRepository.deleteCategoryBook(id) > 0 && categoryRepository.deleteCategory(id) > 0) {
				return (category.get().getName() + " eliminato");
			}
			throw new CategoryException("error while deletion was happening");
		}
		throw new CategoryException("you must specify an existing id");
	}

	public Optional<Set<CategoryDTO>> getAll() {
		Set<CategoryDTO> categories = new HashSet<>();
		for (Category c : categoryRepository.findAll()) {
			categories.add(new CategoryDTO(c));
		}
		return Optional.of(categories);
	}

	public Optional<CategoryDTO> getById(Integer id) {
		return Optional.of(new CategoryDTO(categoryRepository.findById(id).get()));
	}
}
