package com.endava.internship.mocking.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class PaymentTest {
    private final Integer userId = 1;
    private final Double amount = 5.;
    private final String message = "message";
    private final Payment testPayment = new Payment(userId, amount, message);

    @Test
    void shouldCreateNewPayment() {
        assertAll(
                () -> assertThat(testPayment.getUserId()).isEqualTo(userId),
                () -> assertThat(testPayment.getMessage()).isEqualTo(message),
                () -> assertThat(testPayment.getAmount()).isEqualTo(amount)
        );
    }

    @Test
    void shouldCreateCopyOfPayment() {
        Payment paymentCopy = Payment.copyOf(testPayment);

        assertAll(
                () -> assertThat(paymentCopy.getPaymentId()).isEqualTo(testPayment.getPaymentId()),
                () -> assertThat(paymentCopy.getUserId()).isEqualTo(testPayment.getUserId()),
                () -> assertThat(paymentCopy.getAmount()).isEqualTo(testPayment.getAmount())
        );
        assertThat(paymentCopy).isNotSameAs(testPayment);
    }

    @Test
    void shouldReturnTrueIfEquals() {
        Payment paymentCopy = Payment.copyOf(testPayment);

        assertThat(testPayment.equals(paymentCopy)).isTrue();
    }

    @Test
    void shouldReturnTrueOnEqualsToItself() {
        Payment paymentCopy = testPayment;
        assertThat(testPayment.equals(paymentCopy)).isTrue();
    }

    @Test
    void shouldReturnFalseOnEqualsToNull() {
        assertThat(testPayment.equals(null)).isFalse();
    }

    @Test
    void shouldReturnFalseOnEqualsToOtherClassInstance() {
        assertThat(testPayment.equals("")).isFalse();
    }

}