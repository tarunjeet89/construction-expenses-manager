package com.salh.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.salh.dto.PaymentDocumentUploadRequestDTO;
import com.salh.dto.PaymentDocumentUploadResponseDTO;

public interface PaymentDocumentService {

	PaymentDocumentUploadResponseDTO uploadImage(MultipartFile file,  PaymentDocumentUploadRequestDTO requestDTO)  throws IOException;

	byte[] downloadPaymentDocument(UUID id);

	PaymentDocumentUploadResponseDTO uploadImage(MultipartFile file, UUID paymentId) throws IOException;

}
