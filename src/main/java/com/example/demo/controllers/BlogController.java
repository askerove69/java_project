package com.example.demo.controllers;


import com.example.demo.models.Post;
import com.example.demo.repo.PostRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
public class BlogController {


    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    @PreAuthorize("permitAll()")
    public ModelAndView blogMain() {
        Iterable<Post> posts = postRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("blog-main");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }
    @GetMapping("/blog/add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String blogPostApp (
            @RequestParam String title,
            @RequestParam String anons,
            @RequestParam String full_text,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dt,
            Model model) {

        Post post = new Post(title, anons, full_text, dt);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res  = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res  = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "blog-edit";

    }

    @PostMapping("/blog/{id}/edit")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String blogPostUpdate(
            @PathVariable(value = "id") long id,
            @ModelAttribute("post") Post post,
            Model model
    ) {
        // Загрузка существующего объекта Post из репозитория
        Post existingPost = postRepository.findById(id).orElseThrow();

        // Обновление полей существующего объекта
        existingPost.setTitle(post.getTitle());
        existingPost.setAnons(post.getAnons());
        existingPost.setFull_text(post.getFull_text());
        existingPost.setDt(post.getDt());

        // Сохранение обновленного объекта в репозитории
        postRepository.save(existingPost);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }




}
