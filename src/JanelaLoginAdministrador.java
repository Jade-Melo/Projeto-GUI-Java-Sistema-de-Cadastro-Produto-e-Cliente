import javax.swing.*;
import java.awt.*;

public class JanelaLoginAdministrador extends JFrame {

    private JTextField campoNome;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton btnEntrar;

    public JanelaLoginAdministrador() {
        setTitle("Login do Administrador");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Login do Administrador", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCampos.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        campoNome = new JTextField(20);
        painelCampos.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painelCampos.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        campoEmail = new JTextField(20);
        painelCampos.add(campoEmail, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCampos.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        campoSenha = new JPasswordField(20);
        painelCampos.add(campoSenha, gbc);

        add(painelCampos, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        btnEntrar = new JButton("Entrar");
        painelBotoes.add(btnEntrar);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        add(painelBotoes, BorderLayout.SOUTH);

        btnEntrar.addActionListener(e -> logar());
        campoSenha.addActionListener(e -> logar());

        setVisible(true);
    }

    private void logar() {

        String nome = campoNome.getText();
        String email = campoEmail.getText();
        String senha = new String(campoSenha.getPassword());

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        AdministradorDAO dao = new AdministradorDAO();

        if (dao.autenticar(nome, email, senha)) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            new JanelaPrincipal().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Nome, Email ou Senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            campoSenha.setText("");
            campoNome.requestFocus();
        }
    }

    public static void main(String[] args) {
        new JanelaLoginAdministrador();
    }
}