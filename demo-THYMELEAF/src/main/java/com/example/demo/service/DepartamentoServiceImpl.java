package com.example.demo.service;

import com.example.demo.dao.DepartamentoDao;
import com.example.demo.domain.Departamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 1. Mudei o pacote do Transactional

import java.util.List;

@Service
@Transactional(readOnly = false) // 2. Defini o padrão como escrita
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoDao dao;

    @Override
    public void salvar(Departamento dep) {
        dao.save(dep);
    }

    @Override
    public void editar(Departamento dep) {
        dao.update(dep);
    }

    @Override
    public void excluir(Long id) {
        dao.delete(id);
    }

    @Override
    @Transactional(readOnly = true) // 3. Otimização para consulta
    public Departamento buscarPorId(Long id) {
        return dao.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Departamento> buscarTodos() {
        return dao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean departamentoTemCargos(Long id) {
        Departamento dep = buscarPorId(id);

        // Se o departamento nem existe, ele obviamente não tem cargos
        if (dep == null) {
            return false;
        }

        // Se existe, verifica se a lista de cargos está vazia
        return !dep.getCargos().isEmpty();
    }

}