package com.sudo.io;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
public class PaymentDetails
{
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    public enum PaymentStatus{PENDING,COMPLETED,FAILED}
}
