package com.salh.dto;

import java.util.UUID;

import com.salh.enums.PaymentDocumentTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDocumentUploadRequestDTO {

	private UUID paymentId;
	private PaymentDocumentTypeEnum documentType;
	
}
