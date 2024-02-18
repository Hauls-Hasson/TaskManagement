package taskmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class GerenciadorTarefas implements OperacoesCRUD<Tarefas> {

    private List<Tarefas> listaTarefas;
    BancoDeDados bancodedados = new BancoDeDados("jdbc:postgresql://motty:5432/xjyjjau", "xjyjjauz", "xunZJNqYVSXRM4n688fZEcSL6RETkw9U");

    public GerenciadorTarefas() {
        this.listaTarefas = new ArrayList<>();
        bancodedados.carregarTarefasDoBanco(listaTarefas);
    }

    public List<Tarefas> getListaTarefas() {
        return listaTarefas;
    }

    @Override
    public void adicionar(Tarefas tarefa) {
        listaTarefas.add(tarefa);
        bancodedados.adicionar(tarefa);
        JOptionPane.showMessageDialog(null, "Tarefa adicionada com sucesso!");
    }

    @Override
    public void listar() {
        
    }

    @Override
    public void editar(int indice, Tarefas novaTarefa) {
        if (indice >= 0 && indice < listaTarefas.size()) {
            listaTarefas.set(indice, novaTarefa);
            bancodedados.editar(indice, novaTarefa);
            JOptionPane.showMessageDialog(null, "Tarefa editada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Índice inválido. Não foi possível editar a tarefa.");
        }
    }

    @Override
    public void removerTarefa(int indice) {
        if (indice >= 0 && indice < listaTarefas.size()) {
            listaTarefas.remove(indice);
            bancodedados.removerTarefa(indice);
            JOptionPane.showMessageDialog(null, "Tarefa removida com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Índice inválido. Não foi possível remover a tarefa.");
        }
    }

    public void filtrarPorDataInicio(String dataInicio) {
        StringBuilder detalhes = new StringBuilder("\n=== Tarefas filtradas por Data de Início ===\n");

        for (Tarefas tarefa : listaTarefas) {
            if (tarefa.getDatainicio().equals(dataInicio)) {
                detalhes.append(exibirDetalhesTarefa(tarefa)).append("\n");
            }
        }

        JOptionPane.showMessageDialog(null, detalhes.toString());
    }

    public void filtrarPorDataConclusao(String dataConclusao) {
        StringBuilder detalhes = new StringBuilder("\n=== Tarefas filtradas por Data de Conclusão ===\n");

        for (Tarefas tarefa : listaTarefas) {
            if (tarefa.getDataconclusao().equals(dataConclusao)) {
                detalhes.append(exibirDetalhesTarefa(tarefa)).append("\n");
            }
        }

        JOptionPane.showMessageDialog(null, detalhes.toString());
    }

    public void filtrarPorStatus(boolean status) {
        StringBuilder detalhes = new StringBuilder("\n=== Tarefas filtradas por Status ===\n");

        for (Tarefas tarefa : listaTarefas) {
            if (tarefa.getStatus() == status) {
                detalhes.append(exibirDetalhesTarefa(tarefa)).append("\n");
            }
        }

        JOptionPane.showMessageDialog(null, detalhes.toString());
    }

    private String exibirDetalhesTarefa(Tarefas tarefa) {
        return tarefa + "\n------------------------";
    }

    public void exibirTodasTarefas() {
        StringBuilder detalhes = new StringBuilder("\n=== Lista de Todas as Tarefas ===\n");

        for (Tarefas tarefa : listaTarefas) {
            detalhes.append(exibirDetalhesTarefa(tarefa)).append("\n");
        }

        JOptionPane.showMessageDialog(null, detalhes.toString());
    }
}
