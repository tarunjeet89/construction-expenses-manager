package com.salh.controller;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salh.dto.ConstructionMembersCreateDTO;
import com.salh.dto.ConstructionMembersResponseDTO;
import com.salh.dto.ConstructionMembersSearchDTO;
import com.salh.service.ConstructionMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ConstructionMembersController {
	
	private final ConstructionMemberService constructionMemberService;
	
	@Operation(summary  = "Create construction members", 
    		description = """
    				Create construction members <br>
    				Members can be WORKER OR COMPANY
    				""")
	@PostMapping
	public ResponseEntity<ConstructionMembersResponseDTO> createConstructionMember(@RequestBody ConstructionMembersCreateDTO constructionMembersCreateDTO) {
		return ResponseEntity.ok(constructionMemberService.createConstructionMember(constructionMembersCreateDTO));
	}
	
	@GetMapping
    public List<ConstructionMembersSearchDTO> getAllMembers(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> phoneNumber) {
        return constructionMemberService.getAllMembers(name, phoneNumber);
    }
	
	@PageableAsQueryParam
	@GetMapping("top-paid")
    public List<ConstructionMembersSearchDTO> getTop10PaidMembers(
    		@Parameter(hidden=true) @PageableDefault(size = 5) Pageable pageable) {
        return constructionMemberService.getTopPaidMembers(pageable);
    }
	
	

	
}
