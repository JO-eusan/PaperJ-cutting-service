package paperJ.cutservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import paperJ.cutservice.domain.Admin;

@Slf4j
@Controller
public class AdminController {

    // 관리자 체크 페이지 (admin-check.html) 보여줌
    @GetMapping("/admin/check")
    public String showAdminCheckPage() {
        return "admin/admin-check";
    }

    // 관리자 대시보드 화면 -> 개발 필요
    @GetMapping("/admin/dashboard")
    public String showDashboard() {
        return "admin/admin-dashboard";
    }

    @PostMapping("/admin/check")
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
}
