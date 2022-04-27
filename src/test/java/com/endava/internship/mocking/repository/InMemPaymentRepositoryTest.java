package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Payment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

class InMemPaymentRepositoryTest {
    private static final InMemPaymentRepository paymentRepository = new InMemPaymentRepository();
    private static final Payment FIRST_PAYMENT = new Payment(1, 1000., "firstPayment");
    private static final Payment SECOND_PAYMENT = new Payment(2, 2000., "secondPayment");

    @BeforeAll
    static void setUpRepository() {
        paymentRepository.save(FIRST_PAYMENT);
        paymentRepository.save(SECOND_PAYMENT);
    }

    @Test
    void shouldFindPaymentById() {
        assertThat(paymentRepository.findById(FIRST_PAYMENT.getPaymentId()))
                .isEqualTo(Optional.of(FIRST_PAYMENT));
    }

    @Test
    void shouldReturnEmptyIfPaymentWithSuchIdDoesNotExist() {
        UUID wrongId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        assertThat(paymentRepository.findById(wrongId)).isEqualTo(Optional.empty());
    }

    @Test
    void shouldReturnListOfAllPayments() {
        List<Payment> testList = Arrays.asList(FIRST_PAYMENT, SECOND_PAYMENT);
        assertThat(paymentRepository.findAll()).isEqualTo(testList);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionOnSaveNullOrAlreadyExistingPayment() {
        assertThrows(IllegalArgumentException.class,
                () -> paymentRepository.save(null));
        assertThrows(IllegalArgumentException.class,
                () -> paymentRepository.save(FIRST_PAYMENT));
    }

    @Test
    void shouldEditMessage() {
        String editedMessage = "editedMessage";
        assertThat(paymentRepository.editMessage(FIRST_PAYMENT.getPaymentId(), editedMessage).getMessage())
                .isEqualTo(editedMessage);
    }

    @Test
    void shouldThrowNoSuchElementExceptionOnEditNonExistingPayment() {
        UUID wrongId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        assertThrows(NoSuchElementException.class,
                () -> paymentRepository.editMessage(wrongId, ""));
    }
}