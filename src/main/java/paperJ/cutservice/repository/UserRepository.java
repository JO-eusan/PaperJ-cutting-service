package paperJ.cutservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import paperJ.cutservice.domain.User;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    /* 사용자 저장 */
    public void save(User user) {
        em.persist(user);
    }

    /* 사용자 조회 */
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    /* passkey로 사용자 조회 */
    public User findByPasskey(String passkey) {
        try {
            return em.createQuery("select u from User u where u.passkey = :passkey", User.class)
                    .setParameter("passkey", passkey)
                    .getSingleResult();
        } catch (NoResultException e) {
            // passkey로 사용자를 찾을 수 없을 때, null 반환
            return null;
        }
    }
}
