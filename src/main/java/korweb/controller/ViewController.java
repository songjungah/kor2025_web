package korweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// ========== 템플릿을 반환하는 컨트롤러 클래스 ========== //
@Controller
public class ViewController {

    // [1] 메인 페이지를 반환해주는 메소드
    @GetMapping("")     // http://localhost:8080
    public String index() {
        return "index.html";
    }

    // [2] 로그인 페이지를 반환해주는 메소드
    @GetMapping("/member/login")
    public String login() {
        return "/member/login.html";
    }

    // [3] 회원가입 페이지를 반환해주는 메소드
    @GetMapping("/member/signup")
    public String signup() {
        return "member/signup.html";
    }

}
