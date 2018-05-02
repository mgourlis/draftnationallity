package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.dto.ValidateExamDTO;
import gr.mgourlis.draftnationallity.model.Exam;
import gr.mgourlis.draftnationallity.model.ExamStatus;
import gr.mgourlis.draftnationallity.service.IExamService;
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

@Controller
@Secured("MODERATOR")
@RequestMapping("/moderator/exam")
public class ModeratorExamController {

    @Autowired
    IExamService examService;

    @RequestMapping("/")
    public ModelAndView getExams(@RequestParam(name = "file", required = false, defaultValue = "") String file,
                                 @RequestParam(name = "lfile", required = false, defaultValue = "") String lfile,
                                 @RequestParam(name = "uid", required = false, defaultValue = "") String uid,
                                 @RequestParam(name = "successMessageBox", required = false) String successMessageBox,
                                 @RequestParam(name = "errorMessageBox", required = false) String errorMessageBox,
                                 ModelAndView modelAndView,
                                 Pageable pageable,
                                 Authentication authentication){
        Page<Exam> examsPage;
        if(lfile.equals("") && file.equals("") && uid.equals("")) {
            examsPage = examService.findAllByStatus(ExamStatus.FINALIZED, pageable);
        }
        else {
            if (lfile.equals(""))
                lfile = null;
            if (file.equals(""))
                file = null;
            if (uid.equals(""))
                uid = null;
            examsPage = examService.findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatus(lfile, file, uid, ExamStatus.FINALIZED, pageable);
        }
        modelAndView.addObject("successMessageBox",successMessageBox);
        modelAndView.addObject("errorMessageBox",errorMessageBox);
        modelAndView.addObject("exams", examsPage.getContent());
        modelAndView.addObject("page",examsPage);
        modelAndView.setViewName("moderator/exam/showExams");
        return modelAndView;
    }

    @RequestMapping("/validated")
    public ModelAndView getValidatedExams(@RequestParam(name = "file", required = false, defaultValue = "") String file,
                                 @RequestParam(name = "lfile", required = false, defaultValue = "") String lfile,
                                 @RequestParam(name = "uid", required = false, defaultValue = "") String uid,
                                 @RequestParam(name = "successMessageBox", required = false) String successMessageBox,
                                 @RequestParam(name = "errorMessageBox", required = false) String errorMessageBox,
                                 ModelAndView modelAndView,
                                 Pageable pageable,
                                 Authentication authentication){
        Page<Exam> examsPage;
        if(lfile.equals("") && file.equals("") && uid.equals("")) {
            examsPage = examService.findAllByStatus(ExamStatus.VALIDATED, pageable);
        }
        else {
            if (lfile.equals(""))
                lfile = null;
            if (file.equals(""))
                file = null;
            if (uid.equals(""))
                uid = null;
            examsPage = examService.findExamsByLocalFileNumberContainingOrFileNumberContainingOrUIDContainingAndStatus(lfile, file, uid, ExamStatus.VALIDATED, pageable);
        }
        modelAndView.addObject("successMessageBox",successMessageBox);
        modelAndView.addObject("errorMessageBox",errorMessageBox);
        modelAndView.addObject("exams", examsPage.getContent());
        modelAndView.addObject("page",examsPage);
        modelAndView.setViewName("moderator/exam/showExams");
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView showExam(@PathVariable("id") long id,
                                 @RequestParam(name = "successMessageBox", required = false) String successMessageBox,
                                 @RequestParam(name = "errorMessageBox", required = false) String errorMessageBox,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOne(id);
        if(exam != null) {
            if(exam.getStatus() == ExamStatus.FINALIZED || exam.getStatus() == ExamStatus.VALIDATED) {
                modelAndView.addObject("successMessageBox", successMessageBox);
                modelAndView.addObject("errorMessageBox", errorMessageBox);
                Collections.sort(exam.getExamQuestions());
                modelAndView.addObject("exam", exam);
                modelAndView.setViewName("moderator/exam/showExam");
                return modelAndView;
            }else{
                redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
                modelAndView.setViewName("redirect:/moderator/exam/");
                return modelAndView;
            }
        }else{
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/moderator/exam/");
            return modelAndView;
        }
    }

    @RequestMapping(value= "/validate/{id}", method = RequestMethod.GET)
    public ModelAndView validateExam(@PathVariable("id") long id,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOne(id);
        if(exam != null) {
            if(exam.getStatus() == ExamStatus.FINALIZED || exam.getStatus() == ExamStatus.VALIDATED) {
                ValidateExamDTO validateExamDTO = new ValidateExamDTO();
                validateExamDTO.init(exam);
                modelAndView.addObject("exam", validateExamDTO);
                modelAndView.setViewName("moderator/exam/validateExam");
                return modelAndView;
            }else{
                redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
                modelAndView.setViewName("redirect:/moderator/exam/");
                return modelAndView;
            }
        }else{
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/moderator/exam/");
            return modelAndView;
        }
    }

    @RequestMapping(value= "/validate/{id}", method = RequestMethod.POST, params="action=tempsave")
    public ModelAndView temporaryValidateExam(@PathVariable("id") long id,
                                            @Valid @ModelAttribute("exam") ValidateExamDTO validateExamDTO,
                                            BindingResult bindingResult,
                                            Authentication authentication,
                                            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOne(id);
        if(exam == null){
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/moderator/exam/");
            return modelAndView;
        }
        if(exam.getStatus() == ExamStatus.FINALIZED || exam.getStatus() == ExamStatus.VALIDATED) {
            if (bindingResult.hasErrors()) {
                modelAndView.setViewName("moderator/exam/validateExam");
            } else {
                try {
                    examService.validateExam(exam, validateExamDTO.getFileNumber(), authentication.getName(),false);
                    redirectAttributes.addAttribute("successMessageBox", "Exam ratings saved temporally, please finalize when you are ready.");
                    modelAndView.setViewName("redirect:/moderator/exam/" + exam.getId());
                } catch (Exception e) {
                    modelAndView.addObject("errorMessageBox", "Error: " + e.getMessage());
                    modelAndView.setViewName("moderator/exam/validateExam");
                }
            }
        }else{
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/moderator/exam/");
        }
        return modelAndView;
    }

    @RequestMapping(value= "/validate/{id}", method = RequestMethod.POST, params="action=save")
    public ModelAndView saveValidateExam(@PathVariable("id") long id,
                                              @Valid @ModelAttribute("exam") ValidateExamDTO validateExamDTO,
                                              BindingResult bindingResult,
                                              Authentication authentication,
                                              RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOne(id);
        if(exam == null){
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/moderator/exam/");
            return modelAndView;
        }
        if(exam.getStatus() == ExamStatus.FINALIZED || exam.getStatus() == ExamStatus.VALIDATED) {
            if (bindingResult.hasErrors()) {
                modelAndView.setViewName("moderator/exam/validateExam");
            } else {
                try {
                    examService.validateExam(exam, validateExamDTO.getFileNumber(), authentication.getName(), true);
                    redirectAttributes.addAttribute("successMessageBox", "Exam ratings saved temporally, please finalize when you are ready.");
                    modelAndView.setViewName("redirect:/moderator/exam/" + exam.getId());
                } catch (Exception e) {
                    modelAndView.addObject("errorMessageBox", "Error: " + e.getMessage());
                    modelAndView.setViewName("moderator/exam/validateExam");
                }
            }
        }else{
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/moderator/exam/");
        }
        return modelAndView;
    }
}
