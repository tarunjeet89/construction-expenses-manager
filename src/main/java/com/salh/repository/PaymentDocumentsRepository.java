package com.salh.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import com.salh.modal.PaymentDocuments;

@Repository
public interface PaymentDocumentsRepository extends JpaRepository<PaymentDocuments, UUID>, RevisionRepository<PaymentDocuments, UUID, Integer> {


}
