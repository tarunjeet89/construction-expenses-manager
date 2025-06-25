package com.salh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDTO {
	
	private Long contructionMembersCount;
	private Long totalAmountSpent;
	
	@Builder.Default
	private Long totalAmount = 7000000l;

}
