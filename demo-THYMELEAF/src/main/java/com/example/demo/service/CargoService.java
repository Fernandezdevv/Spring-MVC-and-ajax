package com.example.demo.service;

import com.example.demo.domain.Cargo;
import com.example.demo.domain.Departamento;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CargoService {

    void salvar(Cargo cargo);
    void editar (Cargo cargo);
    void excluir(Long id);
    Cargo buscarPorId(Long id);
    @Transactional(readOnly = true)
    List<Cargo> buscarTodos();

    boolean cargoTemFuncionario(Long id);
}
