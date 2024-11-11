package model;

import java.io.Serializable;

public class Pizza implements Item, Serializable {
    private static final long serialVersionUID = 1L; // Usado para garantir compatibilidade em versões
    
    private int id;
    private String nome;
    private double preco;
    private String recheio;
    private String borda;
    private String molho;

    public Pizza(int id, String nome, double preco, String recheio, String borda, String molho) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.recheio = recheio;
        this.borda = borda;
        this.molho = molho;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getRecheio() {
        return recheio;
    }

    public String getBorda() {
        return borda;
    }

    public String getMolho() {
        return molho;
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

    public void setRecheio(String recheio) {
        this.recheio = recheio;
    }

    public void setBorda(String borda) {
        this.borda = borda;
    }

    public void setMolho(String molho) {
        this.molho = molho;
    }
}
