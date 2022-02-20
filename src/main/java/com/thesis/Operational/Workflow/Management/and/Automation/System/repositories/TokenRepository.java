package com.thesis.Operational.Workflow.Management.and.Automation.System.repositories;

import com.thesis.Operational.Workflow.Management.and.Automation.System.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Modifying
    void deleteByCreatedDateLessThan(LocalDateTime date);

    Optional<Token> findByConfirmationToken(String confirmationToken);

}
