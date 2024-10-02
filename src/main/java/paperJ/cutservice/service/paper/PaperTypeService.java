package paperJ.cutservice.service.paper;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import paperJ.cutservice.domain.paper.PaperColor;
import paperJ.cutservice.domain.paper.PaperType;
import paperJ.cutservice.repository.paper.PaperColorRepository;
import paperJ.cutservice.repository.paper.PaperTypeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaperTypeService {

    private final PaperTypeRepository paperTypeRepository;
    private final PaperColorRepository paperColorRepository;

    /* 모든 PaperType 조회 */
    public List<String> getAllPaperTypes() {
        return paperTypeRepository.findAllPaperTypes();
    }

    /* ID로 종이 종류 조회 */
    public PaperType getPaperTypeById(Long paperTypeId) {
        return paperTypeRepository.findById(paperTypeId);
    }

    /* 이름과 gsm으로 paperType 조회 */
    public PaperType getPaperTypeByNameAndGSM(String name, int gsm) {
        return paperTypeRepository.findByNameAndGSM(name, gsm);
    }

    /* 종이 이름으로 GSM으로 papertype ID 조회 */
    public List<Integer> getGSMbyPaperType(String paperTypeName) {
        return paperTypeRepository.findGSMbyPaperType(paperTypeName);
    }

    /* 종이 이름과 GSM으로 색상 조회 */
    public List<String> getColorByPaperTypeAndGSM(String name, int gsm) {
        return paperColorRepository.findColorsByPaperTypeAndGSM(name, gsm);
    }

    /* 전달받은 파일에서 PaperType 및 PaperColor 데이터 저장 */
    @Transactional
    public void importExcel(MultipartFile file) throws Exception {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);  // 첫 번째 시트 사용
            PaperType currentPaperType = null;

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 첫 번째 줄은 헤더

                // PaperType 정보 추출
                String paperTypeName = row.getCell(0).getStringCellValue();
                int paperSizeX = (int) row.getCell(1).getNumericCellValue();
                int paperSizeY = (int) row.getCell(2).getNumericCellValue();
                int gsm = (int) row.getCell(3).getNumericCellValue();

                // PaperColor 정보 추출
                String paperColorName = row.getCell(4).getStringCellValue();
                int pricePerSheet = (int) row.getCell(5).getNumericCellValue();

                // PaperType이 변경된 경우 새로 조회 또는 생성
                if (currentPaperType == null ||
                        !currentPaperType.getName().equals(paperTypeName) ||
                        currentPaperType.getGSM() != gsm) {

                    // 종이 이름과 GSM으로 PaperType 조회
                    currentPaperType = paperTypeRepository.findByNameAndGSM(paperTypeName, gsm);
                    if (currentPaperType == null) {
                        // 없으면 새로 생성
                        currentPaperType = new PaperType(paperTypeName, paperSizeX, paperSizeY, gsm);
                        paperTypeRepository.save(currentPaperType);
                    }
                }

                // PaperColor 생성 및 저장
                PaperColor paperColor = new PaperColor(paperColorName, pricePerSheet, currentPaperType);
                paperColorRepository.save(paperColor);
            }
        }
    }
}
