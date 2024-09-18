package paperJ.cutservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paperJ.cutservice.domain.Estimate;
import paperJ.cutservice.repository.AdminRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;

    /* 모든 견적서 조회 */
    public List<Estimate> getEstimates() {
        return adminRepository.findAllEstimates();
    }
}
