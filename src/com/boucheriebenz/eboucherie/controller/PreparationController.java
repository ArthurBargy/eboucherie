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

import com.boucheriebenz.eboucherie.model.Preparation;
import com.boucheriebenz.eboucherie.service.PreparationService;

@Controller
@RequestMapping("/preparations")
public class PreparationController {
	private static Logger logger = Logger.getLogger(PreparationController.class);
    @Resource
    private PreparationService preparationService;


    @RequestMapping
    public String defaultMapping(Model model) throws Exception {
        generateModel(model);
        return "preparations";
    }

    @RequestMapping(params = "save")
    public String create(@Valid Preparation preparation, BindingResult result,
            Model model) throws Exception {
        if (!result.hasErrors()) {
            model.addAttribute("preparation", new Preparation());
            preparationService.save(preparation);
        }
        generateModel(model);
        return "preparations";
    }

    @RequestMapping(params = "update")
    public String update(@RequestParam Integer update, Model model)
            throws Exception {
        Preparation preparation = preparationService.getById(update);
        model.addAttribute("preparation", preparation);
        generateModel(model);
        return "preparations";
    }

    @RequestMapping(params = "delete")
    public String delete(@RequestParam Integer delete, Model model)
            throws Exception {
        preparationService.delete(delete);
        generateModel(model);
        return "preparations";
    }

    private void generateModel(Model model) throws Exception {
        model.addAttribute("preparations", preparationService.getPreparations());
        if (!model.containsAttribute("preparation")) {
            model.addAttribute("preparation", new Preparation());
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
