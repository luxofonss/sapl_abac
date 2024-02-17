package com.lux.auth_service.repository;

import com.lux.auth_service.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepoImpl extends DocumentRepo, JpaRepository<Document, Integer> {
}
