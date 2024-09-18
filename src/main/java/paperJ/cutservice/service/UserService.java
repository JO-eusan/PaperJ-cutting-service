package paperJ.cutservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paperJ.cutservice.domain.Estimate;
import paperJ.cutservice.domain.User;
import paperJ.cutservice.domain.paper.PaperColor;
import paperJ.cutservice.domain.paper.PaperType;
import paperJ.cutservice.repository.EstimateRepository;
import paperJ.cutservice.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final EstimateRepository estimateRepository;

    /* 사용자 가입 및 조회 */
    @Transactional
    public User joinOrFindUser(String passkey) {
        // passkey 중복 확인
        User user = userRepository.findByPasskey(passkey);

        if(user != null) {
            // 이미 등록된 사용자라면 해당 사용자 조회
            return user;
        }
        else {
            // 사용자가 등록되지 않았다면 새로 가입
            User newUser = new User(passkey);
            userRepository.save(newUser);
            return newUser;
        }
    }

    /* 사용자가 새로운 견적서 발급 */
    @Transactional
    public void createEstimate(PaperType paperType, PaperColor paperColor, int sizeX, int sizeY, int GSM, int cardQuantity, User user) {
        Estimate estimate = new Estimate(paperType, paperColor, sizeX, sizeY, GSM, cardQuantity, user);
        estimateRepository.save(estimate); // 기본정보만 저장 -> 추후 Estimate service에서 로직 호출
    }

    /* 사용자가 발급 받은 견적서 목록 조회 */
    public List<Estimate> getEstimates(Long userId) {
        return estimateRepository.findByUser(userId);
    }
}
