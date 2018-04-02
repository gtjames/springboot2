package edu.edgetech.sb2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    String index(){
        System.out.println( "We'll always have Paris" );
        return "index";
    }
}
