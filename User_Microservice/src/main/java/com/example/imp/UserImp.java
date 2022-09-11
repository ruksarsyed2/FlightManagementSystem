package com.example.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.model.AuthenticationRequest;
import com.example.model.AuthenticationResponse;
import com.example.model.EmailBody;
import com.example.model.User;
import com.example.repository.UserRepo;
import com.example.util.JwtUtil;

@Service
public class UserImp {
	@Autowired
	private UserRepo repoObj;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private RestTemplate restTemplate;

	private static final String EMAIL_URL = "http://localhost:8005/email/send-mail";
	private static final String EMAIL_FORMAT = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}";
	private static final String PASSWORD_FORMAT = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
	private static final String USERNAME_FORMAT = "^(?=[a-zA-Z0-9._]{3,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";

	public List<User> getAll() {

		return repoObj.findAll();

	}

	public Optional<User> getUserById(int id) {
		return repoObj.findById(id);
	}

	public String addUser(User user) {

		if (!user.getEmail().matches(EMAIL_FORMAT)) {
			return "Invalid email format.";
		}

		if (!user.getPassword().matches(PASSWORD_FORMAT)) {
			return "Invalid password format.";
		}

		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);

		if (!user.getUsername().matches(USERNAME_FORMAT)) {
			return "Invalid username format.";
		}

		User anotherUser = repoObj.findByUsername(user.getUsername());

		if (anotherUser != null) {
			return "Username already exists please try again";
		}

		String username = user.getUsername();

		String emailBody = "Dear " + username + ",\n\n"
				+ "Welcome to Flight booking Platform, you've been successfully registered.\n"
				+ "You can now login and begin flight booking at" + " http://localhost:8001/hello/welcome\n\n"
				+ "Thank you,\n" + "Flight booking Platform";

		String emailSubject = "Account Registration Confirmation";

		EmailBody mail = new EmailBody(user.getUsername(), user.getEmail(), emailSubject, emailBody);

		repoObj.save(user);

		restTemplate.postForObject(EMAIL_URL, mail, String.class);

		return "User successfully registered";

	}

	public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password", e);
		}

		final UserDetails userDetails = customUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return new AuthenticationResponse(jwt);
	}

	public User getUserByUsername(String username) {
		return repoObj.findByUsername(username);
	}

	public User updateUser(User obj) {
		return repoObj.save(obj);
	}

	public String deleteUser(int id) {
		repoObj.deleteById(id);
		return "User is deleted";
	}

}