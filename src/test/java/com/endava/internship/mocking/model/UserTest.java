package com.endava.internship.mocking.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserTest {
    private final Integer id = 5;
    private final Status status = Status.ACTIVE;
    private final String name = "Alex";
    private final User testUser = new User(id, name, status);

    @Test
    void shouldCreateNewUser() {
        assertAll(
                () -> assertThat(testUser.getId()).isEqualTo(id),
                () -> assertThat(testUser.getName()).isEqualTo(name),
                () -> assertThat(testUser.getStatus()).isEqualTo(status)
        );
    }

    @Test
    void shouldReturnTrueIfEquals() {
        User userCopy = new User(id, name, status);

        assertThat(testUser.equals(userCopy)).isTrue();
    }

    @Test
    void shouldReturnTrueOnEqualsToItself() {
        User userCopy = testUser;
        assertThat(testUser.equals(userCopy)).isTrue();
    }

    @Test
    void shouldReturnFalseOnEqualsToNull() {
        assertThat(testUser.equals(null)).isFalse();
    }

    @Test
    void shouldReturnFalseC() {
        assertThat(testUser.equals("")).isFalse();
    }
}