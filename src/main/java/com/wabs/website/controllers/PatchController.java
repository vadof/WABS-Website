package com.wabs.website.controllers;

import com.wabs.website.models.Patch;
import com.wabs.website.repository.PatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class PatchController {

    @Autowired
    private PatchRepository patchRepository;

    @GetMapping("/patch/{id}")
    public String patchView(@PathVariable Long id, Model model) {
        Optional<Patch> optionalPatch = patchRepository.findById(id);

        if (optionalPatch.isPresent()) {
            Patch patch = optionalPatch.get();
            String patchDate = patch.getReleaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            model.addAttribute("title", "Patch " + patch.getPatch());
            model.addAttribute("patch", patch);
            model.addAttribute("patch_date", patchDate);

            return "patch-view";
        }

        return "redirect:/";
    }

    @GetMapping("/patches")
    public String allPatches(Model model) {
        List<Patch> patches = patchRepository.findAll();
        Collections.reverse(patches);

        model.addAttribute("patches", patches);

        return "patches";
    }

}
