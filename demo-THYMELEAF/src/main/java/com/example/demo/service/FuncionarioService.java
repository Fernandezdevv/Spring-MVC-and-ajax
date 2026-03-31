package com.example.demo.service;

import com.example.demo.domain.Funcionario;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface FuncionarioService {

    void salvar (Funcionario func);
    void editar(Funcionario func);
    void excluir(Long func);
    Funcionario buscarPorId(Long id);
    @Transactional(readOnly = true)
    List<Funcionario> buscarTodos();


    List<Funcionario> buscarPorNome(String nome);

    List<Funcionario>buscarPorCargo(Long id);

    List<Funcionario>buscarPorDatas(LocalDate entrada, LocalDate saida);
}
