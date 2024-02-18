package taskmanagement;

import java.util.List;

import javax.swing.JOptionPane;

import java.sql.*;

public class BancoDeDados implements OperacoesCRUD<Tarefas> {

    private final String url;
    private final String usuario;
    private final String senha;

    public BancoDeDados(String url, String usuario, String senha) {
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    @Override 
    public void adicionar(Tarefas tarefa) {
     
        String sql = "INSERT INTO tarefas (usuario, titulo, descricao, data_inicio, data_conclusao_prevista, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tarefa.getUsuario());
            statement.setString(2, tarefa.getTitulo());
            statement.setString(3, tarefa.getDescricao());
            statement.setString(4, tarefa.getDatainicio());
            statement.setString(5, tarefa.getDataconclusao());
            statement.setBoolean(6, tarefa.getStatus());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void listar() {
            System.out.println("\n=== Lista de Tarefas do Banco de Dados ");
            String sql = "SELECT * FROM tarefas";
             try (Connection connection = DriverManager.getConnection(url, usuario, senha);
                  Statement statement = connection.createStatement();
                  ResultSet resultSet = statement.executeQuery(sql)
             ){

                while(resultSet.next()){
                    String usuario = resultSet.getString("usuario");
                    String titulo = resultSet.getString("titulo");
                    String descricao = resultSet.getString("descricao");
                    String datainicio = resultSet.getString("datainicio");
                    String dataconclusao = resultSet.getString("dataconclusao");
                    boolean status = resultSet.getInt("status") == 1;
                    Tarefas tarefa = new Tarefas (usuario, titulo, descricao,datainicio,dataconclusao);
                    tarefa.setStatus(status);
                    System.out.println(tarefa);
                    System.out.println("------------------");
                }
             } catch (SQLException e) {
                e.printStackTrace();
             }
    }

    @Override
    public void editar(int indice, Tarefas tarefa) {
     
        String sql = "UPDATE tarefas SET titulo=?, descricao=?, data_inicio=?, data_conclusao_prevista=?, status=? " +
                "WHERE id=?";

        try (Connection connection = DriverManager.getConnection(url, usuario, senha);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tarefa.getTitulo());
            statement.setString(2, tarefa.getDescricao());
            statement.setString(3, tarefa.getDatainicio());
            statement.setString(4, tarefa.getDataconclusao());
            statement.setBoolean(5, tarefa.getStatus());
            statement.setInt(6, indice + 1); // Índices no banco começam em 1

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removerTarefa(int indice) {
    
        String sql = "DELETE FROM tarefas WHERE id=?";

             try (Connection connection = DriverManager.getConnection(url, usuario, senha);
                  PreparedStatement statement = connection.prepareStatement(sql)) {

                 statement.setInt(1,indice+1);

                 int linhasAfetadas = statement.executeUpdate();
                    if (linhasAfetadas > 0){
                        JOptionPane.showMessageDialog(null, "Tarefa removida com sucesso!"); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Falah ao remover Tarefa!");
                    }

             } catch (SQLException e) {
                 e.printStackTrace();
             }
                
    }
    public  List<Tarefas> carregarTarefasDoBanco ( List<Tarefas> listaTarefas) {
       
        try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
            String sql = "SELECT * FROM tarefas"; // Consulta SQL para selecionar todas as tarefas
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Cria uma nova instância de Tarefas com os dados do ResultSet
                    Tarefas tarefa = new Tarefas(
                            resultSet.getString("usuario"),
                            resultSet.getString("titulo"),
                            resultSet.getString("descricao"),
                            resultSet.getString("data_inicio"),
                            resultSet.getString("data_conclusao")
                    );
                    tarefa.setStatus(resultSet.getBoolean("status")); // Define o status da tarefa
                    listaTarefas.add(tarefa); // Adiciona a tarefa à lista
                }
            }
        } catch (SQLException e) { // Captura exceções de SQL
           
            e.printStackTrace();
            // Se ocorrer uma exceção, a lista retornada será vazia 
            
            throw new RuntimeException("Erro ao carregar tarefas do banco de dados", e);
        }
        
        return listaTarefas; // Retorna a lista de tarefas (pode ser vazia se ocorrer uma exceção)
    }

    
    public String autenticarUsuario(String email, String senha) {
        try {
            // Lógica para consultar o banco de dados
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, usuario, senha);

            String query = "SELECT nome FROM usuarios WHERE email = ? AND senha = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, senha);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String nomeUsuario = resultSet.getString("nome");
                    System.out.println("Usuário autenticado com sucesso: " + nomeUsuario);
                    return nomeUsuario;
                } else {
                    System.out.println("Falha na autenticação para o usuário: " + email);
                    return null;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
}

}