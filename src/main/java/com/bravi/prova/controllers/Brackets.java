package com.bravi.prova.controllers;

import com.bravi.prova.utils.brackets.BracketsValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Brackets {

    @GetMapping("/brackets")
    public String brackets() {
        return "brackets/bracketshome";
    }

    @PostMapping("/brackets")
    public String bracketsForm(ModelMap model, @RequestParam("brackets") String brackets){
        boolean isBalanced = new BracketsValidation().isBalanced(brackets);

        String result = (isBalanced) ? "É válido" : "Não é valido";
        model.addAttribute("sequencia", brackets);
        model.addAttribute("result", result);
        return "brackets/bracketshome";
    }
}
