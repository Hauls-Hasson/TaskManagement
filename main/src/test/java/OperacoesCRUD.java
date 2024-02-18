

public interface OperacoesCRUD<T> {
    void adicionar(T objeto);
    void listar();
    void editar(int indice, T objeto);
    void removerTarefa(int indice);
}
