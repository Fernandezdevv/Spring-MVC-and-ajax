package com.example.demo.web.controller;


import com.example.demo.domain.Cargo;
import com.example.demo.domain.Departamento;
import com.example.demo.service.CargoService;
import com.example.demo.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService service;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping("/cadastrar")
    public String cadastrar(@ModelAttribute("departamento") Departamento departamento) {
        return "departamento/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model){
        model.addAttribute("departamentos", service.buscarTodos());
        return "departamento/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr){

        if(result.hasErrors()){
            return "/departamento/cadastro";
        }
        service.salvar(departamento);
        attr.addFlashAttribute("success", "Departamento inserido com sucesso!");
        return "redirect:/departamentos/cadastrar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model){
        model.addAttribute("departamento", service.buscarPorId(id));
        return "departamento/cadastro";
    }

    @GetMapping("/editar")
    public String editar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr){
        if (result.hasErrors()){
            return "/departamento/cadastro";
        }

        departamentoService.editar(departamento);
        attr.addFlashAttribute("succes", "Registro atualizado com sucesso!");
        return "redirect:/departamento/cadastro";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        if (service.departamentoTemCargos(id)) {
            attr.addFlashAttribute("fail", "Departamento não removido. Possui cargo(s) vinculado(s).");
        } else {
            service.excluir(id);
            attr.addFlashAttribute("success", "Departamento excluído com sucesso!");
        }
        return "redirect:/departamentos/listar";
    }
}