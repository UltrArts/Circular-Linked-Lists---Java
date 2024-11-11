package view;

import controller.ProdutoController;
import controller.VendaController;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private ProdutoController produtoController;
    private VendaController vendaController;
    private JDesktopPane desktopPane;
    private JLabel backgroundLabel;

    public MainFrame(ProdutoController produtoController, VendaController vendaController) {
        this.produtoController = produtoController;
        this.vendaController = vendaController;
        setTitle("Quase Três Lanches - Sistema de Gestão");

        // Configurações da tela fixa
        setSize(920, 800);
        setMinimumSize(new Dimension(920, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criação da barra de menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Menu Gestão
        JMenu menuGestao = new JMenu("Gestão");
        menuBar.add(menuGestao);
        JMenuItem menuItemGestao = new JMenuItem("Gestão de Itens");
        menuGestao.add(menuItemGestao);

        // Menu Vendas
        JMenu menuVendas = new JMenu("Vendas");
        menuBar.add(menuVendas);
        JMenuItem menuItemVendas = new JMenuItem("Histórico de Vendas");
        menuVendas.add(menuItemVendas);

        // Desktop Pane para as janelas internas
        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        // Configuração da imagem de fundo responsiva
        backgroundLabel = new JLabel();
        backgroundLabel.setSize(920, 800);
        updateBackgroundImage();  // Inicializa a imagem de fundo
        
        desktopPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        // Eventos dos itens de menu
        menuItemGestao.addActionListener(e -> abrirGestao());
        menuItemVendas.addActionListener(e -> abrirHistoricoVendas());
    }

    // Método para carregar e redimensionar a imagem de fundo
    private void updateBackgroundImage() {
        desktopPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                java.net.URL imgURL = getClass().getResource("/resources/bg.png");
                if (imgURL != null) {
                    ImageIcon icon = new ImageIcon(imgURL);
                    Image scaledImage = icon.getImage().getScaledInstance(
                            desktopPane.getWidth(), desktopPane.getHeight(), Image.SCALE_SMOOTH
                    );
                    backgroundLabel.setIcon(new ImageIcon(scaledImage));
                    backgroundLabel.setBounds(0, 0, desktopPane.getWidth(), desktopPane.getHeight());
                } else {
                    System.err.println("Erro: Imagem de fundo não encontrada em /resources/bg.png");
                }
            }
        });
    }

    // Métodos para abrir a tela de gestão de itens e histórico de vendas
    private void abrirGestao() {
        // Verifica se já existe uma instância de CadastroItemFrame aberta
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof CadastroItemFrame) {
                frame.toFront();  // Coloca a janela existente em foco
                try {
                    frame.setSelected(true);
                } catch (java.beans.PropertyVetoException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        // Cria uma nova instância de CadastroItemFrame se nenhuma estiver aberta
        CadastroItemFrame cadastroItemFrame = new CadastroItemFrame(produtoController);
        cadastroItemFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        desktopPane.add(cadastroItemFrame);
        cadastroItemFrame.setVisible(true);
    }

    private void abrirHistoricoVendas() {
        // Verifica se já existe uma instância de VendasInternalFrame aberta
        
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (frame instanceof VendasInternalFrame) {
                frame.toFront();
                try {
                    frame.setSelected(true); // Tenta colocar a janela em primeiro plano
                } catch (java.beans.PropertyVetoException e) {
                    e.printStackTrace();
                }
                return;
            }
        }

        // Cria uma nova instância de VendasInternalFrame se nenhuma estiver aberta
        VendasInternalFrame vendasInternalFrame = new VendasInternalFrame(vendaController, produtoController);
        vendasInternalFrame.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        // Adiciona o frame ao desktopPane
        desktopPane.add(vendasInternalFrame);
//        desktopPane.add(vendasInternalFrame, JLayeredPane.PALETTE_LAYER);

        // Torna a janela visível e coloca em primeiro plano
        vendasInternalFrame.setVisible(true);

        // Força a atualização do desktopPane para garantir que a janela seja exibida corretamente
        desktopPane.revalidate();
        desktopPane.repaint();

        // Garante que a nova janela seja focada
        try {
            vendasInternalFrame.setSelected(true);
            vendasInternalFrame.toFront();
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }
        
    }

    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProdutoController produtoController = new ProdutoController();
            VendaController vendaController = new VendaController();
            MainFrame mainFrame = new MainFrame(produtoController, vendaController);
            mainFrame.setVisible(true);
        });
    }
}
