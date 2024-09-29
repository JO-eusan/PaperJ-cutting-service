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
        Order order = estimate.getOrder();

        order.setStatus(status);
        orderRepository.save(order);
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

    /* 견적서 계산 로직 (sheetQuantity, pricePersheet, totalPrice) */
    private int calculateSheetQuantity(Estimate estimate) {
        // 종이의 크기와 엽서의 크기
        int sheetX = estimate.getPaperType().getSizeX();
        int sheetY = estimate.getPaperType().getSizeY();
        int cardX = estimate.getSizeX();
        int cardY = estimate.getSizeY();

        // 작은 면을 기준으로 몇 개의 카드가 들어갈 수 있는지 계산
        int cardsPerX = Math.min(sheetX, sheetY) / Math.min(cardX, cardY);
        int cardsPerY = Math.max(sheetX, sheetY) / Math.min(cardX, cardY);

        // 종이 한 장에서 나올 수 있는 총 카드 수 계산
        int cardsPerSheet = cardsPerX * cardsPerY;

        // 총 카드 수를 종이 한 장당 나오는 카드 수로 나눠서 필요한 종이 수 계산
        return (int) Math.ceil((double) estimate.getCardQuantity() / cardsPerSheet);
    }
}
