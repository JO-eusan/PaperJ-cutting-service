package paperJ.cutservice.repository.paper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import paperJ.cutservice.domain.paper.PaperColor;

import java.util.List;

@Repository
public class PaperColorRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(PaperColor paperColor) {
        em.persist(paperColor);
    }

    public PaperColor findById(Long paperColorId) {
        return em.find(PaperColor.class, paperColorId);
    }

    /* 종이 이름과 종이 종류 id로 종이 색상 조회 */
    public PaperColor findByTypeAndColor(String colorName, Long paperTypeId) {
        try {
            return em.createQuery(
                            "select pc from PaperColor pc where pc.color = :colorName and pc.paperType.id = :paperTypeId", PaperColor.class)
                    .setParameter("colorName", colorName)
                    .setParameter("paperTypeId", paperTypeId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // 검색 결과가 없을 경우 null 반환
        }
    }

    /* 종이 이름과 가능한 GSM에 따른 가능한 색상 조회 */
    public List<String> findColorsByPaperTypeAndGSM(String name, int gsm) {
        return em.createQuery(
                "select distinct pc.color from PaperColor pc where pc.paperType.name = :name and pc.paperType.GSM = :gsm", String.class)
                .setParameter("name", name)
                .setParameter("gsm", gsm)
                .getResultList();
    }
}
