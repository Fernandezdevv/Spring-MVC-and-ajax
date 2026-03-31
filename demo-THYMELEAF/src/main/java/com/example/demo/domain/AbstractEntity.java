package com.example.demo.domain;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;


@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractEntity <ID extends Serializable> implements  Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity<?> that = (AbstractEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "id=" + id;
    }

    //teste de arquivo
}


