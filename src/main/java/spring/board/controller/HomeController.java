package spring.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.board.common.annotation.LoginCheck;
import spring.board.domain.member.UserAccount;
import spring.board.service.ArticleService;

import java.util.List;
import java.util.stream.Collectors;

import static spring.board.controller.dto.MemberDto.*;
import static spring.board.controller.dto.ArticleDto.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String home(@LoginCheck UserAccount loginUserAccount, Model model, Pageable pageable) {
        Page<ArticleInfo> articles = articleService.findAllArticles(pageable);
        List<ArticleInfo> articleInfoList = articles.stream().collect(Collectors.toList());

        model.addAttribute("articles", articleInfoList);
        model.addAttribute("articlePage", articles);

        if (loginUserAccount == null) {
            return "home";
        }

        MemberInfo member = MemberInfo.builder()
                .userId(loginUserAccount.getUserId())
                .nickname(loginUserAccount.getNickname())
                .build();

        model.addAttribute("member", member);
        return "home";
    }
}
