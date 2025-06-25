package com.salh.validator;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.salh.config.exception.BadRequestException;
import com.salh.dto.PaymentCreateDTO;
import com.salh.modal.ConstructionMembers;
import com.salh.repository.ConstructionMembersRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentValidator {
	
	private final ConstructionMembersRepository constructionMembersRepository;
	
	public ConstructionMembers validateCreatePayment(PaymentCreateDTO paymentCreateDTO) {
		
		if (Objects.isNull(paymentCreateDTO.getConstructionMemberId())) {
			throw new BadRequestException("member.id.required");
		}
		
		if (Objects.isNull(paymentCreateDTO.getAmount())) {
			throw new BadRequestException("payment.amount.required");
		}
		
		if (Objects.isNull(paymentCreateDTO.getPaymentStatus())) {
			throw new BadRequestException("payment.status.required");
		}
		
		if (Objects.isNull(paymentCreateDTO.getPaymentMode())) {
			throw new BadRequestException("payment.mode.required");
		}
		
		if (StringUtils.isBlank(paymentCreateDTO.getPaymentIntent())) {
			throw new BadRequestException("payment.intent.required");
		}
		
		if (Objects.isNull(paymentCreateDTO.getPaymentDate())) {
			throw new BadRequestException("payment.date.required");
		}
		
		return constructionMembersRepository.findById(paymentCreateDTO.getConstructionMemberId()).orElseThrow(()-> new BadRequestException("member.not.found"));
		
	}

}
