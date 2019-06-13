package edu.edgetech.sb2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    String index(){
        System.out.println( "Index:   We'll always have Paris" );
        return "index";
    }
    @RequestMapping("/contact")
    String contact(){
        System.out.println( "Contact: Here's lookin' at you kid" );
        return "contact";
    }
}
