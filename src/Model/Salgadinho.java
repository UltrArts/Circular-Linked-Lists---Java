package model;

import java.io.Serializable;

public class Salgadinho implements Item, Serializable {
    private static final long serialVersionUID = 1L; // Usado para garantir compatibilidade em versões
    
    private int id;
    private String nome;
    private double preco;
    private String tipo;  // Por exemplo: "Coxinha", "Kibe", etc.
    private String massa;
    private String recheio;

    public Salgadinho(int id, String nome, double preco, String tipo, String massa, String recheio) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tipo = tipo;
        this.massa = massa;
        this.recheio = recheio;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public double getPreco() {
        return preco;
    }

    // Métodos adicionais da classe Salgadinho
    public String getTipo() {
        return tipo;
    }

    public String getMassa() {
        return massa;
    }

    public String getRecheio() {
        return recheio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMassa(String massa) {
        this.massa = massa;
    }

    public void setRecheio(String recheio) {
        this.recheio = recheio;
    }
}
