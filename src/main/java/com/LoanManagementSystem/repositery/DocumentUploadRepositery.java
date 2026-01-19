package com.LoanManagementSystem.repositery;

import com.LoanManagementSystem.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentUploadRepositery extends JpaRepository<DocumentEntity, UUID> {

    Optional<DocumentEntity> findByIdAndLoanId(UUID id, Long loanId);

    List<DocumentEntity> findByLoanId(Long loanId);
}
