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


import com.boucheriebenz.eboucherie.model.Tarif;
import com.boucheriebenz.eboucherie.service.TarifService;

@Controller
@RequestMapping("/tarifs")
public class TarifController {
	private static Logger logger = Logger.getLogger(TarifController.class);
    @Resource
    private TarifService tarifService;
    

    @RequestMapping
    public String defaultMapping(Model model) throws Exception {
        generateModel(model);
        return "tarifs";
    }

    @RequestMapping(params = "save")
    public String create(@Valid Tarif tarif,
            BindingResult result, Model model) throws Exception {
        if (!result.hasErrors()) {
            model.addAttribute("tarif", new Tarif());
            tarifService.save(tarif);
        }
        generateModel(model);
        return "tarifs";
    }

    @RequestMapping(params = "update")
    public String update(@RequestParam Integer update, Model model)
            throws Exception {
        Tarif tarif = tarifService.getById(update);
        model.addAttribute("tarif", tarif);
        generateModel(model);
        return "tarifs";
    }

    @RequestMapping(params = "delete")
    public String delete(@RequestParam Integer delete, Model model)
            throws Exception {
        tarifService.delete(delete);
        generateModel(model);
        return "tarifs";
    }

    private void generateModel(Model model) throws Exception {
        model.addAttribute("tarifs", tarifService.getTarifs());          
        if (!model.containsAttribute("tarif")) {
            model.addAttribute("tarif", new Tarif());
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
