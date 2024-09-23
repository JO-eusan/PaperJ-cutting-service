package paperJ.cutservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import paperJ.cutservice.domain.Estimate;

import java.util.List;

@Repository
public class AdminRepository {

    @PersistenceContext
    private EntityManager em;

    /* 사용자가 발급한 견적서 조회 */
    public List<Estimate> findAllEstimates() {
        // JPQL을 사용하여 모든 Estimate(견적서) 조회
        return em.createQuery("select e from Estimate e", Estimate.class)
                .getResultList();
    }
}
