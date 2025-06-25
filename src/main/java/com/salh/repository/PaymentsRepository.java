package com.salh.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import com.salh.dto.MonthWisePaymentDTO;
import com.salh.dto.PaymentResponseDTO;
import com.salh.dto.PaymentResponseExtendedDTO;
import com.salh.dto.WorkerPaymentDTO;
import com.salh.modal.Payments;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, UUID>, RevisionRepository<Payments, UUID, Integer> {
	
	@Query("""
			SELECT new com.salh.dto.PaymentResponseDTO(
				p.id,
				p.constructionMember.id,
				p.amount,
				p.paymentStatus,
				p.paymentMode,
				p.paymentDate,
				p.paymentIntent,
				pd.id
			)
			FROM Payments p LEFT JOIN PaymentDocuments pd ON p=pd.payments
			WHERE p.constructionMember.id = :constructionMemberId
			ORDER BY p.paymentDate ASC
			""")
	List<PaymentResponseDTO> getAllPaymentsByConstructionMemberId(UUID constructionMemberId);
	
	@Query("""
			SELECT new com.salh.dto.PaymentResponseExtendedDTO(
				p.id,
				p.constructionMember.id,
				p.amount,
				p.paymentStatus,
				p.paymentMode,
				p.paymentDate,
				p.paymentIntent,
				pd.id,
				p.constructionMember.name
			)
			FROM Payments p 
			LEFT JOIN PaymentDocuments pd ON p=pd.payments
			""")
	Page<PaymentResponseExtendedDTO> getAllPaymentsByConstructionMemberId(Pageable pageable);
	
	@Query("SELECT SUM(p.amount) FROM Payments p")
	Long getTotalAmount();
	
	@Query("""
		    SELECT new com.salh.dto.WorkerPaymentDTO(
		        cm.name,
		        cm.phoneNumber,
		        CAST(p.amount AS string),
		        CAST(p.paymentMode AS string),
		        p.paymentDate,
		        p.paymentIntent,
		        CAST(SUM(p2.amount) AS string)
		    )
		    FROM com.salh.modal.Payments p
		    JOIN p.constructionMember cm
		    JOIN com.salh.modal.Payments p2 ON p2.constructionMember.id = cm.id
		    WHERE cm.id = :constructionMemberId
		    GROUP BY cm.name, cm.phoneNumber, p.amount, p.paymentMode, p.paymentDate, p.paymentIntent
		    ORDER BY p.paymentDate ASC
		""")
		List<WorkerPaymentDTO> getAllPaymentsByConstructionMemberIdForJasper(UUID constructionMemberId);
	
	
	@Query("""
	        SELECT new com.salh.dto.MonthWisePaymentDTO (FUNCTION('TO_CHAR', p.paymentDate, 'Mon-YYYY') AS yearMonth, SUM(p.amount) )
	        FROM Payments p 
	        WHERE p.paymentDate >= DATE(:fromDate) AND p.paymentDate < DATE(:toDate)
	        GROUP BY FUNCTION('TO_CHAR', p.paymentDate, 'Mon-YYYY') 
	        ORDER BY MIN(p.paymentDate)
	       """)
	List<MonthWisePaymentDTO> getMonthlySpendingForSpecificRange(LocalDate fromDate,LocalDate toDate);





}
