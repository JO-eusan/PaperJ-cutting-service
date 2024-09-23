package paperJ.cutservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import paperJ.cutservice.domain.Order;

import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    /* 주문 저장 */
    public void save(Order order) {
        em.persist(order);
    }

    /* 주문 ID로 조회 */
    public Order findById(Long orderId) {
        return em.find(Order.class, orderId);
    }

    /* 모든 주문 조회 */
    public List<Order> findAllOrders() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

}
