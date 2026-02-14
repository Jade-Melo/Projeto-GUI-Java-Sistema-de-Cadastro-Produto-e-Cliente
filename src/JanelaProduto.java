import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.util.List;

public class JanelaProduto extends JFrame {
    private JTextField campoNomeProduto, campoDescricaoProduto, quantidade, precoUnitario, fornecedor;
    private JButton btnCadastrarProduto, btnLimparProduto;


    private JTable tabelaProduto;
    private DefaultTableModel modeloTabelaProduto;

    private boolean tabelaCarregadaProduto = false;


    public  JanelaProduto() {
        setTitle("Sistema Cadastro de Produto");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTituloCadastro = new JLabel("Cadastro do Produto", SwingConstants.CENTER);
        lblTituloCadastro.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloCadastro.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));


        // Menu
        JMenuBar barra = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));
        menuArquivo.add(itemSair);

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        itemSobre.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Sistema de Cadastro de Produto \nCRUD Completo\nVersão 2.0"));
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
        barra.add(itemJanelaPrincipal);

        // Abas
        JTabbedPane abas = new JTabbedPane();

        JPanel painelCadastroProduto = new JPanel(new GridLayout(7, 2, 10, 10));


        painelCadastroProduto.add(new JLabel("Nome:"));
        campoNomeProduto = new JTextField();
        painelCadastroProduto.add(campoNomeProduto);

        painelCadastroProduto.add(new JLabel("Descrição"));
        campoDescricaoProduto = new JTextField(10);
        painelCadastroProduto.add(campoDescricaoProduto);


        painelCadastroProduto.add(new JLabel("Quantidade"));
        quantidade = new JTextField(10);
        painelCadastroProduto.add(quantidade);


        painelCadastroProduto.add(new JLabel("Preço Unitário"));
        precoUnitario = new JTextField(10);
        painelCadastroProduto.add(precoUnitario);


        painelCadastroProduto.add(new JLabel("Fornecedor"));
        fornecedor = new JTextField(10);
        painelCadastroProduto.add(fornecedor);


        JPanel painelBotoesProduto = new JPanel();
        btnLimparProduto = new JButton("Limpar Campos");
        btnCadastrarProduto = new JButton("Cadastrar");
        painelBotoesProduto.add(btnLimparProduto);
        painelBotoesProduto.add(btnCadastrarProduto);


        JPanel painelCadastroProdutoCompleto = new JPanel(new BorderLayout());
        painelCadastroProdutoCompleto.add(lblTituloCadastro, BorderLayout.NORTH);
        painelCadastroProdutoCompleto.add(painelCadastroProduto, BorderLayout.CENTER);
        painelCadastroProdutoCompleto.add(painelBotoesProduto, BorderLayout.SOUTH);

        abas.addTab("Produto", painelCadastroProdutoCompleto);

        add(abas);
        setLocationRelativeTo(null);
        setVisible(true);
        abas.addTab("Produto", painelCadastroProdutoCompleto);


        // Painel Lista Produto

        JPanel painelListaProduto = new JPanel(new BorderLayout());

        JLabel lblTituloRelatorio = new JLabel("Relatório dos Produtos", SwingConstants.CENTER);
        lblTituloRelatorio.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloRelatorio.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        painelListaProduto.add(lblTituloRelatorio, BorderLayout.NORTH);

        modeloTabelaProduto = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Quantidade", "Fornecedor", "Preço Unitário"}, 0);
        tabelaProduto = new JTable(modeloTabelaProduto);
        painelListaProduto.add(new JScrollPane(tabelaProduto), BorderLayout.CENTER);

        JPanel painelListaBotoes = new JPanel();
        JButton btnAtualizarProduto = new JButton("Atualizar");
        JButton btnDeletarProduto = new JButton("Deletar");
        JButton btnGerarRelatorio = new JButton("Gerar Relatório");
        painelListaBotoes.add(btnAtualizarProduto);
        painelListaBotoes.add(btnDeletarProduto);
        painelListaBotoes.add(btnGerarRelatorio);
        painelListaProduto.add(painelListaBotoes, BorderLayout.SOUTH);

        abas.addTab("Relatório Produto", painelListaProduto);

        add(abas, BorderLayout.CENTER);

        btnCadastrarProduto.addActionListener(e-> cadastrarProduto());
        btnLimparProduto.addActionListener(e-> limparCamposProduto());
        btnGerarRelatorio.addActionListener(e-> carregarTabelaProduto());
        btnAtualizarProduto.addActionListener(e-> atualizarProduto());
        btnDeletarProduto.addActionListener(e-> deletarProduto());

    }

    private void deletarProduto() {
        if (!tabelaCarregadaProduto) {
            JOptionPane.showMessageDialog(this, "Clique em GERAR RELATÓRIO antes de excluir.");
            return;
        }


        int linha = tabelaProduto.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
            return;
        }


        int id = (int) tabelaProduto.getValueAt(linha, 0);


        new ProdutoDAO().excluir(id);


        JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");

        carregarTabelaProduto();


    }


    private void atualizarProduto() {
        if (!tabelaCarregadaProduto) {
            JOptionPane.showMessageDialog(this, "Clique em GERAR RELATÓRIO antes de atualizar.");
            return;
        }


        int linha = tabelaProduto.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para atualizar.");
            return;
        }


        int id = (int) tabelaProduto.getValueAt(linha, 0);
        String novoNome = JOptionPane.showInputDialog("Novo nome:", tabelaProduto.getValueAt(linha, 1));
        String novaDescricao = JOptionPane.showInputDialog("Nova descricao", tabelaProduto.getValueAt(linha, 2));
        int novaQuantidade = Integer.parseInt(JOptionPane.showInputDialog("Nova quantidade", tabelaProduto.getValueAt(linha, 3)));
        String novoFornecedor = JOptionPane.showInputDialog("Novo Fornecedor:", tabelaProduto.getValueAt(linha, 4));
        double novoPrecoUnitario = Double.parseDouble(JOptionPane.showInputDialog("Novo Preço Unitário:", tabelaProduto.getValueAt(linha, 5)));


        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(novoNome);
        produto.setDescricao(novaDescricao);
        produto.setQuantidade(novaQuantidade);
        produto.setFornecedor(novoFornecedor);
        produto.setPrecoUnitario(novoPrecoUnitario);


        new ProdutoDAO().atualizar(produto);


        JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!");

    }

    private void carregarTabelaProduto() {
        modeloTabelaProduto.setRowCount(0);
        List<Produto> lista = new ProdutoDAO().listar();


        for (Produto p : lista) {
            modeloTabelaProduto.addRow(new Object[]{p.getId(), p.getNome(), p.getDescricao(),p.getQuantidade(), p.getFornecedor(), p.getPrecoUnitario()});
        }
        tabelaCarregadaProduto= true;

    }

    private void limparCamposProduto() {
        campoNomeProduto.setText("");
        campoDescricaoProduto.setText("");
        quantidade.setText(String.valueOf(Integer.parseInt(quantidade.getText())));
        fornecedor.setText("");
        precoUnitario.setText(String.valueOf(Double.parseDouble(precoUnitario.getText())));
    }

    private void cadastrarProduto() {
        Produto produto = new Produto();
        produto.setNome(campoNomeProduto.getText());
        produto.setDescricao(campoDescricaoProduto.getText());
        produto.setQuantidade(Integer.parseInt(quantidade.getText()));
        produto.setFornecedor(fornecedor.getText());
        produto.setPrecoUnitario(Double.parseDouble(precoUnitario.getText()));


        new ProdutoDAO().salvar(produto);
        limparCamposProduto();
        JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");

    }

    public static void main(String[] args) {
        new JanelaProduto();
    }

}