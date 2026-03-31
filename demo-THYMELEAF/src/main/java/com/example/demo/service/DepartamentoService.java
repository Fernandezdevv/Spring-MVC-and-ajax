package com.example.demo.service;

import com.example.demo.domain.Cargo;
import com.example.demo.domain.Departamento;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartamentoService {

    void salvar(Departamento dep);
    void editar (Departamento dep);
    void excluir(Long id);
    Departamento buscarPorId(Long id);

    @Transactional(readOnly = true)
    List<Departamento> buscarTodos();

    boolean departamentoTemCargos(Long id);
}
