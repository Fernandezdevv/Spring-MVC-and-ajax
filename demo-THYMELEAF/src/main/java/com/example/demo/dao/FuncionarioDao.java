package com.example.demo.dao;

import com.example.demo.domain.Cargo;
import com.example.demo.domain.Departamento;
import com.example.demo.domain.Funcionario;

import java.time.LocalDate;
import java.util.List;

public interface FuncionarioDao {

    void save (Funcionario funcionario);

    void update(Funcionario funcionario);

    void delete(Long id);

    Funcionario findById(Long id);

    List<Funcionario> findAll();
    List<Funcionario> findByNome(String nome);

    List<Funcionario> findByCargo(Long id);

    List<Funcionario> findByDataEntradaDataSaida(LocalDate entrada, LocalDate saida);

    List<Funcionario> findByDataEntrada(LocalDate entrada);

    List<Funcionario> findByDataSaida(LocalDate saida);
}
