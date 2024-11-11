package model;

import java.io.Serializable;
import java.util.Iterator;

public class ProdutoListaCircularLigada<T> implements Iterable<T>, Serializable {
    private static final long serialVersionUID = 1L;
    
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;
    
      // Definição da classe Node interna
    private static class Node<T> implements Serializable {
        private static final long serialVersionUID = 1L;
        
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }


    
    // Método add para adicionar elementos à lista
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
            tail.next = head; // Faz a lista circular
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head; // Aponta o próximo do último elemento para o primeiro
        }
        size++;
    }

    // Método para obter um item na posição específica
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index fora dos limites.");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // Método para obter o tamanho da lista
    public int size() {
        return size;
    }

    // Método para remover um item pelo índice
    public void removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index fora dos limites.");
        }

        if (index == 0) { // Remover o primeiro nó
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                tail.next = head; // Atualiza o tail para apontar para o novo head
            }
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
            if (index == size - 1) { // Se for o último elemento, atualiza o tail
                tail = current;
            }
        }
        size--;
    }

    // Método para remover um item pelo objeto
    public void remove(T data) {
        if (head == null) {
            return; // Lista vazia
        }

        if (head.data.equals(data)) { // Se for o primeiro nó
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                tail.next = head; // Atualiza o tail para apontar para o novo head
            }
            size--;
            return;
        }

        Node<T> current = head;
        while (current.next != null && !current.next.data.equals(data)) {
            current = current.next;
        }

        if (current.next != null) { // Encontrou o item a ser removido
            current.next = current.next.next;
            if (current.next == head) { // Se for o último item, atualiza o tail
                tail = current;
            }
            size--;
        }
    }

    // Implementação do método Iterator para percorrer a lista
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;
            private boolean firstPass = true;

            @Override
            public boolean hasNext() {
                return current != null && (firstPass || current != head);
            }

            @Override
            public T next() {
                T data = current.data;
                current = current.next;
                firstPass = false;
                return data;
            }
        };
    }
}
