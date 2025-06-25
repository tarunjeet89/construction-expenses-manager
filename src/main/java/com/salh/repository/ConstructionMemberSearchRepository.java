package com.salh.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.envers.query.criteria.MatchMode;
import org.springframework.stereotype.Repository;

import com.salh.dto.ConstructionMembersSearchDTO;

@Repository
public class ConstructionMemberSearchRepository {
	
	@PersistenceContext
    private EntityManager entityManager;

	public List<ConstructionMembersSearchDTO> findMembersWithTotalAmount(Optional<String> name, Optional<String> phoneNumber) {
	    StringBuilder jpql = new StringBuilder("""
	            SELECT new com.salh.dto.ConstructionMembersSearchDTO(
	                c.id, 
	                c.name, 
	                c.phoneNumber, 
	                COALESCE(SUM(p.amount), 0)
	            ) 
	            FROM ConstructionMembers c
	            LEFT JOIN Payments p ON p.constructionMember.id = c.id
	            WHERE 1=1
	        """);

	    // Add dynamic filters
	    if (name.isPresent()) {
	        jpql.append(" AND LOWER(c.name) LIKE LOWER(:name)");
	    }
	    if (phoneNumber.isPresent()) {
	        jpql.append(" AND c.phoneNumber LIKE :phoneNumber");
	    }

	    jpql.append(" GROUP BY c.id, c.name, c.phoneNumber");
	    
	    // Add ORDER BY clause
        jpql.append(" ORDER BY COALESCE(SUM(p.amount), 0) DESC");

	    Query query = entityManager.createQuery(jpql.toString(), ConstructionMembersSearchDTO.class);

	    // Set query parameters if present
	    name.ifPresent(n -> query.setParameter("name", MatchMode.ANYWHERE.toMatchString(n)));
	    phoneNumber.ifPresent(p -> query.setParameter("phoneNumber", p));

	    return query.getResultList();
	}


}
