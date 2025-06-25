package com.salh.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.salh.dto.MonthWisePaymentDTO;
import com.salh.dto.PaymentCreateDTO;
import com.salh.dto.PaymentResponseDTO;
import com.salh.dto.PaymentResponseExtendedDTO;

public interface PaymentService {

	PaymentResponseDTO createPayment(PaymentCreateDTO paymentCreateDTO);

	List<PaymentResponseDTO> listAllPaymentsByMemberId(UUID constructionMemberId);

	Page<PaymentResponseExtendedDTO> listAllPayments(String workerName, Date paymentDateFrom, Date paymentDateTo, Pageable pageable);

	byte[] generateWorkerPaymentsReport(UUID constructionMemberId);

	List<MonthWisePaymentDTO> getTopPaidMembers(LocalDate fromDate, LocalDate toDate);

}
