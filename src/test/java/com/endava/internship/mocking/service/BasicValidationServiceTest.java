package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BasicValidationServiceTest {

    static BasicValidationService testService;

    @BeforeAll
    static void setUp() {
        testService = new BasicValidationService();
    }

    @Test
    void shouldThrowIllegalArgumentExceptionOnNullAmount() {
        assertThrows(IllegalArgumentException.class, () -> testService.validateAmount(null));
    }

    @ParameterizedTest(name = "validateAmount({0})")
    @ValueSource(doubles = {0, -2.5})
    void shouldThrowIllegalArgumentExceptionOnAmountLessOrEqualToZero(double amount) {
        assertThrows(IllegalArgumentException.class, () -> testService.validateAmount(amount));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfPaymentIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> testService.validatePaymentId(null));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfUserIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> testService.validateUserId(null));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfUserIsNotActive() {
        User userMock = mock(User.class, "userMock");
        when(userMock.getStatus()).thenReturn(Status.INACTIVE);

        assertThrows(IllegalArgumentException.class, () -> testService.validateUser(userMock));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfMessageIsNull() {
        assertThrows(IllegalArgumentException.class, () -> testService.validateMessage(null));
    }
}