package com.lux.auth_service.service;

import com.lux.auth_service.model.Document;
import com.lux.auth_service.repository.DocumentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepo documentRepo;

    public List<Document> listDocuments() {
        return documentRepo.findAll();
    }

    public Optional<Document> getDocumentById(Integer id) {
        return documentRepo.findById(id);
    }
}
