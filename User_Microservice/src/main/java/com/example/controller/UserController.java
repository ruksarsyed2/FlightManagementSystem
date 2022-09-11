package com.example.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.imp.CustomUserDetailsService;
import com.example.imp.UserImp;
import com.example.model.AuthenticationRequest;
import com.example.model.AuthenticationResponse;
import com.example.model.Flight;
import com.example.model.User;
import com.example.util.JwtUtil;

@RestController
@RequestMapping("/hello")
public class UserController {
	@Autowired
	private UserImp userservice;

	@Autowired
	private JwtUtil jwtUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/getall")
	public List<User> getAll() {
		return userservice.getAll();

	}

	@GetMapping(value = "/getuser/{id}")
	public Optional<User> getUserById(@PathVariable("id") int id) {
		return userservice.getUserById(id);
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "<h1>Welcome to FlightBooking Platform</h1>";
	}

	@PostMapping("/authenticate")
	public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest jwtRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

		final String token = jwtUtility.generateToken(userDetails);

		return new AuthenticationResponse(token);
	}

	@GetMapping("/get-user-by-username/{username}")
	public User getUserByUsername(@PathVariable("username") String username) {
		return userservice.getUserByUsername(username);
	}

	@PostMapping("/register")
	public String addUser(@RequestBody User obj) {
		return userservice.addUser(obj);
	}

	@PostMapping("/login")
	public AuthenticationResponse getLoginMessage(@RequestBody AuthenticationRequest authenticationRequest) {
		return userservice.login(authenticationRequest);
	}

	@PutMapping("/updateuser")
	public ResponseEntity<User> updateUser(@RequestBody User student) {
		student = userservice.updateUser(student);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

	@DeleteMapping("/deleteuser")
	public ResponseEntity<String> deleteUser(@RequestParam("id") int id) {
		String response = userservice.deleteUser(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/findbyData/{date}/{arrivalLoc}/{departureLoc}")
	public Flight[] getFlightByDateAndArrivalLocAndDepartureLoc(
			@PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,@PathVariable String arrivalLoc,@PathVariable String departureLoc){
		Flight[] flight=restTemplate.getForObject("http://localhost:8081/flight/findbyData/"+date+"/"+arrivalLoc+"/"+departureLoc, Flight[].class)	;
		return flight;
	}
//	@GetMapping("/find/{departure_location}/{arrival_location}")
//	public Flight[] getFlightByData(@PathVariable("departure_location") String dep_loc,@PathVariable("arrival_location") String arr_loc) throws InterruptedException{
//		Flight[] flight=restTemplate.getForObject("https://Flight-Search/Search/find/"+dep_loc+"/"+arr_loc, Flight[].class)	;
//		Thread.sleep(3000);
//		return flight;	
//	}
}