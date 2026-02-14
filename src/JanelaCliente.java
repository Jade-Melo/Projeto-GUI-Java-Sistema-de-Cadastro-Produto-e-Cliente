import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class JanelaCliente extends JFrame {

    private JTextField campoNomeCliente, campoEmailCliente, cpf;
    private JCheckBox checkEmail, checkNotificacao;
    private JRadioButton radioMasc, radioFem;
    private JButton btnCadastrarCliente, btnLimparCliente;

    private JTable tabelaCliente;
    private DefaultTableModel modeloTabelaCliente;
    private boolean tabelaCarregada = false;

    public JanelaCliente() {
        setTitle("Sistema Cadastro do Cliente");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JMenuBar barra = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));
        menuArquivo.add(itemSair);

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        itemSobre.addActionListener(e -> JOptionPane.showMessageDialog(this, "Sistema de Cadastro de Cliente \nCRUD Completo\nVersão 2.0"));
        menuAjuda.add(itemSobre);

        JMenuItem itemJanelaPrincipal = new JMenuItem("Janela Principal");
        itemJanelaPrincipal.addActionListener(e -> {
            new JanelaPrincipal().setVisible(true);
            this.dispose();
        });

        barra.add(menuArquivo);
        barra.add(menuAjuda);
        barra.add(itemJanelaPrincipal);
        setJMenuBar(barra);

        JTabbedPane abas = new JTabbedPane();

        JLabel lblTituloCadastro = new JLabel("Cadastro do Cliente", SwingConstants.CENTER);
        lblTituloCadastro.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloCadastro.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JPanel painelCadastroCliente = new JPanel(new GridLayout(7, 2, 10, 10));

        painelCadastroCliente.add(new JLabel("Nome:"));
        campoNomeCliente = new JTextField();
        painelCadastroCliente.add(campoNomeCliente);

        painelCadastroCliente.add(new JLabel("Email:"));
        campoEmailCliente = new JTextField();
        painelCadastroCliente.add(campoEmailCliente);

        painelCadastroCliente.add(new JLabel("CPF:"));
        cpf = new JTextField();
        painelCadastroCliente.add(cpf);

        painelCadastroCliente.add(new JLabel("Gênero:"));
        JPanel painelGenero = new JPanel();
        radioMasc = new JRadioButton("Masculino");
        radioFem = new JRadioButton("Feminino");
        ButtonGroup grupoGenero = new ButtonGroup();
        grupoGenero.add(radioMasc);
        grupoGenero.add(radioFem);
        painelGenero.add(radioMasc);
        painelGenero.add(radioFem);
        painelCadastroCliente.add(painelGenero);

        painelCadastroCliente.add(new JLabel(""));
        checkEmail = new JCheckBox("Receber Emails");
        painelCadastroCliente.add(checkEmail);

        painelCadastroCliente.add(new JLabel(""));
        checkNotificacao = new JCheckBox("Ativar Notificação");
        painelCadastroCliente.add(checkNotificacao);

        JPanel painelBotoes = new JPanel();
        btnCadastrarCliente = new JButton("Cadastrar");
        btnLimparCliente = new JButton("Limpar Campos");
        painelBotoes.add(btnCadastrarCliente);
        painelBotoes.add(btnLimparCliente);

        JPanel painelCadastroClienteCompleto = new JPanel(new BorderLayout());
        painelCadastroClienteCompleto.add(lblTituloCadastro, BorderLayout.NORTH);
        painelCadastroClienteCompleto.add(painelCadastroCliente, BorderLayout.CENTER);
        painelCadastroClienteCompleto.add(painelBotoes, BorderLayout.SOUTH);

        abas.addTab("Cadastro Cliente", painelCadastroClienteCompleto);

        JPanel painelListaCliente = new JPanel(new BorderLayout());
        JLabel lblTituloRelatorio = new JLabel("Relatório dos Clientes", SwingConstants.CENTER);
        lblTituloRelatorio.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloRelatorio.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        painelListaCliente.add(lblTituloRelatorio, BorderLayout.NORTH);

        modeloTabelaCliente = new DefaultTableModel(new Object[]{"ID", "Nome", "Email", "Gênero", "CPF"}, 0);
        tabelaCliente = new JTable(modeloTabelaCliente);
        painelListaCliente.add(new JScrollPane(tabelaCliente), BorderLayout.CENTER);

        JPanel painelListaBotoes = new JPanel();
        JButton btnAtualizarCliente = new JButton("Atualizar");
        JButton btnDeletarCliente = new JButton("Deletar");
        JButton btnGerarRelatorio = new JButton("Gerar Relatório");
        painelListaBotoes.add(btnAtualizarCliente);
        painelListaBotoes.add(btnDeletarCliente);
        painelListaBotoes.add(btnGerarRelatorio);
        painelListaCliente.add(painelListaBotoes, BorderLayout.SOUTH);

        abas.addTab("Relatório Cliente", painelListaCliente);

        add(abas, BorderLayout.CENTER);

        btnCadastrarCliente.addActionListener(e -> cadastrarCliente());
        btnLimparCliente.addActionListener(e -> limparCamposCliente());
        btnGerarRelatorio.addActionListener(e -> carregarTabelaCliente());
        btnAtualizarCliente.addActionListener(e -> atualizarCliente());
        btnDeletarCliente.addActionListener(e -> deletarCliente());
    }

    private void cadastrarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome(campoNomeCliente.getText());
        cliente.setEmail(campoEmailCliente.getText());
        cliente.setCpf(cpf.getText());
        cliente.setGenero(radioMasc.isSelected() ? "Masculino" : "Feminino");
        cliente.setReceberEmail(checkEmail.isSelected());
        cliente.setReceberNotificacao(checkNotificacao.isSelected());

        new ClienteDAO().salvar(cliente);
        limparCamposCliente();
        JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
    }

    private void limparCamposCliente() {
        campoNomeCliente.setText("");
        campoEmailCliente.setText("");
        cpf.setText("");
        radioMasc.setSelected(false);
        radioFem.setSelected(false);
        checkEmail.setSelected(false);
        checkNotificacao.setSelected(false);
    }

    private void carregarTabelaCliente() {
        modeloTabelaCliente.setRowCount(0);
        List<Cliente> lista = new ClienteDAO().listar();
        for (Cliente c : lista) {
            modeloTabelaCliente.addRow(new Object[]{c.getId(), c.getNome(), c.getEmail(), c.getGenero(), c.getCpf()});
        }
        tabelaCarregada = true;
    }

    private void atualizarCliente() {
        if (!tabelaCarregada) {
            JOptionPane.showMessageDialog(this, "Clique em GERAR RELATÓRIO antes de atualizar.");
            return;
        }
        int linha = tabelaCliente.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para atualizar.");
            return;
        }
        int id = (int) tabelaCliente.getValueAt(linha, 0);
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(JOptionPane.showInputDialog("Novo nome:", tabelaCliente.getValueAt(linha, 1)));
        cliente.setEmail(JOptionPane.showInputDialog("Novo email:", tabelaCliente.getValueAt(linha, 2)));
        cliente.setGenero(JOptionPane.showInputDialog("Novo gênero:", tabelaCliente.getValueAt(linha, 3)));
        cliente.setCpf(JOptionPane.showInputDialog("Novo CPF:", tabelaCliente.getValueAt(linha, 4)));
        new ClienteDAO().atualizar(cliente);
        carregarTabelaCliente();
        JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
    }

    private void deletarCliente() {
        if (!tabelaCarregada) {
            JOptionPane.showMessageDialog(this, "Clique em GERAR RELATÓRIO antes de excluir.");
            return;
        }
        int linha = tabelaCliente.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.");
            return;
        }
        int id = (int) tabelaCliente.getValueAt(linha, 0);
        new ClienteDAO().excluir(id);
        carregarTabelaCliente();
        JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
    }

    public static void main(String[] args) {
        new JanelaCliente().setVisible(true);
    }
}