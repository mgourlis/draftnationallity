package gr.mgourlis.draftnationallity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Secured("ADMIN")
@RequestMapping("/admin/exam-setting")
public class ExamSettingController {


    @RequestMapping("/")
    public ModelAndView getExamSettings(ModelAndView modelAndView, Pageable pageable){

        return modelAndView;
    }

}
