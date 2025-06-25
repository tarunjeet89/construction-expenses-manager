package com.salh.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salh.dto.ConstructionMembersCreateDTO;
import com.salh.dto.ConstructionMembersResponseDTO;
import com.salh.dto.ConstructionMembersSearchDTO;
import com.salh.mapper.ConstructionMemberMapper;
import com.salh.modal.ConstructionMembers;
import com.salh.repository.ConstructionMemberSearchRepository;
import com.salh.repository.ConstructionMembersRepository;
import com.salh.service.ConstructionMemberService;
import com.salh.validator.ConstructionMemberValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConstructionMemberServiceImpl implements ConstructionMemberService{
	
	private final ConstructionMemberMapper constructionMemberMapper;
	private final ConstructionMembersRepository constructionMembersRepository;
	private final ConstructionMemberValidator constructionMemberValidator;
	private final ConstructionMemberSearchRepository constructionMemberSearchRepository; 
	
	@Transactional
	@Override
	public ConstructionMembersResponseDTO createConstructionMember(ConstructionMembersCreateDTO constructionMembersCreateDTO) {
		log.info("createConstructionMember - constructionMembersCreateDTO:{}", constructionMembersCreateDTO);
		constructionMemberValidator.validateCreateConstructionMember(constructionMembersCreateDTO);
		ConstructionMembers constructionMembers = constructionMemberMapper.mapConstructionMembersCreateDTOToEntity(constructionMembersCreateDTO);
		constructionMembersRepository.save(constructionMembers);
		return constructionMemberMapper.mapEntityToConstructionMembersResponseDTO(constructionMembers);
	}

	@Transactional(readOnly = true)
	@Override
	public List<ConstructionMembersSearchDTO> getAllMembers(Optional<String> name, Optional<String> phoneNumber) {
		return constructionMemberSearchRepository.findMembersWithTotalAmount(name, phoneNumber);
	}

	@Override
	public List<ConstructionMembersSearchDTO> getTopPaidMembers(Pageable pageable) {
		return constructionMembersRepository.getTopPaidMembers(pageable);
	}

}
