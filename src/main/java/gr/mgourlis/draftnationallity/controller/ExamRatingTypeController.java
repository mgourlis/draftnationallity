package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.dto.ExamRatingTypeDTO;
import gr.mgourlis.draftnationallity.model.ExamRatingType;
import gr.mgourlis.draftnationallity.service.IExamRatingTypeService;
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
@RequestMapping("/admin/exam-rating-type")
public class ExamRatingTypeController {


    @Autowired
    IExamRatingTypeService examRatingTypeService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView getExamRatingTypes(ModelAndView modelAndView, Pageable pageable){
        Page<ExamRatingType> examRatingTypePage = examRatingTypeService.findAll(pageable);
        modelAndView.addObject("examRatingTypes", examRatingTypePage.getContent());
        modelAndView.addObject("page",examRatingTypePage);
        modelAndView.setViewName("admin/exam-rating-type/showExamRatingTypes");
        return modelAndView;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ModelAndView showExamRatingType(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingType examRatingType = examRatingTypeService.getOne(id);
        if(examRatingType != null){
            modelAndView.addObject("examRatingTypes", examRatingType);
            modelAndView.setViewName("admin/exam-rating-type/showExamRatingType");
            return modelAndView;
        }else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editExamRatingType(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingType examRatingType = examRatingTypeService.getOne(id);
        if(examRatingType == null){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        ExamRatingTypeDTO examRatingTypeDTO = new ExamRatingTypeDTO();
        examRatingTypeDTO.init(examRatingType);
        modelAndView.addObject("examRatingType", examRatingTypeDTO);
        modelAndView.setViewName("/admin/exam-rating-type/editExamRatingType");
        return modelAndView;
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editExamRatingType(@PathVariable("id") long id, @Valid @ModelAttribute("examRatingType") ExamRatingTypeDTO examRatingTypeDTO, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingType editExamRatingType = examRatingTypeService.getOne(id);
        if(editExamRatingType == null){
            throw new EntityNotFoundException();
        }

        //TODO - check if used

        else if (bindingResult.hasErrors()){
            modelAndView.setViewName("/admin/exam-rating-type/editExamRatingType");
        }
        else{
            editExamRatingType.setRatingType(examRatingTypeDTO.getRatingType());
            editExamRatingType.setLanguageType(examRatingTypeDTO.isLanguageType());
            examRatingTypeService.save(editExamRatingType);
            modelAndView.setViewName("redirect:/admin/exam-rating-type/" + editExamRatingType.getId());
        }
        return modelAndView;
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public ModelAndView newExamRatingType(){
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingTypeDTO examRatingTypeDTO = new ExamRatingTypeDTO();
        modelAndView.addObject("examRatingType", examRatingTypeDTO);
        modelAndView.setViewName("admin/exam-rating-type/newExamRatingType");
        return modelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView createNewExamRatingType(@Valid @ModelAttribute("examRatingType") ExamRatingTypeDTO examRatingTypeDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingType examRatingTypeExists = examRatingTypeService.findByRatingType(examRatingTypeDTO.getRatingType());
        if (examRatingTypeExists != null) {
            bindingResult
                    .rejectValue("ratingType", "error.examRatingType",
                            "There is already an Exam Rating Type with the name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/exam-rating-type/newExamRatingType");
        } else {
            ExamRatingType examRatingType = new ExamRatingType();
            examRatingType.setRatingType(examRatingTypeDTO.getRatingType());
            examRatingType.setLanguageType(examRatingTypeDTO.isLanguageType());
            examRatingTypeService.save(examRatingType);
            modelAndView.addObject("successMessageBox", "Exam Rating Type has been created successfully");
            modelAndView.addObject("examRatingType", new ExamRatingTypeDTO());
            modelAndView.setViewName("admin/exam-rating-type/newExamRatingType");

        }
        return modelAndView;
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteExamRatingType(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingType examRatingType = examRatingTypeService.getOne(id);
        if (examRatingType == null){
            throw new EntityNotFoundException("Could not find Exam Rating Type");
        }
        examRatingTypeService.delete(id);
        modelAndView.setViewName("redirect:/admin/exam-rating-type/");
        return modelAndView;
    }
    
}
