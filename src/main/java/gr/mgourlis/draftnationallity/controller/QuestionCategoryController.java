package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.model.QuestionCategory;
import gr.mgourlis.draftnationallity.repository.QuestionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Secured("ADMIN")
@RequestMapping("/admin/question-category")
public class QuestionCategoryController {

    @Autowired
    QuestionCategoryRepository questionCategoryRepository;

    @RequestMapping("/")
    public ModelAndView getCategories(ModelAndView modelAndView, Pageable pageable){
        modelAndView = new ModelAndView();
        Page<QuestionCategory> categoryPage = questionCategoryRepository.findAll(pageable);
        modelAndView.addObject("categories", categoryPage.getContent());
        modelAndView.addObject("page",categoryPage);
        modelAndView.setViewName("admin/category/showCategories");
        return modelAndView;
    }
}
