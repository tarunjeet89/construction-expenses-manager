package com.salh.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.salh.dto.PaymentDocumentUploadRequestDTO;
import com.salh.dto.PaymentDocumentUploadResponseDTO;
import com.salh.service.PaymentDocumentService;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payment-documents")
@RequiredArgsConstructor
public class PaymentDocumentController {
	
	private final PaymentDocumentService paymentDocumentService;
	
	@PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PaymentDocumentUploadResponseDTO> uploadPaymentDocument( 
    		@ModelAttribute PaymentDocumentUploadRequestDTO requestDTO,
    		@RequestParam MultipartFile file) throws IOException {
		PaymentDocumentUploadResponseDTO response = paymentDocumentService.uploadImage(file, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	@PostMapping(path = "/upload/payment/{paymentId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PaymentDocumentUploadResponseDTO> uploadPaymentDocuments(
            @Parameter(description = "UUID of the Case Application") @PathVariable UUID paymentId,
            @RequestParam MultipartFile file) throws IOException {
		PaymentDocumentUploadResponseDTO response = paymentDocumentService.uploadImage(file, paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	@GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPaymentDocument(@PathVariable UUID id){
        byte[] image = paymentDocumentService.downloadPaymentDocument(id);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }

}
