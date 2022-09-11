
package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.imp.CustomUserDetailsService;
import com.example.imp.UserImp;
import com.example.model.User;
import com.example.repository.UserRepo;

@SpringBootTest
class UserMicroserviceApplicationTests {

	@MockBean
	private UserRepo repository;
	@Autowired
	private UserImp service;

	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	

	
	
	@Test
	@Order(1)
	void TestgetAll() {
		Mockito.when(repository.findAll())
				.thenReturn(Stream
						.of(new User(1, "ruksar", "user123@gmail.com", "ruksar234", "766532241","ADMIN"),
								new User(2, "ruksar", "user123@gmail.com", "ruksar234", "766532241","ADMIN"))
						.collect(Collectors.toList()));
		assertEquals(2, service.getAll().size());
	}
	
	@Test
	@Order(2)
	void TestupdateUser() {
		User user = new User(1, "ruksar", "user123@gmail.com", "ruksar234", "766532241","ADMIN");
		Mockito.when(repository.save(user)).thenReturn(user);
		assertEquals(user, service.updateUser(user));
	}


	
	@Test
	@Order(3)
	void TestdeleteUser() {
		assertEquals("User is deleted", service.deleteUser(1));
	}
	
	@Test
	@Order(5)
	void testSetPhno() {
		User u = new User();
		u.setPhno("7686979765");
		assertEquals("7686979765", u.getPhno());
	}

	@Test
	@Order(6)
	void testSetRole() {
		User u = new User();
		u.setRole("customer");
		assertEquals("customer", u.getRole());
	}

	
	@Test
	@Order(7)
	void testSetEmail() {
		User u = new User();
		u.setEmail("ruma@gmail.com");
		assertEquals("ruma@gmail.com", u.getEmail());
	}

	@Test
	@Order(8)
	void testSetUsername() {
		User u = new User();
		u.setUsername("ruma");
		assertEquals("ruma", u.getUsername());
	}

	@Test
	@Order(9)
	void testgetUserById() {
		int id = 1;
		Optional<User> u = Optional.ofNullable(new User(1, "ruksar", "user123@gmail.com", "ruksar234", "766532241","ADMIN"));
		when(repository.findById(id))
				.thenReturn(Optional.ofNullable(new User(1, "ruksar", "user123@gmail.com", "ruksar234", "766532241","ADMIN")));
		assertEquals(u.get().getId(), service.getUserById(id).get().getId());

	}
	

	@Test
	@Order(10)
	void TestupdateUserId() {
		User userupd = new User(1, "ruksar", "user123@gmail.com", "ruksar234", "766532241","ADMIN");
		repository.save(userupd);
		userupd.setId(2);
		repository.save(userupd);
		assertEquals(2, userupd.getId());
	}
	@Test
	@Order(11)
	void TestFindByUsername() {
		User user = new User(1, "ruksar", "user123@gmail.com", "ruksar234", "766532241","admin");
		Mockito.when(repository.findByUsername("ruksar")).thenReturn(user);
		assertEquals(user,service.getUserByUsername("ruksar"));
	}
	
	@Test
	@Order(12)
	void testSetPassword() {
		User u = new User();
		u.setPassword("7686979765");
		assertEquals("7686979765", u.getPassword());
	}
	
	
	@Test
	@Order(13)
	void testpasswordEncoder() {
		User user = new User(1, "ruksar", "user123@gmail.com", "ruksar234", "766532241","admin");
		String encodedPassword = bCryptPasswordEncoder.encode("password");
		user.setPassword(encodedPassword);
		Mockito.when(repository.save(user)).thenReturn(user);
		
		assertEquals(encodedPassword, user.getPassword());
	}
}

//@Test
//@Order(1)
//void TestaddUser() {
//	User user = new User(1, "ruksar", "user123@gmail.com", "ruksar234", "766532241","ADMIN");
//	Mockito.when(repository.save(user)).thenReturn(user);
//	assertEquals(user, service.addUser(user));
//}