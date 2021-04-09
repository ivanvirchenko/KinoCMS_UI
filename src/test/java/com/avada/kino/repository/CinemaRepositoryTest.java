package com.avada.kino.repository;

import com.avada.kino.models.Cinema;
import com.avada.kino.models.Hall;
import com.avada.kino.models.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@Slf4j
//@SpringBootTest
//@ActiveProfiles("dev")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CinemaRepositoryTest {
//
//    @Autowired
//    private CinemaRepository repository;
//
//    private final Cinema testCinema = new Cinema(
//            "test cinema", null, null, null, null, null, null
//    );
//    private final Hall testHall = new Hall(
//            "test hall", null, null, null, null
//    );
//    private final Schedule testSchedule = new Schedule(
//            LocalDate.now(), null, null
//    );
//
//    @Test
//    @Order(1)
//    void shouldSaveCinema() {
//        assertDoesNotThrow(() -> repository.save(testCinema));
//    }
//
//    @Test
//    @Order(5)
//    void shouldAddHall() {
//        repository.addHall(1, testHall);
//        assertNotNull(repository.getById(1).getHallsList());
//    }
//
//    @Test
//    @Order(6)
//    void shouldAddSchedule() {
//        repository.addSchedule(1, testSchedule);
//        assertNotNull(repository.getById(1).getSchedules());
//    }
//
//    @Test
//    @Order(2)
//    void shouldReturnCinemaById() {
//        Cinema local_cinema = repository.getById(1);
//        assertNotNull(local_cinema);
//        assertEquals(1, local_cinema.getId());
//        assertEquals(testCinema.getName(), local_cinema.getName());
//    }
//
//    @Test
//    @Order(3)
//    void shouldReturnAllCinemas() {
//        List<Cinema> list = repository.getAll();
//        assertFalse(list.isEmpty());
//        assertEquals(1, list.size());
//    }
//
//    @Test
//    @Order(4)
//    void shouldUpdateCinema() {
//        Cinema local_cinema = repository.getById(1);
//        assertEquals(testCinema.getName(), local_cinema.getName());
//        local_cinema.setName("Updated");
//        repository.update(local_cinema);
//
//        assertEquals("Updated", repository.getById(1).getName());
//    }



//    @Test
//    @Order(7)
//    void shouldDeleteCinema() {
//        assertDoesNotThrow(() -> repository.delete(1));
//        assertThrows(EmptyResultDataAccessException.class, () -> repository.getById(1));
//    }
}
