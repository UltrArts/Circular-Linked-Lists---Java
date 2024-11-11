package controller;

import model.Venda;
import model.ProdutoListaCircularLigada;
import java.io.*;

public class VendaController {

    private ProdutoListaCircularLigada<Venda> listaVendas;
    private static final String FILE_NAME = "files/vendas.dat";

    public VendaController() {
        listaVendas = new ProdutoListaCircularLigada<>();
        loadVendas();
    }

    // Adiciona uma nova venda na lista
    public void adicionarVenda(Venda venda) {
        listaVendas.add(venda);
        saveVendas();
    }

    // Remove uma venda específica da lista (usando ID para garantir unicidade)
    public void removerVenda(int vendaId) {
        for (Venda venda : listaVendas) {
            if (venda.getId() == vendaId) {
                listaVendas.remove(venda);
                saveVendas();
                break;
            }
        }
    }

    // Retorna a lista circular de vendas
    public ProdutoListaCircularLigada<Venda> getListaVendas() {
        return listaVendas;
    }
    
     private void saveVendas() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(listaVendas);
        } catch (IOException e) {
            System.err.println("Erro ao salvar vendas: " + e.getMessage());
        }
    }

    private void loadVendas() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            listaVendas = (ProdutoListaCircularLigada<Venda>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de vendas não encontrado, iniciando com lista vazia.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar vendas: " + e.getMessage());
        }
    }
    
}
