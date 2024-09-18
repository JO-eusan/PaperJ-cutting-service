package paperJ.cutservice.service.paper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paperJ.cutservice.domain.paper.PaperColor;
import paperJ.cutservice.domain.paper.PaperType;
import paperJ.cutservice.repository.paper.PaperColorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaperColorService {

    private final PaperColorRepository paperColorRepository;

    /* 특정 PaperType과 GSM에 해당하는 PaperColor의 목록 조회 */
    public List<String> getColorByPaperType(PaperType paperType, int gsm) {
        return paperColorRepository.findColorsByPaperTypeAndGSM(paperType.getName(), gsm);
    }
}
