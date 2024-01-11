package com.example.demo.controllers;

import com.example.demo.CargoService;
import com.example.demo.models.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private CargoService service;

    @RequestMapping("/cargo_home")
    public String viewHomePage(Model model, @Param("keyword") String keyword){
        List<Cargo> listCargo = service.listAll(keyword);
        model.addAttribute("listCargo", listCargo);
        model.addAttribute("keyword", keyword);
        return "index";

    }

    @RequestMapping("/new")
    public String showNewCargoForm(Model model){
        Cargo cargo = new Cargo();
        model.addAttribute("cargo", cargo);
        return "new_cargo";

    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveCargo(@ModelAttribute("cargo") Cargo cargo){
        service.save(cargo);
        return "redirect:/cargo_home";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditCargoForm(@PathVariable(name="id") Long id){
        ModelAndView mav = new ModelAndView("edit_cargo");
        Cargo cargo = service.get(id);
        mav.addObject("cargo", cargo);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteCargo(@ModelAttribute(name = "id") Long id){
        service.delete(id);
        return "redirect:/cargo_home";
    }


}