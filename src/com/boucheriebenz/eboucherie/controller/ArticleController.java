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

import com.boucheriebenz.eboucherie.model.Article;
import com.boucheriebenz.eboucherie.service.ArticleService;
import com.boucheriebenz.eboucherie.service.PhotoService;
import com.boucheriebenz.eboucherie.service.TarifService;



@Controller
@RequestMapping("/articles")
public class ArticleController {
	private static Logger logger = Logger.getLogger(ArticleController.class);
    @Resource
    private ArticleService articleService;
    @Resource
    private PhotoService photoService;
    @Resource
    private TarifService tarifService;

    @RequestMapping
    public String defaultMapping(Model model) throws Exception {
        generateModel(model);
        return "articles";
    }

    @RequestMapping(params = "save")
    public String create(@Valid Article article,
            BindingResult result, Model model) throws Exception {
        if (!result.hasErrors()) {
            model.addAttribute("article", new Article());
            articleService.save(article);
        }
        generateModel(model);
        return "articles";
    }

    @RequestMapping(params = "update")
    public String update(@RequestParam Integer update, Model model)
            throws Exception {
        Article article = articleService.getById(update);
        model.addAttribute("article", article);
        generateModel(model);
        return "articles";
    }

    @RequestMapping(params = "delete")
    public String delete(@RequestParam Integer delete, Model model)
            throws Exception {
        articleService.delete(delete);
        generateModel(model);
        return "articles";
    }

    private void generateModel(Model model) throws Exception {
        model.addAttribute("articles", articleService.getArticlesAll());
        model.addAttribute("photos", photoService.getPhotos());
        model.addAttribute("tarifs", tarifService.getTarifs());
        if (!model.containsAttribute("article")) {
            model.addAttribute("article", new Article());
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
