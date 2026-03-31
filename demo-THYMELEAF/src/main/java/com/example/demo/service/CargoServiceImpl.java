package com.example.demo.service;

import com.example.demo.dao.CargoDao;
import com.example.demo.domain.Cargo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service @Transactional

public class CargoServiceImpl implements CargoService {

    @Autowired
    private CargoDao dao;

    @Override
    public void salvar(Cargo cargo) {
           dao.save(cargo);
    }

    @Override
    public void editar(Cargo cargo) {
        dao.update(cargo);
    }

    @Override
    public void excluir(Long id) {
       dao.delete(id);
    }

    @Override
    public Cargo buscarPorId(Long id) {
        return dao.findById(id);
    }

    @Override
    public List<Cargo> buscarTodos() {
        return dao.findAll();
    }

    @Override
    public boolean cargoTemFuncionario(Long id) {
        Cargo cargo = buscarPorId(id);

        // Se o departamento nem existe, ele obviamente não tem cargos
        if (cargo == null) {
            return false;
        }

        // Se existe, verifica se a lista de cargos está vazia
        return !cargo.getFuncionarios().isEmpty();
    }

}
