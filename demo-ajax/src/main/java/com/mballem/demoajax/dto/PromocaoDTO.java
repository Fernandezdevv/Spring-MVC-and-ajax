package com.mballem.demoajax.dto;

import com.mballem.demoajax.domain.Categoria;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PromocaoDTO {

    @NotNull
    private Long id;

    public @NotNull Long getId() {
        return id;
    }

    public void setId(@NotNull Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Um titulo é requirido") String getTitulo() {
        return titulo;
    }

    public void setTitulo(@NotBlank(message = "Um titulo é requirido") String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public @NotBlank(message = "uma imagem é requerida") String getLinkImagem() {
        return linkImagem;
    }

    public void setLinkImagem(@NotBlank(message = "uma imagem é requerida") String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public @NotNull(message = "O preço é requerido") BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(@NotNull(message = "O preço é requerido") BigDecimal preco) {
        this.preco = preco;
    }

    public @NotNull(message = "Uma categoria é requerida") Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotNull(message = "Uma categoria é requerida") Categoria categoria) {
        this.categoria = categoria;
    }

    @NotBlank(message = "Um titulo é requirido")
    private String titulo;

    private String descricao;

    @NotBlank(message = "uma imagem é requerida")
    private String linkImagem;

    @NotNull(message = "O preço é requerido")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private BigDecimal preco;

    @NotNull(message = "Uma categoria é requerida")
    private Categoria categoria;

}
