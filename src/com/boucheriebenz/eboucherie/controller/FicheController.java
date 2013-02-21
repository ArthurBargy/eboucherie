package com.boucheriebenz.eboucherie.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boucheriebenz.eboucherie.service.AnimauxService;
import com.boucheriebenz.eboucherie.service.FicheService;

@Controller
@RequestMapping("/ficheanimal")
public class FicheController {
	
	@Resource
    private FicheService ficheService;

    @RequestMapping
    public String defaultMapping(Model model,@RequestParam("id") Integer id) throws Exception {
    	
        model.addAttribute("ficheanimal", ficheService.getById(id));
        
        return "ficheanimal";
    }



}
