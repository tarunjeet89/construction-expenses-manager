package com.salh.modal;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "construction_members")
@Builder
@Audited
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ConstructionMembers {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@EqualsAndHashCode.Exclude
	@JsonManagedReference
	@OneToMany(mappedBy="constructionMember",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<ConstructionMemberImages> memberImages;
	
	@EqualsAndHashCode.Exclude
	@JsonManagedReference
	@OneToMany(mappedBy="constructionMember",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<ConstructionMemberDocuments> memberDocuments;
	
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
