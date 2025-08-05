package com.DocumentManagementSystem.DMS.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "documents", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"documentNumber", "documentType", "employeeId"})
})
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private String documentName;
    private String documentType;
    private String documentNumber;
    private String documentStatus;

    private LocalDate validFrom;
    private LocalDate validTo;
    private String subType;

    private Long companyId;
    private Long vendorId;
    private Long employeeId;

    private String fileUrl; // S3 path or local

    public Document() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Document(Long documentId, String documentName, String documentType, String documentNumber,
			String documentStatus, LocalDate validFrom, LocalDate validTo, String subType, Long companyId,
			Long vendorId, Long employeeId, String fileUrl) {
		super();
		this.documentId = documentId;
		this.documentName = documentName;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.documentStatus = documentStatus;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.subType = subType;
		this.companyId = companyId;
		this.vendorId = vendorId;
		this.employeeId = employeeId;
		this.fileUrl = fileUrl;
	}

	// Getters, setters, constructors
	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDate getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}

