package com.accenture.challenge_backend.infrastructure.repository;

import com.accenture.challenge_backend.infrastructure.document.FranquiciaDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranquiciaRepository extends ReactiveMongoRepository<FranquiciaDocument, String> {
}
