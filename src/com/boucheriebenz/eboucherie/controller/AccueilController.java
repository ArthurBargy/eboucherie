package com.boucheriebenz.eboucherie.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.boucheriebenz.eboucherie.service.PromotionService;

@Controller
@RequestMapping("/accueil")
public class AccueilController {

    private static Logger logger = Logger.getLogger(AccueilController.class);
    @Resource
    private PromotionService promotionService;

    @RequestMapping
    public String defaultMapping(Model model) throws Exception {
        model.addAttribute("promotions", promotionService.getPromotions());
        return "index";
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(Exception e) {
        logger.error("Error", e);
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", e);
        return mav;
    }

}
