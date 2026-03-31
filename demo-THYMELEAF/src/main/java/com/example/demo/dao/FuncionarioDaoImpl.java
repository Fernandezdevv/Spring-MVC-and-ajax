package com.example.demo.dao;

import com.example.demo.domain.Funcionario;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class FuncionarioDaoImpl  extends AbstractDao<Funcionario, Long> implements FuncionarioDao{

    public List<Funcionario> findByNome(String nome){
        return creatQuery("select f from Funcionario f where f.nome like concat('%', ?1, '%')", nome);
    }

    @Override
    public List<Funcionario> findByCargo(Long id) {
        return creatQuery("select f from Funcionario f where f.cargo.id = ?1", id);
    }

    @Override
    public List<Funcionario> findByDataEntradaDataSaida(LocalDate entrada, LocalDate saida) {
        String jpql = new StringBuilder("select f from Funcionario f").append(" where f.dataEntrada >= ?1 and f.dataSaida <= ?2")
                .append(" order by f.dataEntrada asc").toString();

        return creatQuery(jpql, entrada, saida);
    }

    @Override
    public List<Funcionario> findByDataEntrada(LocalDate entrada) {
        String jpql = new StringBuilder("select f from Funcionario f").append(" where f.dataEntrada >= ?1 ")
                .append(" order by f.dataEntrada asc").toString();

        return creatQuery(jpql, entrada);
    }

    @Override
    public List<Funcionario> findByDataSaida(LocalDate saida) {
        String jpql = new StringBuilder("select f from Funcionario f").append(" where f.dataSaida <= ?1 ")
                .append(" order by f.dataEntrada asc").toString();

        return creatQuery(jpql, saida);
    }


}
