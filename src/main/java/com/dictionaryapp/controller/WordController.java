package com.dictionaryapp.controller;

import com.dictionaryapp.model.dto.AddWordDTO;
import com.dictionaryapp.model.entity.Word;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WordController {

    @GetMapping("/word-add")
    public String viewWordAdd() {

        return "word-add";
    }


    @PostMapping("/word-add")
    public String doAddWord(
            @Valid AddWordDTO addWordDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
            ) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addWordDTO", addWordDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addWordDTO", bindingResult);

            return "redirect:/word-add";
        }

        return "redirect:/home";
    }
}
