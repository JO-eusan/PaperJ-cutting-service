package paperJ.cutservice.repository.paper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import paperJ.cutservice.domain.paper.PaperType;

import java.util.List;

@Repository
public class PaperTypeRepository {

    @PersistenceContext
    private EntityManager em;

    /* 종이 종류 저장 */
    public void save(PaperType paperType) {
        em.persist(paperType);
    }

    public PaperType findById(Long paperTypeId) {
        return em.find(PaperType.class, paperTypeId);
    }

    /* 종이 이름과 gsm으로 조회 */
    public PaperType findByNameAndGSM(String name, int gsm) {
        try {
            return em.createQuery("select pt from PaperType pt where pt.name = :name and pt.GSM = :gsm", PaperType.class)
                    .setParameter("name", name)
                    .setParameter("gsm", gsm)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /* 모든 종이 종류 조회 */
    public List<String> findAllPaperTypes() {
        return em.createQuery("select distinct pt.name from PaperType pt", String.class)
                .getResultList();
    }

    /* 종이 이름에 대한 가능한 GSM 목록 조회 */
    public List<Integer> findGSMbyPaperType(String paperTypeName) {
        // 특정 종이에 해당되는 GSM 목록 조회
        return em.createQuery("select distinct pt.GSM from PaperType pt where pt.name = :name", Integer.class)
                .setParameter("name", paperTypeName)
                .getResultList();
    }
}
