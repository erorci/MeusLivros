package com.emerson.meuslivros.model;

import android.text.Editable;

import java.io.Serializable;

/**
 * Created by emerson on 04/03/16.
 */
public class Livros implements Serializable {

    private Long id;
    private String titulo;
    private String autor;
    private int paginas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    @Override
    public String toString() {
        return titulo.toString();
    }
}
