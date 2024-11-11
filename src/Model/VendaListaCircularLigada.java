/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Iterator;

public class VendaListaCircularLigada implements Iterable<Venda>, Serializable {
    private static final long serialVersionUID = 1L;
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    private class Node implements Serializable{
        private static final long serialVersionUID = 1L;
        Venda data;
        Node next;

        Node(Venda data) {
            this.data = data;
        }
    }

        


    // Método para adicionar uma venda à lista
    public void addVenda(Venda novaVenda) {
        Node newNode = new Node(novaVenda);
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

    // Método para obter uma venda por índice
    public Venda get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fora dos limites.");
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    // Método para obter o tamanho da lista
    public int size() {
        return size;
    }

    // Método para remover uma venda pelo índice
    public void removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fora dos limites.");
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
            Node current = head;
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

    // Método para remover uma venda pelo objeto
    public void removeVenda(Venda venda) {
        if (head == null) {
            return; // Lista vazia
        }

        if (head.data.equals(venda)) { // Se for o primeiro nó
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

        Node current = head;
        while (current.next != head && !current.next.data.equals(venda)) {
            current = current.next;
        }

        if (current.next != head) { // Encontrou o item a ser removido
            current.next = current.next.next;
            if (current.next == head) { // Se for o último item, atualiza o tail
                tail = current;
            }
            size--;
        }
    }

    // Implementação do método Iterator para percorrer a lista
    @Override
    public Iterator<Venda> iterator() {
        return new Iterator<Venda>() {
            private Node current = head;
            private boolean firstPass = true;

            @Override
            public boolean hasNext() {
                return current != null && (firstPass || current != head);
            }

            @Override
            public Venda next() {
                Venda data = current.data;
                current = current.next;
                firstPass = false;
                return data;
            }
        };
    }
}
