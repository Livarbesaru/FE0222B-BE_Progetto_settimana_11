package com.dblibrary.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dblibrary.model.auth.User;
import com.dblibrary.model.service.UserDetailsImpl;
import com.dblibrary.repository.UserRepository;

//Classe per recuperare i dati dell'utente
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional //Può effettuare più chiamate al database e vogliamo essere sicuri
	//che vengano gestite come transazioni, annullando l'intera operazione (rollback) in caso di errori
	
	//Metodo serve per trovare l'user se è presente e quindi registrato
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Optional<User> user = userRepository.findByUserName(userName);

		if (user.isPresent()) {
			return UserDetailsImpl.build(user.get());
		} else {
			throw new UsernameNotFoundException("User Not Found with username: " + userName);
		}
	}
}
