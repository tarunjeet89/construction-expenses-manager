package com.salh.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.salh.config.exception.BadRequestException;
import com.salh.dto.ConstructionMembersCreateDTO;

@Component
public class ConstructionMemberValidator {
	
	public void validateCreateConstructionMember(ConstructionMembersCreateDTO constructionMembersCreateDTO) {
		
		String name = constructionMembersCreateDTO.getName();
		
		if (StringUtils.isBlank(name)) {
			throw new BadRequestException("member.name.required");
		}
		
	}

}
