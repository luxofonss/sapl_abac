package com.lux.auth_service.controller;

import com.lux.auth_service.model.Document;
import com.lux.auth_service.service.DocumentService;
import com.lux.auth_service.shared.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;
    @GetMapping("/test")
    public ResponseEntity<ApiResponse> listDocuments() {
        System.out.println("test list documents");
        List<Document> documents = documentService.listDocuments();
        return ResponseEntity.ok(new ApiResponse(
                documents,
                HttpStatus.OK,
                "true"
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDocumentById(@PathVariable Integer id) {
        Optional<Document> document = documentService.getDocumentById(id);
        System.out.println("test get document:: " + document.toString());
        return ResponseEntity.ok(new ApiResponse(
                document,
                HttpStatus.OK,
                "true"
        ));
    }
}
