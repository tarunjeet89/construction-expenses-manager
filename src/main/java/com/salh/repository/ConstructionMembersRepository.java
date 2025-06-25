package com.salh.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import com.salh.dto.ConstructionMembersSearchDTO;
import com.salh.modal.ConstructionMembers;

@Repository
public interface ConstructionMembersRepository extends JpaRepository<ConstructionMembers, UUID>, RevisionRepository<ConstructionMembers, UUID, Integer> {

	@Query("""
	            SELECT new com.salh.dto.ConstructionMembersSearchDTO(
	                c.id, 
	                c.name, 
	                c.phoneNumber, 
	                COALESCE(SUM(p.amount), 0)
	            ) 
	            FROM ConstructionMembers c
	            LEFT JOIN Payments p ON p.constructionMember.id = c.id
	            GROUP BY c.id, c.name, c.phoneNumber
	            ORDER BY COALESCE(SUM(p.amount), 0) DESC
	        """)
	List<ConstructionMembersSearchDTO> getTopPaidMembers(Pageable pageable);

}
