package com.salh.modal;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.salh.enums.PaymentModeEnum;
import com.salh.enums.PaymentStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "payments")
@Builder
@Audited
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Payments {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonBackReference
	@OneToOne
	@JoinColumn(name="construction_member_id",referencedColumnName = "id")
	private ConstructionMembers constructionMember;
	
	@Digits(integer=8, fraction=2)    
	@Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status")
	private PaymentStatusEnum paymentStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_mode")
	private PaymentModeEnum paymentMode;
	
	@Column(name = "payment_date")
	@Temporal(TemporalType.DATE)
	private Date paymentDate;
	
	@Column(name = "payment_intent", columnDefinition = "text")
	private String paymentIntent;
	
	@EqualsAndHashCode.Exclude
	@JsonManagedReference
	@OneToMany(mappedBy="payments",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<PaymentDocuments> paymentDocuments;
	
	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;

	@LastModifiedBy
	@Column(name = "updated_by")
	private String updatedBy;

	@CreatedDate
	@Column(name = "created_on")
	private Date createdOn;

	@LastModifiedDate
	@Column(name = "updated_on")
	private Date updatedOn;

}
