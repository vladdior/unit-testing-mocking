package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock(name = "userRepositoryMock")
    private UserRepository userRepositoryMock;

    @Mock(name = "paymentRepositoryMock")
    private PaymentRepository paymentRepositoryMock;

    @Mock(name = "validationServiceMock")
    private ValidationService validationServiceMock;

    @InjectMocks
    private PaymentService testPaymentService;

    @Captor
    ArgumentCaptor<Payment> paymentCaptor;

    @Test
    void shouldCreatePayment() {
        Double amount = 20.;
        Integer userId = 100;
        String userName = "Alex";
        String paymentMessage = "Payment from user " + userName;
        User userMock = mock(User.class);

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userMock));
        when(userMock.getId()).thenReturn(userId);
        when(userMock.getName()).thenReturn(userName);

        testPaymentService.createPayment(userId, amount);

        verify(validationServiceMock).validateUserId(userId);
        verify(validationServiceMock).validateAmount(amount);
        verify(validationServiceMock).validateUser(userMock);

        verify(paymentRepositoryMock).save(paymentCaptor.capture());
        Payment capturedPayment = paymentCaptor.getValue();

        assertThat(capturedPayment.getUserId()).isEqualTo(userId);
        assertThat(capturedPayment.getMessage()).isEqualTo(paymentMessage);
    }

    @ParameterizedTest(name = "createPayment({0}, amount)")
    @ValueSource(ints = {-100})
    void shouldThrowNoSuchElementExceptionIfUserNotFound(Integer wrongId) {
        assertThrows(NoSuchElementException.class, () -> testPaymentService.createPayment(wrongId, 20.));
    }

    @Test
    void shouldEditMessage() {
        UUID payId = UUID.randomUUID();
        String newMessage = "message";
        Payment returnMock = mock(Payment.class, "returnMock");

        when(paymentRepositoryMock.editMessage(payId, newMessage)).thenReturn(returnMock);

        assertThat(testPaymentService.editPaymentMessage(payId, newMessage)).isEqualTo(returnMock);

        verify(validationServiceMock).validatePaymentId(payId);
        verify(validationServiceMock).validateMessage(newMessage);
        verify(paymentRepositoryMock).editMessage(payId, newMessage);
    }

    @Test
    void shouldGetAllByAmountExceeding() {
        Double amountToExceed = 10.;
        Payment validPaymentMock = mock(Payment.class, "validMock");
        Payment invalidPaymentMock = mock(Payment.class, "invalidMock");
        List<Payment> paymentList = Arrays.asList(validPaymentMock, invalidPaymentMock, validPaymentMock);

        when(paymentRepositoryMock.findAll()).thenReturn(paymentList);
        when(validPaymentMock.getAmount()).thenReturn(225.5);
        when(invalidPaymentMock.getAmount()).thenReturn(0.0);

        assertThat(testPaymentService.getAllByAmountExceeding(amountToExceed))
                .containsExactly(validPaymentMock, validPaymentMock);

        verify(paymentRepositoryMock).findAll();
    }
}
