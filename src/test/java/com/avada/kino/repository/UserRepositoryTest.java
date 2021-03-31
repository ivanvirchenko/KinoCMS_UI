package com.avada.kino.repository;

import com.avada.kino.enums.Gender;
import com.avada.kino.enums.Language;
import com.avada.kino.models.City;
import com.avada.kino.models.Phone;
import com.avada.kino.models.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    private User testUser = new User(
            "testUser",
            "testLastName",
            "TestLogin",
            "testEmail",
            "password",
            "address",
            "1234567812345678",
            Language.RU,
            Gender.M,
            new Phone("+380501234567"),
            LocalDate.now(),
            "Kiev"
    );

    @Test
    @Order(1)
    void saveUserTest() {
        assertDoesNotThrow(() -> repository.save(testUser));
    }

    @Test
    @Order(2)
    void getAllTest() {
        assertFalse(repository.getAll().isEmpty());
    }

    @Test
    @Order(3)
    void getByIdTest() {
        User user = repository.getById(1);
        assertEquals(testUser.getName(), user.getName());
    }

    @Test
    @Order(4)
    void updateTest() {
        User user = repository.getById(1);
        user.setName("updated");
        repository.update(user);

        assertEquals("updated", repository.getById(1).getName());
    }

    @Test
    @Order(5)
    void deleteTest() {
        repository.delete(1);
        assertNull(repository.getById(1));
    }
}
