package com.salh.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salh.config.exception.BadRequestException;
import com.salh.config.exception.InternalServerException;
import com.salh.dto.MonthWisePaymentDTO;
import com.salh.dto.PaymentCreateDTO;
import com.salh.dto.PaymentResponseDTO;
import com.salh.dto.PaymentResponseExtendedDTO;
import com.salh.dto.WorkerPaymentDTO;
import com.salh.mapper.PaymentMapper;
import com.salh.modal.ConstructionMembers;
import com.salh.modal.Payments;
import com.salh.repository.ConstructionMembersRepository;
import com.salh.repository.PaymentsRepository;
import com.salh.repository.PaymentsSearchRepository;
import com.salh.service.PaymentService;
import com.salh.validator.PaymentValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
	
	private final PaymentsRepository paymentsRepository;
	private final PaymentMapper paymentMapper;
	private final PaymentValidator paymentValidator;
	private final ConstructionMembersRepository constructionMembersRepository;
	private final PaymentsSearchRepository paymentsSearchRepository;
	
	@Transactional
	@Override
	public PaymentResponseDTO createPayment(PaymentCreateDTO paymentCreateDTO) {
		log.info("createPayment - paymentCreateDTO:{}", paymentCreateDTO);
		ConstructionMembers constructionMembers =  paymentValidator.validateCreatePayment(paymentCreateDTO);
		Payments payment = paymentMapper.mapPaymentCreateDTOToPayment(paymentCreateDTO);
		payment.setConstructionMember(constructionMembers);
		paymentsRepository.save(payment);
		return paymentMapper.mapPaymentToPaymentResponseDTO(payment);
	}

	@Transactional
	@Override
	public List<PaymentResponseDTO> listAllPaymentsByMemberId(UUID constructionMemberId) {
		log.info("listAllPaymentsByMemberId - constructionMemberId:{}", constructionMemberId);
		constructionMembersRepository.findById(constructionMemberId).orElseThrow(()-> new BadRequestException("member.not.found"));
		return paymentsRepository.getAllPaymentsByConstructionMemberId(constructionMemberId);
	}

	@Transactional
	@Override
	public Page<PaymentResponseExtendedDTO> listAllPayments(String workerName, Date paymentDateFrom, Date paymentDateTo, Pageable pageable) {
		log.info("listAllPayments - pageable:{}", pageable);
		return paymentsSearchRepository.getPaymentsByCriteria(workerName, paymentDateFrom, paymentDateTo, pageable);
	}

	@Transactional
	@Override
	public byte[] generateWorkerPaymentsReport(UUID constructionMemberId) {
		log.info("generateWorkerPaymentsReport - constructionMemberId:{}", constructionMemberId);
		try {
            // Load the JRXML file from the resources folder
            ClassPathResource resource = new ClassPathResource("jasper/WorkerPayments.jrxml");
            InputStream jrxmlStream = resource.getInputStream();
            
            // Compile the JasperReport
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            constructionMembersRepository.findById(constructionMemberId).orElseThrow(()-> new BadRequestException("member.not.found"));
            List<WorkerPaymentDTO> data = paymentsRepository.getAllPaymentsByConstructionMemberIdForJasper(constructionMemberId);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

            // Parameters for the report (if any)
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ReportTitle", "Worker Payments Report");
            parameters.put("createdBy", "Tarunjeet Singh");

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export the report to PDF
            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, pdfOutputStream);

            // Return the PDF as a response
            return pdfOutputStream.toByteArray();
           
        } catch (Exception e) {
        	e.printStackTrace();
            throw new InternalServerException(e.getMessage());
        }
	}

	@Transactional
	@Override
	public List<MonthWisePaymentDTO> getTopPaidMembers(LocalDate fromDate, LocalDate toDate) {
		return paymentsRepository.getMonthlySpendingForSpecificRange(fromDate, toDate);
	}

}
