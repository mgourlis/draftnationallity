package gr.mgourlis.draftnationallity.controller;

import gr.mgourlis.draftnationallity.dto.ExamRatingMarkDTO;
import gr.mgourlis.draftnationallity.model.ExamRatingMark;
import gr.mgourlis.draftnationallity.service.IExamRatingMarkService;
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
@RequestMapping("/admin/exam-rating-mark")
public class ExamRatingMarkController {

    @Autowired
    IExamRatingMarkService examRatingMarkService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView getExamRatingMarks(ModelAndView modelAndView, Pageable pageable){
        Page<ExamRatingMark> examRatingMarkPage = examRatingMarkService.findAll(pageable);
        modelAndView.addObject("examRatingMarks", examRatingMarkPage.getContent());
        modelAndView.addObject("page",examRatingMarkPage);
        modelAndView.setViewName("admin/exam-rating-mark/showExamRatingMarks");
        return modelAndView;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ModelAndView showExamRatingMark(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingMark examRatingMark = examRatingMarkService.getOne(id);
        if(examRatingMark != null){
            modelAndView.addObject("examRatingMarks", examRatingMark);
            modelAndView.setViewName("admin/exam-rating-mark/showExamRatingMark");
            return modelAndView;
        }else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editExamRatingMark(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingMark examRatingMark = examRatingMarkService.getOne(id);
        if(examRatingMark == null){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        ExamRatingMarkDTO examRatingMarkDTO = new ExamRatingMarkDTO();
        examRatingMarkDTO.init(examRatingMark);
        modelAndView.addObject("examRatingMark", examRatingMarkDTO);
        modelAndView.setViewName("/admin/exam-rating-mark/editExamRatingMark");
        return modelAndView;
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editExamRatingMark(@PathVariable("id") long id, @Valid @ModelAttribute("examRatingMark") ExamRatingMarkDTO examRatingMarkDTO, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingMark editExamRatingMark = examRatingMarkService.getOne(id);
        if(editExamRatingMark == null){
            throw new EntityNotFoundException();
        }

        //TODO - check if used

        else if (bindingResult.hasErrors()){
            modelAndView.setViewName("/admin/exam-rating-mark/editExamRatingMark");
        }
        else{
            editExamRatingMark.setRatingMark(examRatingMarkDTO.getRatingMark());
            examRatingMarkService.save(editExamRatingMark);
            modelAndView.setViewName("redirect:/admin/exam-rating-mark/" + editExamRatingMark.getId());
        }
        return modelAndView;
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public ModelAndView newExamRatingMark(){
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingMarkDTO examRatingMarkDTO = new ExamRatingMarkDTO();
        modelAndView.addObject("examRatingMark", examRatingMarkDTO);
        modelAndView.setViewName("admin/exam-rating-mark/newExamRatingMark");
        return modelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView createNewExamRatingMark(@Valid @ModelAttribute("examRatingMark") ExamRatingMarkDTO examRatingMarkDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingMark examRatingMarkExists = examRatingMarkService.findByRatingMark(examRatingMarkDTO.getRatingMark());
        if (examRatingMarkExists != null) {
            bindingResult
                    .rejectValue("ratingMark", "error.examRatingMark",
                            "There is already an Exam Rating Mark with the name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/exam-rating-mark/newExamRatingMark");
        } else {
            ExamRatingMark examRatingMark = new ExamRatingMark();
            examRatingMark.setRatingMark(examRatingMarkDTO.getRatingMark());
            examRatingMarkService.save(examRatingMark);
            modelAndView.addObject("successMessageBox", "Exam Rating Mark has been created successfully");
            modelAndView.addObject("examRatingMark", new ExamRatingMarkDTO());
            modelAndView.setViewName("admin/exam-rating-mark/newExamRatingMark");

        }
        return modelAndView;
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteExamRatingMark(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        ExamRatingMark examRatingMark = examRatingMarkService.getOne(id);
        if (examRatingMark == null){
            throw new EntityNotFoundException("Could not find Exam Rating Mark");
        }
        examRatingMarkService.delete(id);
        modelAndView.setViewName("redirect:/admin/exam-rating-mark/");
        return modelAndView;
    }
}
