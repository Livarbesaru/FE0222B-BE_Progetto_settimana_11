package com.dblibrary.service.library;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dblibrary.model.library.Author;
import com.dblibrary.model.library.dto.AuthorDTO;
import com.dblibrary.repository.AuthorRepository;
import com.dblibrary.util.exception.AuthorException;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	public Optional<AuthorDTO> addAuthor(Author author) {
//		Si controlla se viene passato l'id,
//		cosi da impedire modifiche forzate tramite la funzione di aggiunta
		if(author.getIdAuthor()==null) {
			//Possibili libri aggiunti nell'istanza vengono 
			//eliminati cosi da non permettere duplicazioni
			author.setBooks(new HashSet<>());
			author.setName(author.getName().toLowerCase());
			author.setSurname(author.getSurname().toLowerCase());
			Optional<Author> cat = authorRepository.findByNameAndSurname(author.getName(),author.getSurname());
			if (cat.isPresent()) {
				// se si cerca di aggiungere due volte lo stesso autore
				//la funzione si chiude consegnando indietro l'autore gia' esistente
				return Optional.of(new AuthorDTO(cat.get()));
			} else {
				//Se l'autore non esiste la chiamata va a buon fine e si riceve indietro l'autore
				authorRepository.save(author);
				return Optional.of(new AuthorDTO(author));
			}
		}else {
			throw new AuthorException("to add an author you must not specify an id");
		}
	}

	public Optional<AuthorDTO> editAuthor(Author author) {
		//Si controlla se un id viene passato cosi
		//da impedire aggiunte forzate tramite la funzione di modifica
		if(author.getIdAuthor()!=null) {
			author.setName(author.getName().toLowerCase());
			author.setSurname(author.getSurname().toLowerCase());
			Optional<Author> aut = authorRepository.findById(author.getIdAuthor());
			//Se l'id passato non reindirizza a nessun autore, viene lanciato in un errore
			if(aut.isEmpty()) {
				throw new AuthorException("the id you gave is not present");
			}
			Optional<Author> autNameSurname = authorRepository.findByNameAndSurname(author.getName(),author.getSurname());
			//si controlla se gia' esiste un autore con il nome e cognome che si vuole modificare
			//se l'id dell'autore che si vuole passare e' uguale a quello con nome e cognome corrispondente
			//allora vuol dire che stiamo modificando lo stesso autore e non cercando di doppiare i nominativi
			if (aut.isPresent() && autNameSurname.isPresent()) {
				if(aut.get().getIdAuthor() == autNameSurname.get().getIdAuthor()) {
					authorRepository.save(aut.get());
					return Optional.of(new AuthorDTO(aut.get()));
				}
			} else if(aut.isPresent() && autNameSurname.isEmpty()) {
				aut.get().setName(author.getName());
				aut.get().setSurname(author.getSurname());
				authorRepository.save(aut.get());
				return Optional.of(new AuthorDTO(aut.get()));
			}
			throw new AuthorException("there is already an author with declared name and surname that does not correspond with the one you are editing");
		}
		throw new AuthorException("specify author's id");
	}

	public String deleteAuthor(Long id) {
		Optional<Author> author = authorRepository.findById(id);
		//viene controllato se l'id passato corrisponda ad uno esistente
		if (author.isPresent()) {
			if (authorRepository.deleteAuthorBook(id) > 0 && authorRepository.deleteAuthor(id) > 0) {
				return (author.get().getName()+" "+author.get().getSurname() + " deleted");
			}
		}
		throw new AuthorException("Error during the deletion");
	}

	public Optional<Set<AuthorDTO>> getAll() {
		Set<AuthorDTO> authors = new HashSet<>();
		for (Author a : authorRepository.findAll()) {
			authors.add(new AuthorDTO(a));
		}
		return Optional.of(authors);
	}

	public Optional<AuthorDTO> getById(Long id) {
		return Optional.of(new AuthorDTO(authorRepository.findById(id).get()));
	}
}
