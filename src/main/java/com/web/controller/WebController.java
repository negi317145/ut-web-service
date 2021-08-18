package com.web.controller;


import com.feign.dto.MarkDTO;
import com.feign.dto.StudentDTO;
import com.feign.service.MarkService;
import com.feign.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;


@Controller
public class WebController {

    Logger logger = LoggerFactory.getLogger(WebController.class);

    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    private MarkService markService;

    @Autowired
    public void setMarkService(MarkService markService) {
        this.markService = markService;
    }

    @RequestMapping("/")
    public String registerForm(){
        return "register";
    }


    @RequestMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/register-user")
    public String userRegister(StudentDTO studentDTO, Model model, HttpSession session){

        try {
            studentDTO.setRole("ROLE_USER");
            studentService.registerUser(studentDTO);

            session.setAttribute("message","Registered Successfully");
            //System.out.println("web page student"+studentDTO.getRole());
            return "/login";

        }

        catch(Exception e){
            e.printStackTrace();
        }

        return "register";
    }


    @GetMapping("/student/data")
    public String studentData(Model model, Principal principal){

        //System.out.println(principal.getName());
        model.addAttribute("data",studentService.getStudentByUsername(principal.getName()));

        return "student";
    }

    @GetMapping("/get-form")
    public String getMarkTable(){

        return "addmark";
    }

    @PostMapping("/add-mark")
    public String addMark(MarkDTO markDTO){

        try {
            markService.addMark(markDTO);

            return "login";
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return "register";
    }



    @GetMapping("/get-marks/{studentId}")
    public String getMarks(@PathVariable(name = "studentId") int studentId,Model model){

        logger.info("----------"+studentId);
        System.out.print(studentId);
        List<MarkDTO> markDTO = markService.getMark(studentId);


        model.addAttribute("mark",markDTO);

        return "marks";
    }



}
