package com.boucheriebenz.eboucherie.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boucheriebenz.eboucherie.service.AnimauxService;
import com.boucheriebenz.eboucherie.service.PromotionService;

@Controller
@RequestMapping("/animaux")
public class AnimauxController {

	@Resource
    private AnimauxService animauxService;

    @RequestMapping
    public String defaultMapping(Model model, @RequestParam("page") Integer num_page) throws Exception {
    	
    	int page = 1;
    	int elementsParPage = 9;
    	if(num_page != null){
    		page = num_page;
    	}
    	
        model.addAttribute("animaux", animauxService.getAnimaux((page-1)*elementsParPage,elementsParPage));
        
        int tailleTable = animauxService.getTailleTable();
        int nombrePages = (int) Math.ceil(tailleTable * 1.0 / elementsParPage);
        
        model.addAttribute("tailleTable", tailleTable);
        model.addAttribute("nombrePages", nombrePages);
        model.addAttribute("pageActuelle", page);

        return "animaux";
    }

}
