package paperJ.cutservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import paperJ.cutservice.domain.Admin;
import paperJ.cutservice.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home"; // home.html로 이동 (홈 화면)
    }

    // passkey를 입력받아 처리
    @PostMapping("/login")
    public String login(@RequestParam("passkey") String passkey) {
        log.info("User passkey: {}", passkey);

        Admin admin = new Admin();

        // 관리자 체크
        if(passkey.equals(admin.getPasskey())) {
            log.info("Redirecting to admin check page.");
            return "redirect:/admin/check"; // 관리자 체크 페이지로 이동 -> AdminController
        } else {
            log.info("Redirecting to user home page.");

            userService.joinOrFindUser(passkey);

            return "redirect:/user/home"; // 일반 사용자 화면으로 이동 -> UserController
        }
    }
}
