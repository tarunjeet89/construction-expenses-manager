package com.salh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.salh.dto.ConstructionMembersCreateDTO;
import com.salh.dto.ConstructionMembersResponseDTO;
import com.salh.dto.ConstructionMembersSearchDTO;

public interface ConstructionMemberService {

	ConstructionMembersResponseDTO createConstructionMember(ConstructionMembersCreateDTO constructionMembersCreateDTO);

	List<ConstructionMembersSearchDTO> getAllMembers(Optional<String> name, Optional<String> phoneNumber);

	List<ConstructionMembersSearchDTO> getTopPaidMembers(Pageable pageable);

}
