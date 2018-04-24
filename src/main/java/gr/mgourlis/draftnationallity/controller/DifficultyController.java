package gr.mgourlis.draftnationallity.controller;


import gr.mgourlis.draftnationallity.model.Difficulty;
import gr.mgourlis.draftnationallity.service.IDifficultyService;
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
@RequestMapping("/admin/difficulty")
public class DifficultyController {

    @Autowired
    IDifficultyService difficultyService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView getDifficulties(ModelAndView modelAndView, Pageable pageable){
        Page<Difficulty> difficultiesPage = difficultyService.findAll(pageable);
        modelAndView.addObject("difficulties", difficultiesPage.getContent());
        modelAndView.addObject("page",difficultiesPage);
        modelAndView.setViewName("admin/difficulty/showDifficulties");
        return modelAndView;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ModelAndView showDifficulty(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        Difficulty difficulty = difficultyService.findDifficultyById(id);
        try {
            modelAndView.addObject("difficulties", difficulty);
            modelAndView.setViewName("admin/difficulty/showDifficulty");
            return modelAndView;
        }catch (EntityNotFoundException e){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editDifficulty(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();
        Difficulty difficulty = difficultyService.findDifficultyById(id);
        if(difficulty == null){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
        modelAndView.addObject("difficulty", difficulty);
        modelAndView.setViewName("/admin/difficulty/editDifficulty");
        return modelAndView;
    }

    @RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editDifficulty(@PathVariable("id") long id, @Valid @ModelAttribute("difficulty") Difficulty difficulty, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        Difficulty editdifficulty = difficultyService.findDifficultyById(id);
        if(editdifficulty == null){
            throw new EntityNotFoundException();
        }
        else if (bindingResult.hasErrors()){
            modelAndView.setViewName("/admin/difficulty/editDifficulty");
        }
        else{
            editdifficulty.setLevel(difficulty.getLevel());
            editdifficulty.setLevelNumber(difficulty.getLevelNumber());
            difficultyService.save(editdifficulty);
            modelAndView.setViewName("redirect:/admin/difficulty/" + editdifficulty.getId());
        }
        return modelAndView;
    }

    @RequestMapping(value="/new", method = RequestMethod.GET)
    public ModelAndView newDifficulty(){
        ModelAndView modelAndView = new ModelAndView();
        Difficulty difficulty = new Difficulty();
        modelAndView.addObject("difficulty", difficulty);
        modelAndView.setViewName("admin/difficulty/newDifficulty");
        return modelAndView;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid @ModelAttribute("difficulty") Difficulty difficulty, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Difficulty difficultyExists = difficultyService.findByLevelNumber(difficulty.getLevelNumber());
        if (difficultyExists != null) {
            bindingResult
                    .rejectValue("levelNumber", "error.difficulty",
                            "There is already a difficulty with the level number provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/difficulty/newDifficulty");
        } else {
            difficultyService.save(difficulty);
            modelAndView.addObject("successMessageBox", "Difficulty has been created successfully");
            modelAndView.addObject("difficulty", new Difficulty());
            modelAndView.setViewName("admin/difficulty/newDifficulty");

        }
        return modelAndView;
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteUser(@PathVariable("id") long id){
        ModelAndView modelAndView = new ModelAndView();

        //TODO - Check if difficulty exists in question or difficulty setting

        difficultyService.delete(id);
        modelAndView.setViewName("redirect:/admin/difficulty/");
        return modelAndView;
    }
}
