package test;
public class BancodeDadosTest {

    
    public void testRemoverTarefa() {
        // Configuração
        String url = "jdbc:postgresql://mott.db.elephantsql.com:5432/qbxnpisz";
        String usuario = "qbxnpisz";
        String senha = "wy3xBTbG1KVo6o-Yw9yVEllxImAUIa_c";

        BancoDeDados bancoDeDadosDeTeste = new BancoDeDados(url, usuario, senha);

        // Adicione uma tarefa para o teste
        Tarefas tarefaTeste = new Tarefas("Teste","Teste", "Descrição do Teste", "01/01/2022", "02/01/2022");
        bancoDeDadosDeTeste.adicionar(tarefaTeste);

        // Execução do teste
        bancoDeDadosDeTeste.removerTarefa(1); // Assumindo que a tarefa adicionada tem índice 1

        // Verificação
        // Aqui você pode adicionar verificações adicionais, por exemplo, verificar se a tarefa foi removida corretamente
    }
}