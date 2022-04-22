package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InMemUserRepositoryTest {
    InMemUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemUserRepository();
    }

    @Test
    void shouldFindUserByIdOrReturnEmpty() {
        User testUser = new User(2, "Maria", Status.ACTIVE);

        assertThat(userRepository.findById(2)).hasValue(testUser);
        assertThat(userRepository.findById(6)).isEmpty();
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfUserIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.findById(null));
    }
}