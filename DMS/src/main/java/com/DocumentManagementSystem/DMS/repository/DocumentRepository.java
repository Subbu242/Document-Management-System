package com.DocumentManagementSystem.DMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DocumentManagementSystem.DMS.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    boolean existsByDocumentNumberAndDocumentTypeAndEmployeeId(String docNum, String docType, Long empId);
}
