package paperJ.cutservice.repository.paper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import paperJ.cutservice.domain.paper.PaperColor;
import paperJ.cutservice.domain.paper.PaperType;

import java.util.List;

@Repository
public class PaperColorRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(PaperColor paperColor) {
        em.persist(paperColor);
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
