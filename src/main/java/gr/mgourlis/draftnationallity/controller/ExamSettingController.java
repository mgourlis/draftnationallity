package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.model.DifficultySetting;
import gr.mgourlis.draftnationallity.model.ExamSetting;
import gr.mgourlis.draftnationallity.service.IExamSettingService;
import gr.mgourlis.draftnationallity.service.IQuestionCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@Secured("ADMIN")
@RequestMapping("/admin/exam-setting")
public class ExamSettingController {

    @Autowired
    IExamSettingService examSettingService;

    @Autowired
    IQuestionCategoryService questionCategoryService;

    @RequestMapping("/")
    public ModelAndView getExamSettings(ModelAndView modelAndView, Pageable pageable){
        Page<ExamSetting> examSettingPage = examSettingService.findAll(pageable);
        modelAndView.addObject("examSettings", examSettingPage.getContent());
        modelAndView.addObject("page",examSettingPage);
        modelAndView.setViewName("admin/examsetting/showExamSettings");
        return modelAndView;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ModelAndView showExamSetting(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        ExamSetting examSetting = examSettingService.getOne(id);
        try {
            modelAndView.addObject("examSetting", examSetting);
            modelAndView.setViewName("admin/examsetting/showExamSetting");
            return modelAndView;
        }catch (EntityNotFoundException e){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editExamSetting(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        ExamSetting examSetting = examSettingService.getOne(id);
        if(examSetting == null){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        modelAndView.addObject("questionCategories",questionCategoryService.findAll());
        modelAndView.addObject("examSetting", examSetting);
        modelAndView.setViewName("/admin/examsetting/editExamSetting");
        return modelAndView;
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editExamSetting(@PathVariable("id") long id, @Valid @ModelAttribute("examSetting") ExamSetting examSetting, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        ExamSetting editExamSetting = examSettingService.getOne(id);
        if(editExamSetting == null){
            throw new EntityNotFoundException();
        }
        ExamSetting checkName = examSettingService.findExamSettingByName(editExamSetting.getName());
        if(checkName != null){
            if(checkName.getId() != editExamSetting.getId()) {
                bindingResult
                        .rejectValue("name", "error.examSetting",
                                "There is already an Exam Setting with the name provided");
            }
        }
        float difficultySum = 0;
        for (DifficultySetting difficultySetting : examSetting.getDifficultySettings()) {
            if(difficultySetting.getPercentage() < 0 || 100 > difficultySetting.getPercentage()){
                bindingResult
                        .rejectValue("difficultySetting" + difficultySetting.getId(), "error.examSetting",
                                "The percentage must be between 0.0% - 100.0% ");
            }
            difficultySum += difficultySetting.getPercentage();
        }
        if(examSetting.getQuestionCategories().isEmpty()){
            bindingResult
                    .rejectValue("questionCategories", "error.examSetting",
                            "Select at least one Question Category");
        }
        boolean checkFlag = false;
        if(difficultySum > 100 || 99 > difficultySum){
            modelAndView.addObject("errorMessageBox","The sum of the percentage must be between 99.0% - 100.0%");
            checkFlag = true;
        }
        if (bindingResult.hasErrors() || checkFlag){
            modelAndView.addObject("questionCategories",questionCategoryService.findAll());
            modelAndView.setViewName("/admin/examsetting/editExamSetting");
        }
        else{
            editExamSetting.setName(examSetting.getName());
            editExamSetting.setEnabled(examSetting.getEnabled());
            editExamSetting.setNumOfQuestions(examSetting.getNumOfQuestions());
            editExamSetting.setQuestionCategories(examSetting.getQuestionCategories());
            editExamSetting.setDifficultySettings(examSetting.getDifficultySettings());
            examSettingService.save(editExamSetting);
            modelAndView.setViewName("redirect:/admin/exam-setting/" + editExamSetting.getId());
        }
        return modelAndView;
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public ModelAndView newExamSetting(){
        ModelAndView modelAndView = new ModelAndView();
        ExamSetting examSetting = examSettingService.createExamSetting();
        modelAndView.addObject("questionCategories",questionCategoryService.findAll());
        modelAndView.addObject("examSetting", examSetting);
        modelAndView.setViewName("admin/examsetting/newExamSetting");
        return modelAndView;
    }

    @RequestMapping(value="/new", method = RequestMethod.POST)
    public ModelAndView newExamSetting(@Valid @ModelAttribute("examSetting") ExamSetting examSetting, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        ExamSetting checkName = examSettingService.findExamSettingByName(examSetting.getName());
        if(checkName != null){
                bindingResult
                        .rejectValue("name", "error.examSetting",
                                "There is already an Exam Setting with the name provided");
        }
        float difficultySum = 0;
        for (DifficultySetting difficultySetting : examSetting.getDifficultySettings()) {
            if(difficultySetting.getPercentage() < 0 || 100 > difficultySetting.getPercentage()){
                bindingResult
                        .rejectValue("difficultySetting" + difficultySetting.getId(), "error.examSetting",
                                "The percentage must be between 0.0% - 100.0% ");
            }
            difficultySum += difficultySetting.getPercentage();
        }
        if(examSetting.getQuestionCategories().isEmpty()){
            bindingResult
                    .rejectValue("questionCategories", "error.examSetting",
                            "Select at least one Question Category");
        }
        boolean checkFlag = false;
        if(difficultySum > 100 || 99 > difficultySum){
            modelAndView.addObject("errorMessageBox","The sum of the percentage must be between 99.0% - 100.0%");
            checkFlag = true;
        }
        if (bindingResult.hasErrors() || checkFlag){
            modelAndView.addObject("questionCategories",questionCategoryService.findAll());
            modelAndView.setViewName("/admin/examsetting/newExamSetting");
        }
        else{
            examSettingService.save(examSetting);
            modelAndView.setViewName("/admin/examsetting/newExamSetting");
        }
        return modelAndView;
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteQuestion(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        examSettingService.delete(id);
        modelAndView.setViewName("redirect:/admin/exam-setting/");
        return modelAndView;
    }

}
