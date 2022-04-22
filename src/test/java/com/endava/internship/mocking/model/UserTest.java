package com.endava.internship.mocking.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User testUser;
    private Integer id;
    private Status status;
    private String name;

    @BeforeEach
    void setUp() {
        id = 5;
        status = Status.ACTIVE;
        name = "Alex";

        testUser = new User(id, name, status);
    }

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