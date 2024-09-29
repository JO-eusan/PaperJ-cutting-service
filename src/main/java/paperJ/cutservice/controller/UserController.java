package paperJ.cutservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import paperJ.cutservice.controller.dto.OrderRequestDto;
import paperJ.cutservice.domain.Estimate;
import paperJ.cutservice.domain.User;
import paperJ.cutservice.service.EstimateService;
import paperJ.cutservice.service.OrderService;
import paperJ.cutservice.service.UserService;
import paperJ.cutservice.service.paper.PaperColorService;
import paperJ.cutservice.service.paper.PaperTypeService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EstimateService estimateService;
    private final PaperTypeService paperTypeService;
    private final PaperColorService paperColorService;
    private final OrderService orderService;

    // 사용자 홈 화면 (user-home.html) 보여줌
    @GetMapping("/user/home/{userId}")
    public String showUserHome(@PathVariable("userId") Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("userId", userId);

        return "user/user-home";
    }

    // 견적서 발급 화면
    @GetMapping("/user/estimate/new/{userId}")
    public String showCreateEstimateForm(@PathVariable("userId") Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("paperTypes", paperTypeService.getAllPaperTypes());

        return "user/create-estimate"; // 견적서 발급 화면
    }

    // 종이 이름에 따른 GSM 목록 불러오기
    @ResponseBody
    @GetMapping("/user/gsm")
    public List<Integer> getGsmByPaperType(@RequestParam("paperTypeName") String paperTypeName) {
        return paperTypeService.getGSMbyPaperType(paperTypeName);
    }

    // 종이 이름과 GSM에 따른 종이 색상 목록 불러오기
    @ResponseBody
    @GetMapping("/user/paperColors")
    public List<String> getPaperColorsByPaperTypeAndGsm(@RequestParam("paperTypeName") String paperTypeName, @RequestParam("gsm") int gsm) {
        return paperColorService.getColorByPaperType(paperTypeName, gsm);
    }

    // 입력받은 견적서 정보로 견적서 생성
    @PostMapping("/user/estimate/new/{userId}")
    public String createEstimate(@PathVariable("userId") Long userId,
                                 @RequestParam("paperName") String paperName,
                                 @RequestParam("paperColor") String paperColor,
                                 @RequestParam("gsm") int gsm,
                                 @RequestParam("sizeX") int sizeX,
                                 @RequestParam("sizeY") int sizeY,
                                 @RequestParam("cardQuantity") int cardQuantity) {

        User user = userService.getUserById(userId);

        Long paperTypeId = paperTypeService.getPaperTypeByNameAndGSM(paperName, gsm).getId();
        Long paperColorId = paperColorService.getPaperColorByNameAndType(paperColor, paperTypeId).getId();

        Estimate estimate = estimateService.createEstimate(userId, paperTypeId, paperColorId, gsm, sizeX, sizeY, cardQuantity);
        return "redirect:/estimate/view/" + userId + "/" + estimate.getId(); // 견적서 정보로 리다이렉트
    }

    // 견적서 목록 화면
    @GetMapping("/user/estimate/list/{userId}")
    public String showUserEstimates(@PathVariable("userId") Long userId, Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("estimates", estimateService.getEstimateByUserId(user.getId()));
        model.addAttribute("userId", userId); // passkey를 view로 전달
        return "user/estimate-list"; // 사용자가 발급한 견적서 목록 화면
    }

    // 견적서 확인 페이지 (견적서 자세히 보기 -> 주문 가능 여부에 따른 화면)
    @GetMapping("/estimate/view/{userId}/{id}")
    public String viewEstimate(@PathVariable("userId") Long userId, @PathVariable Long id, Model model) {
        User user = userService.getUserById(userId);
        Estimate estimate = estimateService.getEstimateById(id);

        model.addAttribute("userId", userId);
        model.addAttribute("estimate", estimate);
        model.addAttribute("role", "USER"); // 사용자 설정

        if(!estimate.isOrder()) // 주문을 안했다면
            return "user/estimate-view"; // 주문 가능한 화면
        else // 주문을 했다면
            return "user/estimate-only-view"; // 정보만 확인 가능한 화면
    }

    @PostMapping("/user/order")
    @ResponseBody
    public ResponseEntity<String> requestOrder(@RequestBody OrderRequestDto orderRequest) {
        try {
            orderService.requestOrder(orderRequest.getEstimateId(), orderRequest.getContact(), orderRequest.getName());
            return ResponseEntity.ok("주문이 성공적으로 처리되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
