package com.avada.kino.repository;

import com.avada.kino.models.Promotion;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PromotionRepositoryTest {

    @Autowired
    private PromotionRepository repository;
    private final Promotion enabledPromotion = new Promotion(
            "enabledPromotion", null, null, null, true, null, null
    );
    private final Promotion disabledPromotion = new Promotion(
            "disabledPromotion", null, null, null, false, null, null
    );

    @Test
    @Order(1)
    void shouldSavePromotion() {
        assertDoesNotThrow(() -> repository.save(enabledPromotion));
        assertDoesNotThrow(() -> repository.save(disabledPromotion));
    }

    @Test
    @Order(2)
    void shouldReturnAllPromotion() {
        List<Promotion> promotions = repository.getAll();

        assertFalse(promotions.isEmpty());
        assertEquals(2, promotions.size());
    }

    @Test
    @Order(3)
    void shouldReturnOnlyEnabledPromotion() {
        List<Promotion> promotions = repository.getEnabled(true);

        assertFalse(promotions.isEmpty());
        assertEquals(1, promotions.size());
        for (Promotion promotion : promotions) {
            assertTrue(promotion.isEnabled());
        }
    }

    @Test
    @Order(4)
    void shouldReturnOnlyNotEnabledPromotion() {
        List<Promotion> promotions = repository.getEnabled(false);

        assertFalse(promotions.isEmpty());
        assertEquals(1, promotions.size());
        for (Promotion promotion : promotions) {
            assertFalse(promotion.isEnabled());
        }
    }

    @Test
    @Order(5)
    void shouldReturnPromotionById() {
        Promotion promotion = repository.getById(1);

        assertEquals(1, promotion.getId());
        assertEquals(enabledPromotion.getName(), promotion.getName());
    }

    @Test
    @Order(6)
    void shouldUpdatePromotion() {
        Promotion promotion = repository.getById(1);
        promotion.setName("updated");
        repository.update(promotion);

        assertEquals("updated", repository.getById(1).getName());
    }

    @Test
    @Order(7)
    void shouldDeletePromotion() {
        repository.delete(1);

        assertNull(repository.getById(1));
    }
}
