package com.avada.kino.repository;

import com.avada.kino.models.News;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NewsRepositoryTest {
    @Autowired
    private NewsRepository repository;
    private News newsEnabled = new News(
            "Enabled", null, null, null, true, LocalDate.now(), null
    );
    private News newsDisabled = new News(
            "disabled", null, null, null, false, LocalDate.now(), null
    );

    @Test
    @Order(1)
    void shouldSaveNews() {
        assertDoesNotThrow(() -> repository.save(newsEnabled));
        assertDoesNotThrow(() -> repository.save(newsDisabled));
    }

    @Test
    @Order(2)
    void shouldReturnAllNews() {
        List<News> news = repository.getAll();

        assertFalse(news.isEmpty());
        assertEquals(2, news.size());
    }

    @Test
    @Order(3)
    void shouldReturnOnlyEnabledNews() {
        List<News> news = repository.getEnabled(true);

        for (News n : news) {
            assertTrue(n.isEnabled());
        }
    }

    @Test
    @Order(4)
    void shouldReturnOnlyNotEnabledNews() {
        List<News> news = repository.getEnabled(false);

        for (News n : news) {
            assertFalse(n.isEnabled());
        }
    }

    @Test
    @Order(5)
    void shouldReturnNewsById() {
        assertEquals(newsEnabled.getName(), repository.getById(1).getName());
    }

    @Test
    @Order(6)
    void shouldUpdateNews() {
        News news = repository.getById(1);
        news.setName("updated");
        repository.update(news);

        assertEquals("updated", repository.getById(1).getName());
    }

    @Test
    @Order(7)
    void shouldDeleteNews() {
        repository.delete(1);

        assertNull(repository.getById(1));
    }
}
