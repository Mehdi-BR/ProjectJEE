package com.emsi.patientsmvc.web;

import com.emsi.patientsmvc.entities.Patient;
import com.emsi.patientsmvc.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository PR;
    @GetMapping(path="/index")
    public String patients(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String keyword){

        Page<Patient> PagePatients=PR.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("ListPatients",PagePatients.getContent());
        model.addAttribute("pages",new int[PagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);


        return "patients";
    }
    @GetMapping("/delete")
    public String delete(long id,String keyword,int page){
        PR.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;

    }
}
