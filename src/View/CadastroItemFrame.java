package view;

import controller.ProdutoController;
import model.Item;
import model.Pizza;
import model.Salgadinho;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class CadastroItemFrame extends JInternalFrame implements MenuObserver {
    private JTextField nomeField, precoField, recheioField, molhoField, massaField;
    private JComboBox<String> itemTypeCombo, tipoCombo, bordaCombo;
    private JButton addButton, updateButton, deleteButton;
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private ProdutoController produtoController;
    private int selectedRowIndex = -1;

    public CadastroItemFrame(ProdutoController produtoController) {
        super("Cadastro", true, true, true, true);
        this.produtoController = produtoController;
         // Registrar este frame como observer no MenuController
        produtoController.setObserver(this);
        // Definir o ícone da janela
        java.net.URL iconURL = getClass().getResource("/resources/list.png");
        if (iconURL != null) {
            setFrameIcon(new ImageIcon(iconURL));
        } else {
            System.err.println("Erro: Ícone de Gestão de Itens não encontrado em /resources/list.png");
        }
        // Registrar este frame como observer no MenuController

        // Conteúdo da janela
        JPanel panel = new JPanel();
        panel.add(new JLabel("Gestão"));
        add(panel, BorderLayout.CENTER);
        initComponents();
    }

    private void initComponents() {
        setTitle("Cadastro de Produto");
        setSize(900, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior com formulário usando GridBagLayout para layout responsivo
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Definindo a largura de cada coluna
        gbc.weightx = 1.0;

        // Linha 1
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Tipo de Item:"), gbc);
        gbc.gridx = 1;
        itemTypeCombo = new JComboBox<>(new String[]{"Pizza", "Salgadinho"});
        itemTypeCombo.addActionListener(e -> toggleFields());
        formPanel.add(itemTypeCombo, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 3;
        nomeField = new JTextField();
        formPanel.add(nomeField, gbc);

        // Linha 2
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        precoField = new JTextField();
        formPanel.add(precoField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Recheio:"), gbc);
        gbc.gridx = 3;
        recheioField = new JTextField();
        formPanel.add(recheioField, gbc);

        // Linha 3 - Campos específicos para Pizza
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Borda:"), gbc);
        gbc.gridx = 1;
        bordaCombo = new JComboBox<>(new String[]{"Não Recheada", "Recheada"});
        bordaCombo.addActionListener(e -> toggleRecheioField());
        formPanel.add(bordaCombo, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Molho:"), gbc);
        gbc.gridx = 3;
        molhoField = new JTextField();
        formPanel.add(molhoField, gbc);

        // Linha 4 - Campos específicos para Salgadinho
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Massa:"), gbc);
        gbc.gridx = 1;
        massaField = new JTextField();
        formPanel.add(massaField, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 3;
        tipoCombo = new JComboBox<>(new String[]{"Assado", "Frito"});
        formPanel.add(tipoCombo, gbc);

        toggleFields();

        // Painel inferior com botões centralizados
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Adicionar");
        updateButton = new JButton("Atualizar");
        deleteButton = new JButton("Excluir");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> saveItem());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());

        // Painel da Tabela
        String[] columnNames = {"ID", "Nome", "Preço", "Detalhes"};
        tableModel = new DefaultTableModel(columnNames, 0);
        itemTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(itemTable);
        itemTable.getSelectionModel().addListSelectionListener(e -> loadSelectedItem());

        // Adicionando painéis ao layout principal
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tableScrollPane, BorderLayout.SOUTH);

        refreshTable();
    }
    
    private void toggleFields() {
        boolean isPizza = itemTypeCombo.getSelectedItem().equals("Pizza");
        bordaCombo.setEnabled(isPizza);
        molhoField.setEnabled(isPizza);
        massaField.setEnabled(!isPizza);
        tipoCombo.setEnabled(!isPizza);
        toggleRecheioField(); // Atualiza o estado do campo recheio conforme a seleção da borda
    }

    private void toggleRecheioField() {
        recheioField.setEnabled(bordaCombo.getSelectedItem().equals("Recheada"));
    }

    private void saveItem() {
        String nome = nomeField.getText();
        double preco = Double.parseDouble(precoField.getText());
        String recheio = recheioField.isEnabled() ? recheioField.getText() : "";

        if (itemTypeCombo.getSelectedItem().equals("Pizza")) {
            String borda = (String) bordaCombo.getSelectedItem();
            String molho = molhoField.getText();
            Pizza pizza = new Pizza(produtoController.getItems().size() + 1, nome, preco, recheio, borda, molho);
            produtoController.addItem(pizza);
        } else {
            String massa = massaField.getText();
            String tipo = tipoCombo.getSelectedItem().toString();
            Salgadinho salgadinho = new Salgadinho(produtoController.getItems().size() + 1, nome, preco, tipo, massa, recheio);
            produtoController.addItem(salgadinho);
        }

        JOptionPane.showMessageDialog(this, "Item cadastrado com sucesso!");
        clearFields();
        refreshTable();
    }

    private void updateItem() {
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para atualizar.");
            return;
        }

        String nome = nomeField.getText();
        double preco = Double.parseDouble(precoField.getText());
        String recheio = recheioField.isEnabled() ? recheioField.getText() : "";

        Item item = produtoController.getItems().get(selectedRowIndex);
        String selectedItem = itemTypeCombo.getSelectedItem().toString().trim();

        if (selectedItem.equals("Pizza")) {
            if (item instanceof Pizza) {
                Pizza pizza = (Pizza) item;
                pizza.setNome(nome);
                pizza.setPreco(preco);
                pizza.setRecheio(recheio);
                pizza.setBorda((String) bordaCombo.getSelectedItem());
                pizza.setMolho(molhoField.getText());
            }
        } else if (selectedItem.equals("Salgadinho")) {
            if (item instanceof Salgadinho) {
                Salgadinho salgadinho = (Salgadinho) item;
                salgadinho.setNome(nome);
                salgadinho.setPreco(preco);
                salgadinho.setRecheio(recheio);
                salgadinho.setMassa(massaField.getText());
                salgadinho.setTipo(tipoCombo.getSelectedItem().toString());
            }
        }

        JOptionPane.showMessageDialog(this, "Item atualizado com sucesso!");
        clearFields();
        refreshTable();
    }


    private void deleteItem() {
         if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para excluir.");
            return;
        }

        // Exibe o popup de confirmação
        int confirmation = JOptionPane.showConfirmDialog(
                this, 
                "Você tem certeza que deseja excluir este item?", 
                "Confirmar exclusão", 
                JOptionPane.YES_NO_OPTION
        );

        // Se o usuário clicar em "Yes" (sim), prossegue com a exclusão
        if (confirmation == JOptionPane.YES_OPTION) {
            // Remover pelo índice
            produtoController.getItems().removeAt(selectedRowIndex);
             // Remover pelo objeto
            //menuController.getItems().remove(selectedItem);
            JOptionPane.showMessageDialog(this, "Item excluído com sucesso!");
            clearFields();
            refreshTable();
        } else {
            // Caso o usuário clique em "No" (não), nada acontece
            JOptionPane.showMessageDialog(this, "Exclusão cancelada.");
        }
    }

    private void loadSelectedItem() {
        selectedRowIndex = itemTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            Item item = produtoController.getItems().get(selectedRowIndex);

            nomeField.setText(item.getNome());
            precoField.setText(String.valueOf(item.getPreco()));
            
            if (item instanceof Pizza) {
                itemTypeCombo.setSelectedItem("Pizza");
                Pizza pizza = (Pizza) item;
                recheioField.setText(pizza.getRecheio());
                bordaCombo.setSelectedItem(pizza.getBorda());
                molhoField.setText(pizza.getMolho());
            } else if (item instanceof Salgadinho) {
                itemTypeCombo.setSelectedItem("Salgadinho");
                Salgadinho salgadinho = (Salgadinho) item;
                massaField.setText(salgadinho.getMassa());
                tipoCombo.setSelectedItem(salgadinho.getTipo());
            }
            toggleFields();
        }
        
    }

    private void clearFields() {
        nomeField.setText("");
        precoField.setText("");
        recheioField.setText("");
        bordaCombo.setSelectedIndex(0);
        molhoField.setText("");
        massaField.setText("");
        tipoCombo.setSelectedIndex(0);
        itemTypeCombo.setSelectedIndex(0);
        selectedRowIndex = -1;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Item item : produtoController.getItems()) {
            String detalhes = "";
            if (item instanceof Pizza) {
                Pizza pizza = (Pizza) item;
                detalhes = "Pizza - Borda: " + pizza.getBorda() + ", Recheio: " + pizza.getRecheio() + ", Molho: " + pizza.getMolho();
            } else if (item instanceof Salgadinho) {
                Salgadinho salgadinho = (Salgadinho) item;
                detalhes = "Salgadinho - Tipo: " + salgadinho.getTipo() + ", Massa: " + salgadinho.getMassa();
            }
            tableModel.addRow(new Object[]{item.getId(), item.getNome(), item.getPreco(), detalhes});
        }
    }

    @Override
    public void updateItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
