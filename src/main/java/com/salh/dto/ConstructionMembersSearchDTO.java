package com.salh.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionMembersSearchDTO {
	
	private UUID id;
	private String name;
	private String phoneNumber;
	private BigDecimal amount;
	
}
