package paperJ.cutservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UserController {

    // 사용자 홈 화면 (user-home.html) 보여줌
    @GetMapping("/user/home")
    public String showUserHome() {
        return "user/user-home";
    }
}
