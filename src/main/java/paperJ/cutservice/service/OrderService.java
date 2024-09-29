package paperJ.cutservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paperJ.cutservice.domain.Estimate;
import paperJ.cutservice.domain.Order;
import paperJ.cutservice.domain.UserInfo;
import paperJ.cutservice.repository.EstimateRepository;
import paperJ.cutservice.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final EstimateRepository estimateRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public void save(Order order) {
        orderRepository.save(order); // 상태 저장
    }

    /* 견적서 ID로 주문 조회 */
    public Order getOrderByEstimateId(Long estimateId) {
        return orderRepository.findByEstimateId(estimateId);
    }

    /* 견적서에 대한 주문 신청 */
    @Transactional
    public void requestOrder(Long estimateId, String contact, String name) {
        Estimate estimate = estimateRepository.findById(estimateId);

        // 이미 주문이 존재하는 경우 처리
        if (estimate.getOrder() != null) {
            throw new IllegalArgumentException("해당 견적서에 대한 주문이 이미 존재합니다.");
        }

        UserInfo userInfo = new UserInfo(name, contact);

        Order order = new Order(userInfo, estimate);
        orderRepository.save(order);

        estimate.changeStatus(true);
        estimate.setOrder(order);
    }

}
