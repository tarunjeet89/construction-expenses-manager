package com.salh.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerPaymentDTO {
	
	private String name;
	private String phoneNumber;
    private String amount;
	private String paymentMode;
	private Date paymentDate;
	private String paymentIntent;
	private String totalAmount;

}
