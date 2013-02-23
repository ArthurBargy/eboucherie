package com.boucheriebenz.eboucherie.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.boucheriebenz.eboucherie.model.Promotion;
import com.boucheriebenz.eboucherie.service.ArticleService;
import com.boucheriebenz.eboucherie.service.PhotoService;
import com.boucheriebenz.eboucherie.service.PromotionService;

@Controller
@RequestMapping("/promotions")
public class PromotionController {

    private static Logger logger = Logger.getLogger(PromotionController.class);
    @Resource
    private PromotionService promotionService;
    @Resource
    private PhotoService photoService;
    @Resource
    private ArticleService articleService;

    @RequestMapping
    public String defaultMapping(Model model) throws Exception {
        generateModel(model);
        return "promotions";
    }

    @RequestMapping(params = "save")
    public String create(@Valid Promotion promotion,
            BindingResult result, Model model) throws Exception {
        if (!result.hasErrors()) {
            model.addAttribute("promotion", new Promotion());
            promotionService.save(promotion);
        }
        generateModel(model);
        return "promotions";
    }

    @RequestMapping(params = "update")
    public String update(@RequestParam Integer update, Model model)
            throws Exception {
        Promotion promotion = promotionService.getById(update);
        model.addAttribute("promotion", promotion);
        generateModel(model);
        return "promotions";
    }

    @RequestMapping(params = "delete")
    public String delete(@RequestParam Integer delete, Model model)
            throws Exception {
        promotionService.delete(delete);
        generateModel(model);
        return "promotions";
    }

    private void generateModel(Model model) throws Exception {
        model.addAttribute("promotions", promotionService.getPromotionsAll());
        model.addAttribute("photos", photoService.getPhotos());
        model.addAttribute("articles", articleService.getArticles());
        if (!model.containsAttribute("promotion")) {
            model.addAttribute("promotion", new Promotion());
        }
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(Exception e) {
        logger.error("Error", e);
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", e);
        return mav;
    }

}
