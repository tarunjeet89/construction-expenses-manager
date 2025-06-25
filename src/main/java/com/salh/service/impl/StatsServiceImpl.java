package com.salh.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salh.dto.StatsDTO;
import com.salh.repository.ConstructionMembersRepository;
import com.salh.repository.PaymentsRepository;
import com.salh.service.StatsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService{
	
	private final ConstructionMembersRepository constructionMembersRepository;
	private final PaymentsRepository paymentsRepository;
	
	@Transactional
	@Override
	public StatsDTO getStats() {
		return StatsDTO
				.builder()
				.contructionMembersCount(constructionMembersRepository.count())
				.totalAmountSpent(paymentsRepository.getTotalAmount())
				.build();
	}

}
