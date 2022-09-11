package com.dblibrary.service.library;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dblibrary.model.library.Author;
import com.dblibrary.model.library.Book;
import com.dblibrary.model.library.Category;
import com.dblibrary.model.library.dto.BookDTO;
import com.dblibrary.repository.AuthorRepository;
import com.dblibrary.repository.BookRepository;
import com.dblibrary.repository.CategoryRepository;
import com.dblibrary.util.exception.BookException;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	public Optional<Book> addBook(Book book){
//		Si controlla che l'id non venga specificato cosi da non permettere 
//		modifiche forzate tramite la fuznione di aggiunta
		if(book.getIdBook()==null) {
			//Set utilizzati per poi settare con i valori giusti i set di categorie ed autori del libro
			// in quanto modificare i set gia presenti nel libro mentre vengono ciclati i valori, da errore
			Set<Author> authors=new HashSet<>();
			Set<Category> categories=new HashSet<>();
			//si viene a controllare che gli autori passati siano gia esistenti o meno nel database
			//cosi da non creare duplicazioni
			for(Author a:book.getAuthors()) {
				a.setName(a.getName().toLowerCase());
				a.setSurname(a.getSurname().toLowerCase());
				Optional<Author> authorOptional=authorRepository.findByNameAndSurname(a.getName(), a.getSurname());
				//nel caso in cui questo esista viene preso dal database
				if(authorOptional.isPresent()) {
					authors.add(authorOptional.get());
				}
				else {
					//altrimenti viene aggiunto al database
					a.setBooks(new HashSet<>());
					authors.add(a);
					authorRepository.save(a);
				}
			}
			for(Category c:book.getCategories()) {
				c.setName(c.getName().toLowerCase());
				Optional<Category> category=categoryRepository.findByName(c.getName());
				//nel caso in cui questo esista viene preso dal database
				if(category.isPresent()) {
					categories.add(category.get());
				}
				else {
					//altrimenti viene aggiunto al database
					c.setBooks(new HashSet<>());
					categories.add(c);
					categoryRepository.save(c);
				}
			}
			//le liste del libro vengono settate con i valori richiamati/aggiunti al database
			book.setAuthors(authors);
			book.setCategories(categories);
			bookRepository.save(book);
			return Optional.of(book);
		}
		throw new BookException("to add a book you must not specify an id");
	}
	
	public Optional<Book> editBook(Book book){
		//Si controlla che l'id sia presente, cosi da permettere una modifica
		//altrimenti se non indicato avviene un aggiunta
		if(book.getIdBook()!=null) {
			Optional<Book> oldBook=bookRepository.findById(book.getIdBook());
			//se l'id che si passa non richiama nessuno libro si lancia un errore
			if(oldBook.isEmpty()) {
				throw new BookException("the book's id you gave is not present");
			}
			Set<Category> categories=new HashSet<>();
			Set<Author> authors=new HashSet<>();
			
			oldBook.get().setDate(book.getDate());
			oldBook.get().setName(book.getName());
			oldBook.get().setPrice(book.getPrice());
		
			//si viene a controllare che le categorie passati siano esistenti nel database
			for(Category c:book.getCategories()) {
				Optional<Category> category=categoryRepository.findByName(c.getName());
				//se non presente una corrispondeza allora significa che si vuole 
				//aggiungere una categoria non esistente nel sistema, o vie e' stato un errore nella
				//compilazione del nome
				if(category.isEmpty() && c.getIdCategory()!=null) {
					category=categoryRepository.findById(c.getIdCategory());
				}
				//si controlla nel caso in cui l'id passato sia esistente ed il nome passato e'errato, 
				//nel caso l'id abbia una corrispondenza, la categoria giusta viene aggiunta al libro con successo
				if(category.isPresent()) {
					categories.add(category.get());
				}
			}
			//si viene a controllare che gli autori passati siano esistenti nel database
			for(Author a:book.getAuthors()) {
				//se non presente una corrispondeza allora significa che si vuole 
				//aggiungere un autore non esistente nel sistema, o vie e' stato un errore nella
				//compilazione del nome e cognome
				Optional<Author> author=authorRepository.findByNameAndSurname(a.getName(), a.getSurname());
				if(author.isEmpty() && a.getIdAuthor()!=null) {
					author=authorRepository.findById(a.getIdAuthor());
				}
				//si controlla nel caso in cui l'id passato sia esistente ed il nominativo passato e'errato, 
				//nel caso l'id abbia una corrispondenza, l'autore giusto viene aggiunto al libro con successo
				if(author.isPresent()) {
					authors.add(author.get());
				}
			}
			//se il set possiede almeno un autore, allora vuol dire che abbiamo
			//passato almeno un valore esatto
			if(authors.size()>0) {
				oldBook.get().setAuthors(authors);
			}
			if(categories.size()>0) {
				oldBook.get().setCategories(categories);
			}
			//nel caso almeno uno dei due valori abbia zero oggetti, allora abbiamo passato valori sbagliati
			if(authors.size()==0 || categories.size()==0) {
				throw new BookException("the authors or categories you are trying to change are not present in our database");
			}else {
				bookRepository.save(oldBook.get());
				return oldBook;
			}
		}
		throw new BookException("you must specify an id");
	}
	
	public Optional<BookDTO> getBookById(Long id){
		Optional<Book> book=bookRepository.findById(id);
		BookDTO bookDTO=new BookDTO(book.get());
		return Optional.of(bookDTO);
	}
	
	public Optional<Set<BookDTO>> getBookByAuthor(Long idAuthor){
		Optional<Set<Book>> books=bookRepository.findByAuthor(idAuthor);
		Set<BookDTO> booksDTO=new HashSet<>();
		for(Book b:books.get()) {
			booksDTO.add(new BookDTO(b));
		}
		return Optional.of(booksDTO);
	}
	
	public Optional<Set<BookDTO>> getBookByCategory(Integer idCategory){
		Optional<Set<Book>> books=bookRepository.findByCategory(idCategory);
		Set<BookDTO> booksDTO=new HashSet<>();
		for(Book b:books.get()) {
			booksDTO.add(new BookDTO(b));
		}
		return Optional.of(booksDTO);
	}
	
	public void deleteBook(Long id) {
		//se l'id passato non ha una corrispondenza viene lanciato un errore
		if(getBookById(id).isPresent()) {
			bookRepository.delete(bookRepository.findById(id).get());
		}
		throw new BookException("you must specify an id");
	}
	
	public Optional<Set<BookDTO>> getAll(){
		Set<Book> books=Set.copyOf(bookRepository.findAll());
		Set<BookDTO> booksDTO=new HashSet<>();
		for(Book b:books) {
			booksDTO.add(new BookDTO(b));
		}
		return Optional.of(booksDTO);
	}
}
