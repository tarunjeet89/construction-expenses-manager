package com.salh.mapper;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.salh.config.exception.BadRequestException;
import com.salh.dto.PaymentDocumentUploadRequestDTO;
import com.salh.dto.PaymentDocumentUploadResponseDTO;
import com.salh.enums.PaymentDocumentTypeEnum;
import com.salh.modal.PaymentDocuments;
import com.salh.modal.Payments;
import com.salh.repository.PaymentsRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentDocumentMapper {
	
	private final PaymentsRepository paymentsRepository;

	public PaymentDocuments multipartFileToPaymentDocumentMapper(MultipartFile multipartFile,  PaymentDocumentUploadRequestDTO requestDTO) throws IOException {
		
		Payments payment = paymentsRepository.findById(requestDTO.getPaymentId()).orElseThrow(()-> new BadRequestException("payment.not.found"));
		
		return PaymentDocuments.builder()
		.documentType(requestDTO.getDocumentType())
		.fileName(multipartFile.getOriginalFilename())
		.fileType(multipartFile.getContentType())
		.documentData(multipartFile.getBytes())
		.payments(payment)
		.build();
	}
	
	public PaymentDocumentUploadResponseDTO paymentDocumentToUploadResponseDTO(PaymentDocuments paymentDocument) {
		return PaymentDocumentUploadResponseDTO.builder()
		.id(paymentDocument.getId())
		.fileName(paymentDocument.getFileName())
		.build();
	}

	public PaymentDocuments multipartFileToPaymentDocumentMapper(MultipartFile multipartFile, UUID paymentId) throws IOException {
		Payments payment = paymentsRepository.findById(paymentId).orElseThrow(()-> new BadRequestException("payment.not.found"));
		String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		
		return PaymentDocuments.builder()
		.documentType(determineFileType(extension))
		.fileName(multipartFile.getOriginalFilename())
		.fileType(multipartFile.getContentType())
		.documentData(multipartFile.getBytes())
		.payments(payment)
		.build();
	}
	
	public PaymentDocumentTypeEnum determineFileType(String extension) {
        switch (extension.toLowerCase()) {
            case "jpeg":
                return PaymentDocumentTypeEnum.IMAGE;
            case "jpg":
            	 return PaymentDocumentTypeEnum.IMAGE;
            case "png":
            	 return PaymentDocumentTypeEnum.IMAGE;
            case "pdf":
            	 return PaymentDocumentTypeEnum.PDF;
            default:
            	throw new BadRequestException("invalid.file.extension");
        }
    }
	
}
