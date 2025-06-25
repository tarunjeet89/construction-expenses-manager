package com.salh.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salh.dto.MonthWisePaymentDTO;
import com.salh.dto.PaymentCreateDTO;
import com.salh.dto.PaymentResponseDTO;
import com.salh.dto.PaymentResponseExtendedDTO;
import com.salh.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
	
	private final PaymentService paymentService;
	
	@Operation(summary  = "Create payment for construction members", 
    		description = "Create payment for construction members")
	@PostMapping
	public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody PaymentCreateDTO paymentCreateDTO) {
		return ResponseEntity.ok(paymentService.createPayment(paymentCreateDTO));
	}
	
	@GetMapping("/constructionMember/{constructionMemberId}")
	public ResponseEntity<List<PaymentResponseDTO>> listAllPaymentsByMemberId(@PathVariable UUID constructionMemberId) {
		return ResponseEntity.ok(paymentService.listAllPaymentsByMemberId(constructionMemberId));
	}
	
	@PageableAsQueryParam
	@GetMapping
	public ResponseEntity<Page<PaymentResponseExtendedDTO>> listAllPayments( 
			@RequestParam(required = false) String workerName,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDateFrom,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date paymentDateTo,
			@Parameter(hidden=true) @PageableDefault(size = 100, sort = "paymentDate", direction = Direction.ASC) Pageable pageable) {
		return ResponseEntity.ok(paymentService.listAllPayments(workerName, paymentDateFrom, paymentDateTo, pageable));
	}
	
	@GetMapping("/pdf-reports/{constructionMemberId}")
    public ResponseEntity<byte[]> generateWorkerPaymentsReport(@PathVariable UUID constructionMemberId) {
    	  byte[] pdfBytes = paymentService.generateWorkerPaymentsReport(constructionMemberId);
          HttpHeaders headers = new HttpHeaders();
          headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=WorkerPayments.pdf");
          headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

          return ResponseEntity.ok()
                  .headers(headers)
                  .body(pdfBytes);
    }
    
    @GetMapping("monthy-payments")
    public List<MonthWisePaymentDTO> getDateSpecificMonthlyAmountSpent(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return paymentService.getTopPaidMembers(fromDate, toDate);
    }

}
