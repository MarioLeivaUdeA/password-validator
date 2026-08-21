package com.example;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.stream.IntStream;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/** Basic performance tests for password validation. */
class PasswordValidatorPerformanceTest {

    private final PasswordValidator validator = new PasswordValidator();

    @Test
    void singleValidationShouldBeFast() {
        assertTimeout(Duration.ofMillis(10), () -> validator.isValid("Abc123!@#"));
    }

    @RepeatedTest(10)
    void multipleValidationsShouldBeFast() {
        String[] passwords = IntStream.range(0, 1000)
                .mapToObj(index -> generateRandomPassword())
                .toArray(String[]::new);
        long start = System.nanoTime();
        for (String password : passwords) {
            validator.isValid(password);
        }
        long duration = System.nanoTime() - start;
        double milliseconds = duration / 1_000_000.0;
        assertTrue(milliseconds < 1,
                "Tiempo excedido: " + milliseconds + " ms para 1000 validaciones");
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder(10);
        for (int index = 0; index < 10; index++) {
            int characterIndex = (int) (Math.random() * chars.length());
            password.append(chars.charAt(characterIndex));
        }
        return password.toString();
    }
}