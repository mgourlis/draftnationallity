package gr.mgourlis.draftnationallity.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured("USER")
@RequestMapping("/user/exam")
public class UserExamController {



}
