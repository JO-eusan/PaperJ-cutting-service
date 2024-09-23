package paperJ.cutservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paperJ.cutservice.domain.*;
import paperJ.cutservice.domain.paper.PaperColor;
import paperJ.cutservice.domain.paper.PaperType;
import paperJ.cutservice.repository.EstimateRepository;
import paperJ.cutservice.repository.OrderRepository;
import paperJ.cutservice.repository.UserRepository;
import paperJ.cutservice.repository.paper.PaperColorRepository;
import paperJ.cutservice.repository.paper.PaperTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimateService {

    private final UserRepository userRepository;
    private final EstimateRepository estimateRepository;
    private final PaperTypeRepository paperTypeRepository;
    private final PaperColorRepository paperColorRepository;
    private final OrderRepository orderRepository;

    /* 모드 견적서 조회 */
    public List<Estimate> getAllEstimate() {
        return estimateRepository.findAllEstimates();
    }

    /* 특정 견적서를 사용자로 조회 */
    public List<Estimate> getEstimateByUserId(Long userId) {
        return estimateRepository.findByUser(userId);
    }

    /* 특정 견적서를 ID로 조회 */
    public Estimate getEstimateById(Long id) {
        return estimateRepository.findById(id);
    }

    /* 주문 상태 업데이트 */
    @Transactional
    public void updateOrderStatus(Long estimateId, String status) {
        Estimate estimate = estimateRepository.findById(estimateId);
        estimate.getOrder().setStatus(OrderStatus.valueOf(status));
    }

    /* 견적서 발급 */
    @Transactional
    public Estimate createEstimate(Long userId, Long paperTypeId, Long paperColorId, int gsm, int sizeX, int sizeY, int cardQuantity) {
        User user = userRepository.findById(userId);
        PaperType paperType = paperTypeRepository.findByNameAndGSM(paperTypeRepository.findById(paperTypeId).getName(), gsm);
        PaperColor paperColor = paperColorRepository.findById(paperColorId);

        Estimate estimate = new Estimate(paperType, paperColor, sizeX, sizeY, gsm, cardQuantity, user);

        int sheetQuantity = calculateSheetQuantity(estimate);
        double pricePerSheet = paperColor.getPricePerSheet();
        double totalPrice = sheetQuantity * pricePerSheet;

        estimate.setSheetQuantity(sheetQuantity);
        estimate.setPricePerSheet(pricePerSheet);
        estimate.setTotalPrice(totalPrice);

        // 견적서 저장
        estimateRepository.save(estimate);

        return estimate;
    }

    /* 견적서에 대한 주문 신청 */
    @Transactional
    public void requestOrder(Long estimateId, String contact, String name) {
        Estimate estimate = estimateRepository.findById(estimateId);

        // 이미 주문이 존재하는 경우 처리
        if (estimate.getOrder() != null) {
            throw new IllegalArgumentException("해당 견적서에 대한 주문이 이미 존재합니다.");
        }

        UserInfo userInfo = new UserInfo(contact, name);

        Order order = new Order(userInfo, estimate);
        orderRepository.save(order);

        estimate.changeStatus(true);
        estimate.setOrder(order);
    }

    /* 견적서 계산 로직 (sheetQuantity, pricePersheet, totalPrice) */
    private int calculateSheetQuantity(Estimate estimate) {
        // 종이의 면적과 카드의 면적을 계산
        int sheetArea = estimate.getPaperType().getSizeX() * estimate.getPaperType().getSizeY();
        int cardArea = estimate.getSizeX() * estimate.getSizeY();

        // 종이 1장당 몇 장의 카드를 만들 수 있는지 계산
        return (int) Math.ceil((double) estimate.getCardQuantity() / (sheetArea / cardArea));
    }
}
