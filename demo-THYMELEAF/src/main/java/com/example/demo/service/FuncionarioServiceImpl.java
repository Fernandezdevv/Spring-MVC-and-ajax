package com.example.demo.service;

import com.example.demo.dao.FuncionarioDao;
import com.example.demo.domain.Funcionario;
// IMPORT CORRETO ABAIXO
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true) // Agora o Spring reconhece o readOnly
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    private FuncionarioDao dao;

    @Transactional(readOnly = false)
    @Override
    public void salvar(Funcionario func) {
        dao.save(func);
    }

    @Transactional(readOnly = false)
    @Override
    public void editar(Funcionario func) {
        dao.update(func);
    }

    @Transactional(readOnly = false)
    @Override
    public void excluir(Long funcId) {
        dao.delete(funcId);
    }

    @Override
    public Funcionario buscarPorId(Long id) {
        return dao.findById(id);
    }

    @Override
    public List<Funcionario> buscarTodos() {
        return dao.findAll();
    }

    @Override
    public List<Funcionario> buscarPorNome(String nome) {
        return dao.findByNome(nome);
    }

    @Override
    public List<Funcionario> buscarPorCargo(Long id) {
        return dao.findByCargo(id);
    }

    @Override
    public List<Funcionario> buscarPorDatas(LocalDate entrada, LocalDate saida) {
        if (entrada != null && saida != null){
            return dao.findByDataEntradaDataSaida(entrada, saida);
        } else if(entrada != null){
           return dao.findByDataEntrada(entrada);
        } else if(saida != null){
            return dao.findByDataSaida(saida);
        } else{
            return new ArrayList<>();
        }

    }

}