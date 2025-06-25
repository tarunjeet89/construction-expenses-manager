package com.salh.mapper;

import org.mapstruct.Mapper;

import com.salh.dto.ConstructionMembersCreateDTO;
import com.salh.dto.ConstructionMembersResponseDTO;
import com.salh.modal.ConstructionMembers;

@Mapper(componentModel = "spring")
public interface ConstructionMemberMapper {
	
	ConstructionMembers mapConstructionMembersCreateDTOToEntity(ConstructionMembersCreateDTO constructionMembersCreateDTO);
	ConstructionMembersResponseDTO mapEntityToConstructionMembersResponseDTO(ConstructionMembers constructionMembers);

}
