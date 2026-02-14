import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdministradorDAO {

    private Connection conn;

    public AdministradorDAO() {
        conn = Conexao.getConnection();
    }
    public boolean autenticar(String nome, String email, String senha) {
        String sql = "SELECT * FROM Administrador WHERE nome = ? AND email = ? AND senha = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
            return false;
        }
    }
}
