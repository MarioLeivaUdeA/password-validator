package com.example;

import java.util.regex.Pattern;

/** Validates password complexity and calculates a strength score. */
public class PasswordValidator {

    private static final int MIN_LENGTH = 8;
    private static final Pattern UPPER_CASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWER_CASE = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]");

    /**
     * Checks whether a password satisfies all complexity requirements.
     *
     * @param password password to validate
     * @return true when the password is valid
     */
    public boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= MIN_LENGTH
                && UPPER_CASE.matcher(password).find()
                && LOWER_CASE.matcher(password).find()
                && DIGIT.matcher(password).find()
                && SPECIAL.matcher(password).find();
    }

    /**
     * Calculates one point for each satisfied complexity requirement.
     *
     * @param password password to score
     * @return score from zero to five
     */
    public int getStrengthScore(String password) {
        if (password == null) {
            return 0;
        }
        int score = 0;
        if (password.length() >= MIN_LENGTH) {
            score++;
        }
        if (UPPER_CASE.matcher(password).find()) {
            score++;
        }
        if (LOWER_CASE.matcher(password).find()) {
            score++;
        }
        if (DIGIT.matcher(password).find()) {
            score++;
        }
        if (SPECIAL.matcher(password).find()) {
            score++;
        }
        return score;
    }
}