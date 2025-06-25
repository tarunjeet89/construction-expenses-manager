package com.salh.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionMembersResponseDTO {
	
	private UUID id;
	private String name;
	private String phoneNumber;
}
