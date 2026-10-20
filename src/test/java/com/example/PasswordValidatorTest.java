package com.example;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Unit tests for {@link PasswordValidator}, organized using AAA. */
class PasswordValidatorTest {

    private final PasswordValidator validator = new PasswordValidator();

    @Test
    void validPasswordShouldReturnTrue() {
        String password = "Abc123!@#";
        boolean result = validator.isValid(password);
        assertTrue(result, "La contraseña debería ser válida");
    }

    @Test
    void shortPasswordShouldReturnFalse() {
        String password = "Ab1!";
        boolean result = validator.isValid(password);
        assertFalse(result, "Contraseña corta debe ser inválida");
    }

    @Test
    void missingSpecialCharShouldReturnFalse() {
        String password = "Abc12345";
        boolean result = validator.isValid(password);
        assertFalse(result, "Falta carácter especial");
    }

    @Test
    void strengthScoreForValidPasswordShouldBe5() {
        String password = "Abc123!@#";
        int score = validator.getStrengthScore(password);
        assertEquals(5, score, "Debe tener puntaje máximo");
    }

    @Test
    void strengthScoreShouldCountOnlySatisfiedRules() {
        assertAll(
                () -> assertEquals(4, validator.getStrengthScore("Ab1!")),
                () -> assertEquals(2, validator.getStrengthScore("abcdefgh")),
                () -> assertEquals(2, validator.getStrengthScore("ABCDEFGH")),
                () -> assertEquals(3, validator.getStrengthScore("Abcdefgh")),
                () -> assertEquals(4, validator.getStrengthScore("Abcdefg1")));
    }

    @Test
    void nullPasswordShouldReturnFalseAndScoreZero() {
        String password = null;
        boolean isValid = validator.isValid(password);
        int score = validator.getStrengthScore(password);
        assertAll(
                () -> assertFalse(isValid),
                () -> assertEquals(0, score));
    }
}