package com.salh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salh.dto.StatsDTO;
import com.salh.service.StatsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
public class StatsController {
	
	private final StatsService statsService;
	
	@GetMapping
    public ResponseEntity<StatsDTO> getStats() {
		StatsDTO statsDTO = statsService.getStats();
		return ResponseEntity.ok(statsDTO);
    }

}
