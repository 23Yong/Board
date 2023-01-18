package spring.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p>
 *     메인 컨트롤러
 * </p>
 */
@Controller
public class MainController {

    /**
     * 게시판 홈 /articles로 포워딩 시킨다
     * @return
     */
    @GetMapping("/")
    public String root() {
        return "forward:/articles";
    }
}
