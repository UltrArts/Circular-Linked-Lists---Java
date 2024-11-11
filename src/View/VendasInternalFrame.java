package view;

import controller.ProdutoController;
import controller.VendaController;
import model.Venda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Item;
import model.Pizza;

public class VendasInternalFrame extends JInternalFrame implements MenuObserver  {

    private JTabbedPane tabbedPane;
    private JPanel entradaPanel;
    private JPanel vendasPanel;
    private JTable productsTable;
    private JTable vendasTable;
    private DefaultTableModel produtosModel;
    private DefaultTableModel vendasModel;
    private VendaController vendaController;
    private ProdutoController menuController;

    // Controle da quantidade selecionada
    private JSpinner quantitySpinner;

    public VendasInternalFrame(VendaController vendaController, ProdutoController menuController) {
        super("Vendas", true, true, true, true);
        this.vendaController = vendaController;        
        this.menuController = menuController;
         // Registrar este frame como observer no MenuController
        menuController.setObserver(this);

        // Definir o ícone da janela
        java.net.URL iconURL = getClass().getResource("/resources/order.png");
        if (iconURL != null) {
            setFrameIcon(new ImageIcon(iconURL));
        } else {
            System.err.println("Erro: Ícone de Histórico de Vendas não encontrado em /resources/order.png");
        }

        // Inicializar a interface do usuário
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        // Primeira aba: Entrada de vendas
        entradaPanel = new JPanel();
        entradaPanel.setLayout(new BorderLayout());

        // Criação da tabela de produtos
        produtosModel = new DefaultTableModel(new Object[]{"ID", "Produto", "Preço", "Categoria"}, 0);
        productsTable = new JTable(produtosModel);
        loadProducts();

        JScrollPane scrollPane = new JScrollPane(productsTable);
        entradaPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        JLabel quantityLabel = new JLabel("Quantidade:");
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JButton sellButton = new JButton("Efetuar Venda");

        actionPanel.add(quantityLabel);
        actionPanel.add(quantitySpinner);
        actionPanel.add(sellButton);

        entradaPanel.add(actionPanel, BorderLayout.SOUTH);

        // Ação do botão de venda
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                efetuarVenda();
            }
        });

        tabbedPane.addTab("Entrada de Vendas", entradaPanel);

        // Segunda aba: Listar vendas
        vendasPanel = new JPanel();
        vendasPanel.setLayout(new BorderLayout());

        vendasModel = new DefaultTableModel(new Object[]{"ID Venda", "Produto", "Quantidade", "Preço Total"}, 0);
        vendasTable = new JTable(vendasModel);

        JScrollPane vendasScrollPane = new JScrollPane(vendasTable);
        vendasPanel.add(vendasScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Histórico de Vendas", vendasPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Carregar vendas na tabela
        loadVendas();

        // Define o tamanho preferido e ajusta o frame ao conteúdo
        setPreferredSize(new Dimension(900, 680));
        pack();
    }

    // Método para efetuar uma venda
    private void efetuarVenda() {
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para vender.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Coleta dos dados do produto e quantidade
        int productId = (int) produtosModel.getValueAt(selectedRow, 0);
        String productName = (String) produtosModel.getValueAt(selectedRow, 1);
        double productPrice = (double) produtosModel.getValueAt(selectedRow, 2);
        int quantity = (int) quantitySpinner.getValue();
        double totalPrice = productPrice * quantity;

        // Criação e adição de nova venda
        Venda novaVenda = new Venda(productId, productName, quantity, totalPrice);
        vendaController.adicionarVenda(novaVenda);

        // Atualizar a tabela de vendas
        vendasModel.addRow(new Object[]{novaVenda.getId(), productName, quantity, totalPrice});
        JOptionPane.showMessageDialog(this, "Venda efetuada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    // Carregar produtos na tabela (dados fictícios de exemplo)
    // Método para carregar produtos na tabela
    private void loadProducts() {
        produtosModel.setRowCount(0); // Limpa a tabela
        for (Item item : menuController.getItems()) {
            produtosModel.addRow(new Object[]{
                item.getId(),
                item.getNome(),
                item.getPreco(),
                item instanceof Pizza ? "Pizza" : "Outro"
            });
        }
    }

    // Carregar vendas na tabela a partir do controlador de vendas
    private void loadVendas() {
        vendasModel.setRowCount(0); // Limpar tabela de vendas
        for (Venda venda : vendaController.getListaVendas()) {
            vendasModel.addRow(new Object[]{
                venda.getId(), 
                venda.getProduto(), 
                venda.getQuantidade(), 
                venda.getPrecoTotal()
            });
        }
    }

    @Override
    public void updateItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
