package com.DocumentManagementSystem.DMS.Controller;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DocumentManagementSystem.DMS.model.Document;
import com.DocumentManagementSystem.DMS.repository.DocumentRepository;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;

    
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam String documentName,
            @RequestParam String documentType,
            @RequestParam String documentNumber,
            @RequestParam String documentStatus,
            @RequestParam String validFrom,
            @RequestParam String validTo,
            @RequestParam(required = false) String subType,
            @RequestParam Long companyId,
            @RequestParam Long vendorId,
            @RequestParam Long employeeId
    ) {
        try {
            if (file.getSize() > 4 * 1024 * 1024)
                return ResponseEntity.badRequest().body("File exceeds 4MB");

            String type = file.getContentType();
            if (!(type.equals("application/pdf") || type.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")))
                return ResponseEntity.badRequest().body("Only .pdf and .docx files are allowed");

            boolean duplicate = documentRepository.existsByDocumentNumberAndDocumentTypeAndEmployeeId(
                    documentNumber, documentType, employeeId
            );
            if (duplicate)
                return ResponseEntity.badRequest().body("Duplicate document");

            // TODO: Replace with actual AWS S3 upload
            String dummyS3Path = "s3://" + companyId + "/" + employeeId + "/" + documentType + "/" + file.getOriginalFilename();

            Document doc = new Document();
            

            LocalDate today = LocalDate.now(); 
            
            
            if((validFrom != null && validTo != null && (LocalDate.parse(validTo)).isAfter(LocalDate.parse(validFrom)))){
            	doc.setValidFrom(LocalDate.parse(validFrom));
                doc.setValidTo(LocalDate.parse(validTo));
            }
            else {
            	return ResponseEntity.badRequest().body("\"Valid from\" date cannot be after \"Valid to\" date ");
            }
            
            if ((validFrom != null && !today.isAfter(LocalDate.parse(validFrom))) ||
                (validTo != null && !today.isAfter(LocalDate.parse(validTo)))) {
                doc.setDocumentStatus("Active");
            } else {
                doc.setDocumentStatus("Expired");
            }
            
            doc.setDocumentName(documentName);
            doc.setDocumentType(documentType);
            doc.setDocumentNumber(documentNumber);
//            doc.setDocumentStatus(documentStatus);
//            doc.setValidFrom(LocalDate.parse(validFrom));
//            doc.setValidTo(LocalDate.parse(validTo));
            doc.setSubType(subType);
            doc.setCompanyId(companyId);
            doc.setVendorId(vendorId);
            doc.setEmployeeId(employeeId);
            doc.setFileUrl(dummyS3Path);

            documentRepository.save(doc);

            return ResponseEntity.ok("Document uploaded successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}

