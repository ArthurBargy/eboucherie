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

import com.boucheriebenz.eboucherie.model.Tva;
import com.boucheriebenz.eboucherie.service.TvaService;

@Controller
@RequestMapping("/tvas")
public class TvaController {
	private static Logger logger = Logger.getLogger(TvaController.class);
    @Resource
    private TvaService tvaService;


    @RequestMapping
    public String defaultMapping(Model model) throws Exception {
        generateModel(model);
        return "tvas";
    }

    @RequestMapping(params = "save")
    public String create(@Valid Tva tva, BindingResult result, Model model)
            throws Exception {
        if (!result.hasErrors()) {
            model.addAttribute("tva", new Tva());
            tvaService.save(tva);
        }
        generateModel(model);
        return "tvas";
    }

    @RequestMapping(params = "update")
    public String update(@RequestParam Integer update, Model model)
            throws Exception {
        Tva tva = tvaService.getById(update);
        model.addAttribute("tva", tva);
        generateModel(model);
        return "tvas";
    }

    @RequestMapping(params = "delete")
    public String delete(@RequestParam Integer delete, Model model)
            throws Exception {
        tvaService.delete(delete);
        generateModel(model);
        return "tvas";
    }

    private void generateModel(Model model) throws Exception {
        model.addAttribute("tvas", tvaService.getTVAs());
        if (!model.containsAttribute("tva")) {
            model.addAttribute("tva", new Tva());
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
