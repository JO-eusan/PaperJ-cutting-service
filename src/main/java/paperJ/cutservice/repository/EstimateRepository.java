package paperJ.cutservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import paperJ.cutservice.domain.Estimate;

import java.util.List;

@Repository
public class EstimateRepository {

    @PersistenceContext
    private EntityManager em;

    /* 견적서 저장 */
    public void save(Estimate estimate) {
        em.persist(estimate);
    }

    /* 특정 사용자의 견적서 목록 조회 */
    public List<Estimate> findByUser(Long userId) {
        return em.createQuery("select e from estimate e where e.user.id = :userId", Estimate.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
