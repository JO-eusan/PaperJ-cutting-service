package paperJ.cutservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paperJ.cutservice.domain.Estimate;
import paperJ.cutservice.repository.EstimateRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstimateService {

    private final EstimateRepository estimateRepository;

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
}
