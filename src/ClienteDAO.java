import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection conn;

    public ClienteDAO() {
        conn = Conexao.getConnection();
    }

    public void salvar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, email, genero, cpf,receberEmail, receberNotificacao)" + " VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getGenero());
            stmt.setString(4, cliente.getCpf());
            stmt.setBoolean(5,cliente.isReceberEmail());
            stmt.setBoolean(6,cliente.isReceberNotificacao());
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT * FROM Cliente";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setGenero(rs.getString("genero"));
                c.setCpf(rs.getString("cpf"));
                c.setReceberEmail(rs.getBoolean("receberEmail"));
                c.setReceberNotificacao(rs.getBoolean("receberNotificacao"));
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void atualizar(Cliente c) {
        String sql = "UPDATE cliente SET nome=?, email=?, genero=?, cpf=?, receberEmail=?, receberNotificacao=? WHERE id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getEmail());
            stmt.setString(3, c.getGenero());
            stmt.setString(4, c.getCpf());
            stmt.setBoolean(5, c.isReceberEmail());
            stmt.setBoolean(6, c.isReceberNotificacao());
            stmt.setInt(7, c.getId());

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM cliente WHERE id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
