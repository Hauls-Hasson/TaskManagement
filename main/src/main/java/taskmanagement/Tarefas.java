package taskmanagement;

public class Tarefas {
    private String usuario;
    private String titulo;
    private String descricao;
    private String datainicio;
    private String dataconclusao;
    private boolean status; 

    
    public Tarefas(String usuario, String titulo, String descricao, String datainicio, String dataconclusao) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.datainicio = datainicio;
        this.dataconclusao = dataconclusao;
        this.status = false; 
        }

    // Getters e Setters
    public String getUsuario(){
        return usuario;
    }

    public void setUsuario(String usuario){
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(String datainicio) {
        this.datainicio = datainicio;
    }

    public String getDataconclusao() {
        return dataconclusao;
    }

    public void setDataconclusao(String dataconclusao) {
        this.dataconclusao = dataconclusao;
    }

    public boolean getStatus() {
        return status; 
    }

    public void setStatus(boolean status) {
        this.status = status; 
    }
    public String toString() {
        return "Tarefas: " + titulo + "\nDescrição: " + descricao + "\nStatus: " + (getStatus() ? "Concluída" : "Não concluída");
    }
}
