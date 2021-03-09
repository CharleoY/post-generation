package com.umidbek.webapi.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/search")
public class SearchController {

    @GetMapping
    public ModelAndView getSearchPage(Model model) {
        return new ModelAndView("search");
    }
}
