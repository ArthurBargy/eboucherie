package com.boucheriebenz.eboucherie.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.boucheriebenz.eboucherie.model.FichierPhoto;
import com.boucheriebenz.eboucherie.service.PhotoService;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Controller
@RequestMapping("/photos")
public class PhotoController implements HandlerExceptionResolver {

    private static Logger logger = Logger.getLogger(PhotoController.class);
    @Resource
    private PhotoService photoService;

    @RequestMapping
    public String defaultMapping(Model model) throws Exception {
        generateModel(model);
        return "photos";
    }

    @RequestMapping(params = "save")
    public String create(@Valid FichierPhoto fichier, BindingResult result,
            Model model, HttpServletRequest request) throws Exception {
        if (!result.hasErrors()) {
            model.addAttribute("fichierPhoto", new FichierPhoto());
            String entrepot = request.getSession().getServletContext()
                    .getRealPath("/");
            photoService.save(entrepot, fichier);
        }
        generateModel(model);
        return "photos";
    }

    @RequestMapping(params = "delete")
    public String delete(@RequestParam Integer delete, Model model,
            HttpServletRequest request) throws Exception {
        String entrepot = request.getSession().getServletContext()
                .getRealPath("/");
        photoService.delete(entrepot, delete);
        generateModel(model);
        return "photos";
    }

    private void generateModel(Model model) throws Exception {
        model.addAttribute("photos", photoService.getPhotos());
        if (!model.containsAttribute("fichierPhoto")) {
            model.addAttribute("fichierPhoto", new FichierPhoto());
        }
    }

    @ExceptionHandler
    public ModelAndView exceptionHandler(Exception e) {
        logger.error("Error", e);
        if (e instanceof MySQLIntegrityConstraintViolationException) {
            e = new Exception("Cette photo est liée à un autre élément");
        }
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", e);
        return mav;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof MaxUploadSizeExceededException) {
            ex = new Exception("Cette photo excède la taille maximale");
        }
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("exception", ex);
        return mav;
    }

}
