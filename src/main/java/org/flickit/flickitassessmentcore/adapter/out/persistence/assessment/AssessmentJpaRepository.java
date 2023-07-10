package org.flickit.flickitassessmentcore.adapter.out.persistence.assessment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AssessmentJpaRepository extends JpaRepository<AssessmentJpaEntity, UUID> {

    @Query("SELECT a as assessment, r.maturityLevelId as maturityLevelId " +
        "FROM AssessmentJpaEntity a " +
        "LEFT JOIN AssessmentResultJpaEntity r " +
        "ON a.id = r.assessment.id " +
        "WHERE a.spaceId=:spaceId AND " +
        "r.lastModificationDate = (SELECT MAX(ar.lastModificationDate) FROM AssessmentResultJpaEntity ar WHERE ar.assessment.id = a.id) " +
        "ORDER BY a.lastModificationDate DESC")
    List<AssessmentsWithMaturityLevelView> findBySpaceIdOrderByLastModificationDateDescWithLastMaturityLevelId(long spaceId, Pageable pageable);
}
