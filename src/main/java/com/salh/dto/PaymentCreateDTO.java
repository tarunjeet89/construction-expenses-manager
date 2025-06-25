package com.salh.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.salh.enums.PaymentModeEnum;
import com.salh.enums.PaymentStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateDTO {
	
	private UUID constructionMemberId;
    private BigDecimal amount;
	private PaymentStatusEnum paymentStatus;
	private PaymentModeEnum paymentMode;
	private Date paymentDate;
	private String paymentIntent;

}
