package model;

import java.io.Serializable;


public class Venda implements Serializable  {
    private static final long serialVersionUID = 1L; // Usado para garantir compatibilidade em versões

    private int id;
    private String produto;
    private int quantidade;
    private double precoTotal;
    private Venda next;  // Próxima venda na lista circular

    public Venda(int id, String produto, int quantidade, double precoTotal) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
        this.next = null;
    }
    
    public Venda(int id) {
        this.id = id;
    }

    // Getters para acessar os atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(double precoTotal) {
        this.precoTotal = precoTotal;
    }

    public Venda getNext() {
        return next;
    }

    public void setNext(Venda next) {
        this.next = next;
    }
    
}
