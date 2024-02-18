package taskmanagement;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class Autenticar {

    private static final long TEMPO_EXPIRACAO_TOKEN = 60 * 60 * 1000; // 1 hora em milissegundos
    private Map<String, Long> tokens = new HashMap<>();
    private BancoDeDados bancoDeDados;

    public Autenticar(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public String autenticarUsuario(String email, String senha) {
        String nomeUsuario = bancoDeDados.autenticarUsuario(email, senha);
        return nomeUsuario;
    }

    public boolean verificarToken(String token) {
        if (tokens.containsKey(token)) {
            long tempoExpiracao = tokens.get(token);
            if (tempoExpiracao > System.currentTimeMillis()) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Token expirado.");
                tokens.remove(token); // Remova o token expirado
            }
        }
        JOptionPane.showMessageDialog(null, "Token inválido.");
        return false;
    }

    public String gerarToken(String nomeUsuario) {
        if (nomeUsuario != null) {
            String token = gerarTokenAleatorio();
            long tempoExpiracao = System.currentTimeMillis() + TEMPO_EXPIRACAO_TOKEN;

            tokens.put(token, tempoExpiracao);
            JOptionPane.showMessageDialog(null, "Token gerado para usuário '" + nomeUsuario + "': " + token);

            return token;
        } else {
            JOptionPane.showMessageDialog(null, "Falha na autenticação para o usuário.");
            return null;
        }
    }

    private String gerarTokenAleatorio() {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }
}
