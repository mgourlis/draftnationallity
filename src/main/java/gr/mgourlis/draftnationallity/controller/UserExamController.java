package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.dto.CreateExamDTO;
import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamSetting;
import gr.mgourlis.draftnationallity.model.User;
import gr.mgourlis.draftnationallity.service.IExamService;
import gr.mgourlis.draftnationallity.service.IExamSettingService;
import gr.mgourlis.draftnationallity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@Secured("USER")
@RequestMapping("/user/exam")
public class UserExamController {

    @Autowired
    IExamService examService;

    @Autowired
    IExamSettingService examSettingService;

    @Autowired
    IUserService userService;

    @RequestMapping("/")
    public ModelAndView getExams(ModelAndView modelAndView, Pageable pageable, Authentication authentication){
        Page<Exam> examsPage = examService.findExamsByUser(authentication.getName(),pageable);
        modelAndView.addObject("exams", examsPage.getContent());
        modelAndView.addObject("page",examsPage);
        modelAndView.setViewName("user/exam/showExams");
        return modelAndView;
    }

    @RequestMapping(value= "/new", method = RequestMethod.GET)
    public ModelAndView createExam(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(authentication.getName());
        CreateExamDTO createExamDTO = new CreateExamDTO();
        createExamDTO.setForeas(user.getForeas());
        modelAndView.addObject("examSettings", examSettingService.findAll());
        modelAndView.addObject("exam",createExamDTO);
        modelAndView.setViewName("user/exam/createExam");
        return modelAndView;
    }

    @RequestMapping(value= "/new", method = RequestMethod.POST)
    public ModelAndView createExam(@Valid @ModelAttribute("exam") CreateExamDTO createExamDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        ExamSetting examSetting = examSettingService.getOne(createExamDTO.getExamSettingId());
        if(examSetting == null){
            bindingResult
                    .rejectValue("examSettingId", "error.exam",
                            "Exam Setting does not exist ");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("examSettings", examSettingService.findAll());
            modelAndView.setViewName("user/exam/createExam");
        } else {
            try {
                String uid = examService.createExam(createExamDTO.getExam(examSetting),createExamDTO.getExamSettingId());
                redirectAttributes.addAttribute("successMessageBox", "exam with uid: " + uid + " created successfully");
                modelAndView.setViewName("redirect:/user/exam/");
            }catch (Exception e){
                modelAndView.addObject("examSettings", examSettingService.findAll());
                modelAndView.addObject("errorMessageBox", e.getMessage());
                modelAndView.setViewName("user/exam/createExam");
            }
        }
        return modelAndView;
    }

}
