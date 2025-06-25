package com.salh.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import com.salh.modal.ConstructionMemberImages;

@Repository
public interface ConstructionMemberImagesRepository extends JpaRepository<ConstructionMemberImages, UUID>, 
RevisionRepository<ConstructionMemberImages, UUID, Integer> {

}
