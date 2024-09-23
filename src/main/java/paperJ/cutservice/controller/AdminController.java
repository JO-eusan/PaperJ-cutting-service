package paperJ.cutservice.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import paperJ.cutservice.domain.Admin;
import paperJ.cutservice.domain.Estimate;
import paperJ.cutservice.service.AdminService;
import paperJ.cutservice.service.EstimateService;
import paperJ.cutservice.service.paper.PaperTypeService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final EstimateService estimateService;
    private final PaperTypeService paperTypeService;

    // 관리자 체크 페이지 (admin-check.html) 보여줌
    @GetMapping("/check")
    public String showAdminCheckPage() {
        return "admin/admin-check";
    }

    @PostMapping("/check")
    public String checkAdminKey(@RequestParam("checkkey") String checkkey, Model model) {
        // 관리자 체크값 꺼내기 위한 엔티티 생성
        Admin admin = new Admin();

        if (!checkkey.equals(admin.getCheckkey())) {
            // 틀린 경우, 에러 메시지를 모델에 추가하고 다시 admin-check.html로 이동
            model.addAttribute("errorMessage", "암호가 틀렸습니다. 다시 시도하세요.");
            return "admin/admin-check";
        }

        // 올바른 CheckKey 입력 시 다른 페이지로 이동 (관리자 대시보드)
        return "redirect:/admin/dashboard";
    }

    // 관리자 대시보드 화면 (주문 현황 및 파일 업로드)
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Estimate> estimates = estimateService.getAllEstimate();
        model.addAttribute("estimates", estimates);
        return "admin/admin-dashboard";
    }

    // 견적서 확인 페이지(사용자 발급)
    @GetMapping("/estimate/view/{id}")
    public String viewEstimate(@PathVariable Long id, Model model) {
        Estimate estimate = estimateService.getEstimateById(id);
        model.addAttribute("estimate", estimate);
        return "user/estimate-view"; // 사용자와 같은 견적서 확인 페이지 사용
    }

    // 파일 업로드 처리
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file")MultipartFile file, Model model) {
        try {
            paperTypeService.importExcel(file);
            model.addAttribute("message", "파일이 성공적으로 업로드 되었습니다.");
        } catch (Exception e) {
            model.addAttribute("message", "파일 업로드가 실패하였습니다.");
        }
        return "redirect:/admin/dashboard";
    }

    // 주문 상태 업데이트
    @PostMapping("/update-status/{id}")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam("status") String status) {
        estimateService.updateOrderStatus(id, status);
        return "redirect:/admin/dashboard"; // 상태 업데이트 후, 대시보드로 이동
    }
}


