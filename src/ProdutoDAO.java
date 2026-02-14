import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private Connection conn;

    public ProdutoDAO(){
        conn = Conexao.getConnection();
    }

    //CREATE
    public void salvar(Produto produto) {
        String sql = "INSERT INTO produto (nome, descricao, quantidade, fornecedor , precoUnitario)" + "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setString(2,produto.getDescricao());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setString(4, produto.getFornecedor());
            stmt.setDouble(5, produto.getPrecoUnitario());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //READ
    public List<Produto> listar(){
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setFornecedor(rs.getString("fornecedor"));
                produto.setPrecoUnitario(rs.getDouble("precoUnitario"));
                lista.add(produto);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    //UPDATE
    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome=?, descricao=?, quantidade=?, fornecedor=?, precoUnitario=? WHERE id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setString(4, produto.getFornecedor());
            stmt.setDouble(5, produto.getPrecoUnitario());
            stmt.setInt(6, produto.getId());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //DELETE
    public void excluir(int id){
        String sql = "DELETE FROM produto WHERE id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}