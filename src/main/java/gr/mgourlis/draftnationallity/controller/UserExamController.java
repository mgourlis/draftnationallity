package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.dto.*;
import gr.mgourlis.draftnationallity.model.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Iterator;


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
    public ModelAndView getExams(@RequestParam(name = "lfile", required = false, defaultValue = "") String lfile,
                                 @RequestParam(name = "successMessageBox", required = false) String successMessageBox,
                                 @RequestParam(name = "errorMessageBox", required = false) String errorMessageBox,
                                 ModelAndView modelAndView,
                                 Pageable pageable,
                                 Authentication authentication){
        Page<Exam> examsPage = examService.findExamsByUser(authentication.getName(),pageable);
        modelAndView.addObject("successMessageBox",successMessageBox);
        modelAndView.addObject("errorMessageBox",errorMessageBox);
        modelAndView.addObject("exams", examsPage.getContent());
        modelAndView.addObject("page",examsPage);
        modelAndView.setViewName("user/exam/showExams");
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView showExam(@PathVariable("id") long id,
                                 @RequestParam(name = "successMessageBox", required = false) String successMessageBox,
                                 @RequestParam(name = "errorMessageBox", required = false) String errorMessageBox,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam != null) {
            modelAndView.addObject("successMessageBox",successMessageBox);
            modelAndView.addObject("errorMessageBox",errorMessageBox);
            Collections.sort(exam.getExamQuestions());
            modelAndView.addObject("exam", exam);
            modelAndView.setViewName("user/exam/showExam");
            return modelAndView;
        }else{
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
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
                modelAndView.addObject("errorMessageBox", "Error: " + e.getMessage());
                modelAndView.setViewName("user/exam/createExam");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value= "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editExam(@PathVariable("id") long id,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam != null) {
            EditExamDTO editExamDTO = new EditExamDTO();
            editExamDTO.init(exam);
            modelAndView.addObject("exam", editExamDTO);
            modelAndView.setViewName("user/exam/editExam");
            return modelAndView;
        }else{
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
    }

    @RequestMapping(value= "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editExam(@PathVariable("id") long id,
                                   @Valid @ModelAttribute("exam") EditExamDTO editExamDTO,
                                   BindingResult bindingResult,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam == null){
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/exam/editExam");
        } else {
            try {
                examService.editExam(exam, editExamDTO);
                redirectAttributes.addAttribute("successMessageBox", "Exam " + exam.getuID() + " edited successfully.");
                modelAndView.setViewName("redirect:/user/exam/");
            }catch (Exception e){
                modelAndView.addObject("errorMessageBox", "Error: " + e.getMessage());
                modelAndView.setViewName("user/exam/editExam");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value= "/setanswers/{id}", method = RequestMethod.GET)
    public ModelAndView answerExam(@PathVariable("id") long id,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam != null) {
            AnswerExamDTO answerExamDTO = new AnswerExamDTO();
            Collections.sort(exam.getExamQuestions());
            answerExamDTO.init(exam);
            modelAndView.addObject("exam", answerExamDTO);
            modelAndView.setViewName("user/exam/addAnswers");
            return modelAndView;
        }else{
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
    }

    @RequestMapping(value= "/setanswers/{id}", method = RequestMethod.POST, params="action=tempsave")
    public ModelAndView saveTemporaryAswers(@PathVariable("id") long id,
                                        @Valid @ModelAttribute("exam") AnswerExamDTO answerExamDTO,
                                        BindingResult bindingResult,
                                        Authentication authentication,
                                        RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam == null){
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/exam/addAnswers");
        } else {
            try {
                examService.setExamAnswers(exam,answerExamDTO.getExamQuestionsDTO(),false);
                redirectAttributes.addAttribute("successMessageBox", "Exam answers saved temporally, please finalize when you are ready.");
                modelAndView.setViewName("redirect:/user/exam/" + exam.getId());
            }catch (Exception e){
                modelAndView.addObject("errorMessageBox", "Error: " + e.getMessage());
                modelAndView.setViewName("user/exam/addAnswers");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value= "/setanswers/{id}", method = RequestMethod.POST, params="action=save")
    public ModelAndView saveFinalAswers(@PathVariable("id") long id,
                                 @Valid @ModelAttribute("exam") AnswerExamDTO answerExamDTO,
                                 BindingResult bindingResult,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam == null){
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
        Iterator<ExamQuestionDTO> iterExamQuestionDTO = answerExamDTO.getExamQuestionsDTO().iterator();
        int index = 0;
        while (iterExamQuestionDTO.hasNext()) {
            ExamQuestionDTO examQuestionDTO =iterExamQuestionDTO.next();
            if(examQuestionDTO.getAnswertext().equals("")){
                bindingResult.rejectValue("examQuestionsDTO[" + index + "].answertext", "error.exam", "The answer cannot be empty");
            }
            index++;
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/exam/addAnswers");
        } else {
            try {
                examService.setExamAnswers(exam,answerExamDTO.getExamQuestionsDTO(),true);
                redirectAttributes.addAttribute("successMessageBox", "Exam answers finalized.");
                modelAndView.setViewName("redirect:/user/exam/" + exam.getId());
            }catch (Exception e){
                modelAndView.addObject("errorMessageBox", "Error: " + e.getMessage());
                modelAndView.setViewName("user/exam/addAnswers");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value= "/setrating/{id}", method = RequestMethod.GET)
    public ModelAndView rateExam(@PathVariable("id") long id,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam != null) {
            RateExamDTO rateExamDTO = new RateExamDTO();
            rateExamDTO.init(exam);
            modelAndView.addObject("exam", rateExamDTO);
            modelAndView.setViewName("user/exam/addRating");
            return modelAndView;
        }else{
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
    }

    @RequestMapping(value= "/setrating/{id}", method = RequestMethod.POST, params="action=tempsave")
    public ModelAndView saveTemporaryRating(@PathVariable("id") long id,
                                            @Valid @ModelAttribute("exam") AnswerExamDTO answerExamDTO,
                                            BindingResult bindingResult,
                                            Authentication authentication,
                                            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }

    @RequestMapping(value= "/setrating/{id}", method = RequestMethod.POST, params="action=save")
    public ModelAndView saveFinalRating(@PathVariable("id") long id,
                                            @Valid @ModelAttribute("exam") AnswerExamDTO answerExamDTO,
                                            BindingResult bindingResult,
                                            Authentication authentication,
                                            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        return modelAndView;
    }
}
