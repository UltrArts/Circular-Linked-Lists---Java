package model;

public class ItemsVenda {

    private Item item;         // Pode ser Pizza ou Salgadinho
    private int quantidade;    // Quantidade do item no pedido
    private double precoTotal; // Preço total (preço do item * quantidade)

    public ItemsVenda(Item item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
        this.precoTotal = item.getPreco() * quantidade; // Calculando o preço total
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.precoTotal = item.getPreco() * quantidade; // Atualizando o preço total
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    @Override
    public String toString() {
        return "Item: " + item.getNome() + ", Quantidade: " + quantidade + ", Preço Total: " + precoTotal;
    }
}
