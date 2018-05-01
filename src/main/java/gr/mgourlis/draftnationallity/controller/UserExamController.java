package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.dto.*;
import gr.mgourlis.draftnationallity.model.*;
import gr.mgourlis.draftnationallity.service.*;
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
import java.util.List;


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

    @Autowired
    IExamRatingTypeService examRatingTypeService;

    @Autowired
    IExamRatingMarkService examRatingMarkService;

    @RequestMapping("/")
    public ModelAndView getExams(@RequestParam(name = "lfile", required = false, defaultValue = "") String lfile,
                                 @RequestParam(name = "successMessageBox", required = false) String successMessageBox,
                                 @RequestParam(name = "errorMessageBox", required = false) String errorMessageBox,
                                 ModelAndView modelAndView,
                                 Pageable pageable,
                                 Authentication authentication){
        Page<Exam> examsPage;
        if(lfile.equals(""))
            examsPage = examService.findExamsByUser(authentication.getName(),pageable);
        else
            examsPage = examService.findExamsByLocalFileNumberAndUser(lfile,authentication.getName(),pageable);
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
            List<ExamRatingType> examRatingTypeList;
            if(exam.isLanguageExemption())
                examRatingTypeList = examRatingTypeService.findByLanguageType(false);
            else
                examRatingTypeList = examRatingTypeService.findAll();
            List<ExamRatingMark> examRatingMarkList = examRatingMarkService.findAll();
            RateExamDTO rateExamDTO = new RateExamDTO();
            rateExamDTO.init(exam);
            if(rateExamDTO.getExamRatingsDTO().isEmpty()){
                for (ExamRatingType examRatingType : examRatingTypeList) {
                    ExamRatingDTO examRatingDTO = new ExamRatingDTO();
                    examRatingDTO.setRatingTypeId(examRatingType.getId());
                    examRatingDTO.setRatingTypeName(examRatingType.getRatingType());
                    rateExamDTO.getExamRatingsDTO().add(examRatingDTO);
                }
            }
            modelAndView.addObject("marks",examRatingMarkList);
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
                                            @Valid @ModelAttribute("exam") RateExamDTO rateExamDTO,
                                            BindingResult bindingResult,
                                            Authentication authentication,
                                            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam == null){
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("marks",examRatingMarkService.findAll());
            modelAndView.setViewName("user/exam/addRating");
        } else {
            try {
                examService.rateExam(exam,rateExamDTO.getExamRatingsDTO(),false);
                redirectAttributes.addAttribute("successMessageBox", "Exam ratings saved temporally, please finalize when you are ready.");
                modelAndView.setViewName("redirect:/user/exam/" + exam.getId());
            }catch (Exception e){
                modelAndView.addObject("marks",examRatingMarkService.findAll());
                modelAndView.addObject("errorMessageBox", "Error: " + e.getMessage());
                modelAndView.setViewName("user/exam/addRating");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value= "/setrating/{id}", method = RequestMethod.POST, params="action=save")
    public ModelAndView saveFinalRating(@PathVariable("id") long id,
                                            @Valid @ModelAttribute("exam") RateExamDTO rateExamDTO,
                                            BindingResult bindingResult,
                                            Authentication authentication,
                                            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam == null){
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("marks",examRatingMarkService.findAll());
            modelAndView.setViewName("user/exam/addRating");
        } else {
            try {
                examService.rateExam(exam,rateExamDTO.getExamRatingsDTO(),true);
                redirectAttributes.addAttribute("successMessageBox", "Exam ratings finalized.");
                modelAndView.setViewName("redirect:/user/exam/" + exam.getId());
            }catch (Exception e){
                modelAndView.addObject("marks",examRatingMarkService.findAll());
                modelAndView.addObject("errorMessageBox", "Error: " + e.getMessage());
                modelAndView.setViewName("user/exam/addRating");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value= "/finalize/{id}", method = RequestMethod.GET)
    public ModelAndView finalizeExam(@PathVariable("id") long id,
                                     @RequestParam(value = "committeesize", required = true, defaultValue = "5") int committesize,
                                     Authentication authentication,
                                     RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if(committesize < 2){
            committesize = 2;
            modelAndView.addObject("errorMessageBox", "Committee size must be at least 2");
        }
        if(committesize > 5){
            committesize = 5;
            modelAndView.addObject("errorMessageBox", "Committee size must be at most 5");
        }
        modelAndView.addObject("committeesize",committesize);
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam != null) {
            FinalizeExamDTO finalizeExamDTO = new FinalizeExamDTO();
            finalizeExamDTO.init(exam);
            int listsize = finalizeExamDTO.getCommitteeMembersDTO().size();
            if(finalizeExamDTO.getCommitteeMembersDTO().isEmpty()){
                for (int i=0; i<committesize; i++){
                    finalizeExamDTO.getCommitteeMembersDTO().add(new CommitteeMemberDTO());
                }
            }else if(listsize > committesize){
                for( int i=0; i<(listsize - committesize); i++){
                    finalizeExamDTO.getCommitteeMembersDTO().remove(finalizeExamDTO.getCommitteeMembersDTO().size()-1);
                }
            }else if(listsize < committesize){

                for( int i=0; i<(committesize - listsize); i++){
                    finalizeExamDTO.getCommitteeMembersDTO().add(new CommitteeMemberDTO());
                }
            }
            modelAndView.addObject("exam", finalizeExamDTO);
            modelAndView.setViewName("user/exam/finalizeExam");
            return modelAndView;
        }else{
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
    }

    @RequestMapping(value= "/finalize/{id}", method = RequestMethod.POST, params="action=tempsave")
    public ModelAndView saveTemporaryExam(@PathVariable("id") long id,
                                          @RequestParam(value = "committeesize", required = true, defaultValue = "5") int committesize,
                                          @Valid @ModelAttribute("exam") FinalizeExamDTO finalizeExamDTO,
                                          BindingResult bindingResult,
                                          Authentication authentication,
                                          RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam == null){
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
        Iterator<CommitteeMemberDTO> committeeMemberDTOIterator = finalizeExamDTO.getCommitteeMembersDTO().iterator();
        int index = 0;
        while (committeeMemberDTOIterator.hasNext()) {
            CommitteeMemberDTO committeeMemberDTO =committeeMemberDTOIterator.next();
            if(committeeMemberDTO.getName().equals("")){
                bindingResult.rejectValue("committeeMembersDTO[" + index + "].name", "error.exam", "The name cannot be empty");
            }
            if(committeeMemberDTO.getLastName().equals("")){
                bindingResult.rejectValue("committeeMembersDTO[" + index + "].lastName", "error.exam", "The lastname cannot be empty");
            }
            if(committeeMemberDTO.getCommitteeRole() == null){
                bindingResult.rejectValue("committeeMembersDTO[" + index + "].committeeRole", "error.exam", "The role cannot be empty");
            }
            index++;
        }
        if (bindingResult.hasErrors()) {
            if(committesize < 2){
                committesize = 2;
                modelAndView.addObject("errorMessageBox", "Committee size must be at least 2");
            }
            if(committesize > 5){
                committesize = 5;
                modelAndView.addObject("errorMessageBox", "Committee size must be at most 5");
            }
            modelAndView.addObject("committeesize",committesize);
            modelAndView.setViewName("user/exam/finalizeExam");
        } else {
            try {
                exam.setExamGeneralNotes(finalizeExamDTO.getExamGeneralNotes());
                examService.finalizeExam(exam,finalizeExamDTO.getCommitteeMembersDTO(),false);
                redirectAttributes.addAttribute("successMessageBox", "Exam committee members and notes are saved temporally, please finalized.");
                modelAndView.setViewName("redirect:/user/exam/" + exam.getId());
            }catch (Exception e){
                if(committesize < 2){
                    committesize = 2;
                    modelAndView.addObject("errorMessageBox", "Committee size must be at least 2");
                }
                if(committesize > 5){
                    committesize = 5;
                    modelAndView.addObject("errorMessageBox", "Committee size must be at most 5");
                }
                modelAndView.addObject("committeesize",committesize);
                modelAndView.addObject("errorMessageBox", "Error: " + e.getMessage());
                modelAndView.setViewName("user/exam/finalizeExam");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value= "/finalize/{id}", method = RequestMethod.POST, params="action=save")
    public ModelAndView saveFinalExam(@PathVariable("id") long id,
                                      @RequestParam(value = "committeesize", required = true, defaultValue = "5") int committesize,
                                      @Valid @ModelAttribute("exam") FinalizeExamDTO finalizeExamDTO,
                                      BindingResult bindingResult,
                                      Authentication authentication,
                                      RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examService.getOneByUser(id, authentication.getName());
        if(exam == null){
            redirectAttributes.addAttribute("errorMessageBox", "Can not access exam with id " + id);
            modelAndView.setViewName("redirect:/user/exam/");
            return modelAndView;
        }
        Iterator<CommitteeMemberDTO> committeeMemberDTOIterator = finalizeExamDTO.getCommitteeMembersDTO().iterator();
        int index = 0;
        while (committeeMemberDTOIterator.hasNext()) {
            CommitteeMemberDTO committeeMemberDTO =committeeMemberDTOIterator.next();
            if(committeeMemberDTO.getName().equals("")){
                bindingResult.rejectValue("committeeMembersDTO[" + index + "].name", "error.exam", "The name cannot be empty");
            }
            if(committeeMemberDTO.getLastName().equals("")){
                bindingResult.rejectValue("committeeMembersDTO[" + index + "].lastName", "error.exam", "The lastname cannot be empty");
            }
            if(committeeMemberDTO.getCommitteeRole() == null){
                bindingResult.rejectValue("committeeMembersDTO[" + index + "].committeeRole", "error.exam", "The role cannot be empty");
            }
            index++;
        }
        if (bindingResult.hasErrors()) {
            if(committesize < 2){
                committesize = 2;
                modelAndView.addObject("errorMessageBox", "Committee size must be at least 2");
            }
            if(committesize > 5){
                committesize = 5;
                modelAndView.addObject("errorMessageBox", "Committee size must be at most 5");
            }
            modelAndView.addObject("committeesize",committesize);
            modelAndView.setViewName("user/exam/finalizeExam");
        } else {
            try {
                exam.setExamGeneralNotes(finalizeExamDTO.getExamGeneralNotes());
                examService.finalizeExam(exam,finalizeExamDTO.getCommitteeMembersDTO(),true);
                redirectAttributes.addAttribute("successMessageBox", "Exam finalized successfully.");
                modelAndView.setViewName("redirect:/user/exam/" + exam.getId());
            }catch (Exception e){
                if(committesize < 2){
                    committesize = 2;
                    modelAndView.addObject("errorMessageBox", "Committee size must be at least 2");
                }
                if(committesize > 5){
                    committesize = 5;
                    modelAndView.addObject("errorMessageBox", "Committee size must be at most 5");
                }
                modelAndView.addObject("committeesize",committesize);
                modelAndView.addObject("errorMessageBox", "Error: " + e.getMessage());
                modelAndView.setViewName("user/exam/finalizeExam");
            }
        }
        return modelAndView;
    }
}
