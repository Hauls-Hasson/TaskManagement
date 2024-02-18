package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import taskmanagement.BancoDeDados;
import taskmanagement.GerenciadorTarefas;

public class GerenciadorTarefasTeste {
    private GerenciadorTarefas gerenciadorTarefas;
    final List<Tarefas> tabelaDeTarefas=null;
    private String usuario = "test_user";
    protected function funcao;

    public void iniciar() {
        // Inicializar o GerenciadorTarefas e o banco de dados (configurações específicas do teste)
        BancoDeDados bancoDeDadosDeTeste = new BancoDeDados("jdbc:postgresql://motty:5432/xjyjjau","xjyjjauz","xunZJNqYVSXRM4n688fZEcSL6RETkw9U");

        // Criar tabela de tarefas (pode não ser necessário se a tabela já existe)
        
        bancoDeDadosDeTeste.carregarTarefasDoBanco(tabelaDeTarefas);
    }

    public void adicionarListarRemoverTarefaTest() {
        // Adicionar uma tarefa
        
        Tarefas tarefa = new Tarefas(usuario,"teste", "Testando as funcionalidades", "2022-02-20","2022-02-25");
        BancoDeDados.adicionar(tarefa);
        // Listar tarefas para o usuário específico
        String sql = "SELECT * FROM tarefas WHERE usuario = ?";
        
        try (
            Connection connection = DriverManager.getConnection("jdbc:postgresql://motty:5432/xjyjjau","xjyjjauz","xunZJNqYVSXRM4n688fZEcSL6RETkw9U");
        PreparedStatement statement = connection.prepareStatement(sql)){
        statement.setString(1, usuario);
           
        ResultSet resultSet = statement.executeQuery();
        if(resultSet != null ){
         // Remover a tarefa
         while (resultSet != null) {
        gerenciadorTarefas.removerTarefa(1);   
        }
        
    }

    }catch (SQLException e) {
            
            e.printStackTrace();
        }
        
}

}