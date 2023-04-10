package com.example.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.user.Role;
import com.example.user.User;
import com.example.user.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class UserRepositoryTests {
    
    @Autowired
    private UserRepository userRepository; 

    private User user0; 

    @BeforeEach
    void setUp() {

       user0 = User.builder()
        .firstName("Test User 0")
        .lastName("Andreu")
        .password("123456")
        .email("auauau@gmail.com")
        .role(Role.USER)
        .build(); 
    }

    @Test
    @DisplayName("Test para agregar un user")
    public void testAddUser() {

        /**
	 * Segun el enfoque: Una prueba unitaria se divide en tres partes
	 *
	 * 1. Arrange: Setting up the data that is required for this test case
	 * 2. Act: Calling a method or Unit that is being tested.
	 * 3. Assert: Verify that the expected result is right or wrong.
	 *
	 * Segun el enfoque BDD
	 *
	 * 1. given
	 * 2. when
	 * 3. then
	 * */    			
        // given - dado que:

        User user = User.builder()
        .firstName("Test User 1")
        .lastName("Andreu")
        .password("123456")
        .email("hola@gmail.com")
        .role(Role.USER)
        .build(); 

        //when 

        User userAdded = userRepository.save(user); 

        //then

        assertThat(userAdded).isNotNull(); 
        assertThat(userAdded.getId()).isGreaterThan(0L);  

    }

    @DisplayName("Test para listar usuarios")
    @Test
    public void testFindAllUsers() {

        //given 

        User user1 = User.builder()
        .firstName("Test User 1")
        .lastName("Andreu")
        .password("123456")
        .email("hola@gmail.com")
        .role(Role.USER)
        .build(); 

        userRepository.save(user0); 
        userRepository.save(user1); 

        //when

        List<User> usuarios = userRepository.findAll(); 

        //then 

        assertThat(usuarios).isNotNull(); 
        assertThat(usuarios.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("Test para recuperar un user por su id")
    public void findUserById(){
        
        //given
        userRepository.save(user0); 

        //when
        User user = userRepository.findById(user0.getId()).get(); 

        //then 
        assertThat(user.getId()).isNotEqualTo(0L); 

    }

    @Test
    @DisplayName("Test para actualizar un user")
    public void testUpdateUser(){

        //given 
        userRepository.save(user0); 

        //when

        User userGuardado = userRepository.findByEmail(user0.getEmail()).get(); 

        userGuardado.setLastName("Pepito");
        userGuardado.setFirstName("Pepe");
        userGuardado.setEmail("pp@gmail.com");

        User userUpdated = userRepository.save(userGuardado); 

        //then

        assertThat(userUpdated.getEmail()).isEqualTo("pp@gmail.com"); 
        assertThat(userUpdated.getFirstName()).isEqualTo("Pepe"); 
    }

    @DisplayName("Test para elminar un user")
    @Test
    public void testDeleteUser() {

        //given 
        userRepository.save(user0); 

        //when 
        userRepository.delete(user0);
        Optional<User> optionalUser = userRepository.findByEmail(user0.getEmail()); 

        //then
        assertThat(optionalUser).isEmpty(); 

    }
}
