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

import com.boucheriebenz.eboucherie.model.Texte;
import com.boucheriebenz.eboucherie.service.TexteService;

@Controller
@RequestMapping("/textes")
public class TexteController {
	private static Logger logger = Logger.getLogger(TexteController.class);
    @Resource
    private TexteService texteService;


    @RequestMapping
    public String defaultMapping(Model model) throws Exception {
        generateModel(model);
        return "textes";
    }

    @RequestMapping(params = "save")
    public String create(@Valid Texte texte, BindingResult result, Model model)
            throws Exception {
        if (!result.hasErrors()) {
            model.addAttribute("texte", new Texte());
            texteService.save(texte);
            texteService.refreshContext();
        }
        generateModel(model);
        return "textes";
    }

    @RequestMapping(params = "update")
    public String update(@RequestParam String update, Model model)
            throws Exception {
        Texte texte = texteService.getByNom(update);
        model.addAttribute("texte", texte);
        generateModel(model);
        return "textes";
    }

    @RequestMapping(params = "delete")
    public String delete(@RequestParam String delete, Model model)
            throws Exception {
        texteService.delete(delete);
        texteService.refreshContext();
        generateModel(model);
        return "textes";
    }

    private void generateModel(Model model) throws Exception {
        model.addAttribute("textes", texteService.getTextes());
        if (!model.containsAttribute("texte")) {
            model.addAttribute("texte", new Texte());
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
