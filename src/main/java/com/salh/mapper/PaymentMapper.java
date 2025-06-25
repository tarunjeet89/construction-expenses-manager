package com.salh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.salh.dto.PaymentCreateDTO;
import com.salh.dto.PaymentResponseDTO;
import com.salh.modal.Payments;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
	
	@Mapping(ignore = true, target = "constructionMember")
	Payments mapPaymentCreateDTOToPayment(PaymentCreateDTO paymentCreateDTO);
	
	@Mapping(source="constructionMember.id", target="constructionMemberId")
	PaymentResponseDTO mapPaymentToPaymentResponseDTO(Payments payment);

}
