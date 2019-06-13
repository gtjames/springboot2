package edu.edgetech.sb2.controllers;

import edu.edgetech.sb2.models.User;
import edu.edgetech.sb2.services.NotificationService;
import edu.edgetech.sb2.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AuthenticationController {

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("email", "");
        modelAndView.addObject("password", "");
        modelAndView.setViewName("login"); // resources/template/login.html
        return modelAndView;
    }

//    @RequestMapping(value = { "/x/gtj" })
//    public String name(Model model) {
//        model.addAttribute("email", "gtjames@gmail.com");
//        model.addAttribute("password", "password");
//        return "login";
//    }
//
//    @RequestMapping(value = { "/x/super" })
//    public String superUser(Model model) {
//        model.addAttribute("email", "admin@gmail.com");
//        model.addAttribute("password", "abc123ABC");
//        return "login";
//    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";				// resources/template/register.html
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin"); // resources/template/admin.html
        return modelAndView;
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validations
        if(bindingResult.hasErrors()) {
            modelAndView.addObject("successMessage", "Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
        }
        else if(userService.isUserAlreadyPresent(user)){
            modelAndView.addObject("successMessage", "user already exists!");
        }
        // we will save the user if, no binding errors
        else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User is registered successfully!");
        }

        //	------------	this is where wer are going to send an email
        try {
            notificationService.sendNotification(user);
        } catch (MailException ex) {
            logger.info("Error Send email " + ex.getMessage());
        }

        modelAndView.addObject("user", new User());
        modelAndView.addObject("email", user.getEmail());
        modelAndView.addObject("password", "");
        modelAndView.setViewName("contact");
        return modelAndView;
    }
}
