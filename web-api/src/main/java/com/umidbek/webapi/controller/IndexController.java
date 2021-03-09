package com.umidbek.webapi.controller;

import com.umidbek.data.access.service.ProfileService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class IndexController {

    private final ProfileService userService;

    public IndexController(ProfileService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView index(Model model) throws IOException {
        return new ModelAndView("index");
    }
}
