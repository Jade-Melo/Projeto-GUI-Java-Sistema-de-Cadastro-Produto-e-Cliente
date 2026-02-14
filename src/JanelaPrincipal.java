import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    private JButton btnCliente;
    private JButton btnProduto;

    public JanelaPrincipal() {
        setTitle("Sistema de Cadastro");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Bem vindo ao Sistema!", SwingConstants.CENTER);
        JLabel subtitulo = new JLabel("Escolha cliente ou produto para administrar", SwingConstants.CENTER);

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        subtitulo.setFont(new Font("Arial", Font.BOLD, 14));

        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 5, 10));
        subtitulo.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        JPanel painelTopo = new JPanel();
        painelTopo.setLayout(new BoxLayout(painelTopo, BoxLayout.Y_AXIS));

        painelTopo.add(lblTitulo);
        painelTopo.add(subtitulo);

        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(painelTopo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel();

        btnCliente = new JButton("Cadastrar Cliente");
        btnProduto = new JButton("Cadastrar Produto");

        btnCliente.setFont(new Font("Arial", Font.BOLD, 15));
        btnCliente.setFocusPainted(false);

        btnProduto.setFont(new Font("Arial", Font.BOLD, 15));
        btnProduto.setFocusPainted(false);

        painelBotoes.add(btnCliente);
        painelBotoes.add(btnProduto);

        add(painelBotoes, BorderLayout.CENTER);

        btnCliente.addActionListener(e -> escolherCliente());
        btnProduto.addActionListener(e -> escolherProduto());

        setVisible(true);
    }

    private void escolherCliente() {
        new JanelaCliente().setVisible(true);
        this.dispose();
    }

    private void escolherProduto() {
        new JanelaProduto().setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
        new JanelaPrincipal();
    }
}
