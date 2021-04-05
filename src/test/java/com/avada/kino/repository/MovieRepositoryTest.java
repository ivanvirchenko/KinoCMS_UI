package com.avada.kino.repository;

import com.avada.kino.models.Movie;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository repository;

    @Test
    void saveTest() {
        repository.save(new Movie(
                "Новое имя", "Новое описание",
                null, null, null, null,
                new Date(), new Date()
        ));
    }
}
