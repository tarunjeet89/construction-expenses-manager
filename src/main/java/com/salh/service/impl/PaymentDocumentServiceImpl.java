package com.salh.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.salh.config.exception.BadRequestException;
import com.salh.dto.PaymentDocumentUploadRequestDTO;
import com.salh.dto.PaymentDocumentUploadResponseDTO;
import com.salh.mapper.PaymentDocumentMapper;
import com.salh.modal.PaymentDocuments;
import com.salh.repository.PaymentDocumentsRepository;
import com.salh.service.PaymentDocumentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentDocumentServiceImpl implements PaymentDocumentService {
	
	private final PaymentDocumentMapper paymentDocumentMapper;
	private final PaymentDocumentsRepository paymentDocumentsRepository;
	
	@Transactional
	@Override
	public PaymentDocumentUploadResponseDTO uploadImage(MultipartFile file, PaymentDocumentUploadRequestDTO requestDTO) throws IOException{
		PaymentDocuments paymentDocument = paymentDocumentMapper.multipartFileToPaymentDocumentMapper(file, requestDTO);
		paymentDocumentsRepository.save(paymentDocument);
		return paymentDocumentMapper.paymentDocumentToUploadResponseDTO(paymentDocument);
	}

	@Transactional(readOnly = true)
	@Override
	public byte[] downloadPaymentDocument(UUID id) {
		PaymentDocuments paymentDocument = paymentDocumentsRepository.findById(id).orElseThrow(()-> new BadRequestException("payment.document.not.found"));
	    return paymentDocument.getDocumentData();
	}

	@Transactional
	@Override
	public PaymentDocumentUploadResponseDTO uploadImage(MultipartFile file, UUID paymentId) throws IOException{
		PaymentDocuments paymentDocument = paymentDocumentMapper.multipartFileToPaymentDocumentMapper(file, paymentId);
		paymentDocumentsRepository.save(paymentDocument);
		return paymentDocumentMapper.paymentDocumentToUploadResponseDTO(paymentDocument);
	}

}
