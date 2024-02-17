package com.lux.auth_service.repository;

import com.lux.auth_service.model.Document;
import io.sapl.spring.method.metadata.PostEnforce;
import io.sapl.spring.method.metadata.PreEnforce;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface DocumentRepo {
    @PreEnforce(subject = "authentication.getPrincipal()", action = "'list documents'")
    List<Document> findAll();

    @PostEnforce(subject = "authentication.getPrincipal()", action = "'get document'", resource = "returnObject")
    Optional<Document> findById(Integer id);
}
