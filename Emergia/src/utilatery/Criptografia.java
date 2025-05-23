
package utilatery;
import org.mindrot.jbcrypt.BCrypt;
public class Criptografia {
    
    // Método para gerar o hash da senha
    public static String gerarHash(String senha) {
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia.");
        }
        
        // Gera o hash da senha com o salt automaticamente
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }
    
    // Método para verificar se a senha fornecida corresponde ao hash armazenado
    public static boolean verificarSenha(String senha, String hash) {
        if (senha == null || hash == null) {
            throw new IllegalArgumentException("Senha ou hash não pode ser nulo.");
        }
        // Verifica se a senha corresponde ao hash armazenado
        return BCrypt.checkpw(senha, hash);
    }
}

