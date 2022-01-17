package com.sofka.BibliotecaWebFlux.dto;

import java.time.LocalDate;
import java.util.Date;

public class DtoRecurso {
    private String id;
    private String titulo;
    private String autor;
    private String tematica;
    private String tipo;
    private boolean disponible;
    private LocalDate fechaPrestamo;

    public DtoRecurso(){}

    public DtoRecurso(String id, String titulo, String autor, String tematica, String tipo, boolean disponible, LocalDate fechaPrestamo) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.tematica = tematica;
        this.tipo = tipo;
        this.disponible = disponible;
        this.fechaPrestamo = fechaPrestamo;
    }

    public DtoRecurso(String titulo, String autor, String tematica, String tipo, boolean disponible, LocalDate fechaPrestamo) {
        this.titulo = titulo;
        this.autor = autor;
        this.tematica = tematica;
        this.tipo = tipo;
        this.disponible = disponible;
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
}