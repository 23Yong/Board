package spring.board.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.board.controller.dto.PostDto;
import spring.board.domain.Post;
import spring.board.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(Model model) {
        List<PostDto.PostInfo> posts = postService.findAllPosts()
                .stream()
                .map(post ->
                        new PostDto.PostInfo(post.getTitle(), post.getCreatedTime()))
                .collect(Collectors.toList());

        model.addAttribute("posts", posts);
        return "home";
    }
}
