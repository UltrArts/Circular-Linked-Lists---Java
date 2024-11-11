package controller;

import java.io.*;
import model.ProdutoListaCircularLigada;
import model.Item;
import view.MenuObserver;

public class ProdutoController {
    private ProdutoListaCircularLigada<Item> items;
    
    private static final String FILE_NAME = "files/items.dat";
    private MenuObserver observer;

    public ProdutoController() {
        items = new ProdutoListaCircularLigada<>();
        // Adicionando uma Pizza à lista com o novo atributo molho
        loadItems();
//        items.add(new Pizza(1, "Pizza de Calabresa", 20.0, "Calabresa", "Recheada", "Tomate"));
    }

    public ProdutoListaCircularLigada<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        saveItems();
    }
    
    
    private void saveItems() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(items);
        } catch (IOException e) {
            System.err.println("Erro ao salvar itens: " + e.getMessage());
        }
    }
    
     private void loadItems() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            items = (ProdutoListaCircularLigada<Item>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de itens não encontrado, iniciando com lista vazia.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar itens: " + e.getMessage());
        }
    }

 // Registra um observer (frame interessado)
    public void setObserver(MenuObserver observer) {
        this.observer = observer;
    }

    // Notifica o observer para atualizar as telas
    private void notifyObserver() {
        if (observer != null) {
            observer.updateItems();
        }
    }
}
