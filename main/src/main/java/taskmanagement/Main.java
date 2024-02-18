package taskmanagement;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private String token = null;
    private String usuario = null;
   private JTextField txtfieldEmail;
    private JPasswordField passwordFieldSenha;
    BancoDeDados bancodedados = new BancoDeDados("jdbc:postgresql://motty:5432/xjyjjau", "xjyjjauz", "xunZJNqYVSXRM4n688fZEcSL6RETkw9U");
    private Autenticar autenticar = new Autenticar(bancodedados);
    private GerenciadorTarefas gerenciador = new GerenciadorTarefas();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main mainInit = new Main();
            mainInit.iniciarSistema();
        });
    }

    private void iniciarSistema() {
        while (token == null) {
            exibirTelaLogin();
        }
        telaPrincipal();
    }

    private void exibirTelaLogin() {
        JPanel painelLogin = new JPanel();
        txtfieldEmail = new JTextField(15);
        passwordFieldSenha = new JPasswordField(15);

        painelLogin.add(new JLabel("Email:"));
        painelLogin.add(txtfieldEmail);
        painelLogin.add(new JLabel("Senha:"));
        painelLogin.add(passwordFieldSenha);

        int opcao = JOptionPane.showConfirmDialog(null, painelLogin, "Login", JOptionPane.OK_CANCEL_OPTION);

        if (opcao == JOptionPane.OK_OPTION) {
            String email = txtfieldEmail.getText();
            char[] senha = passwordFieldSenha.getPassword();

            // Chame a função de autenticação e geração de token
            usuario = autenticar.autenticarUsuario(email, new String(senha));
            token = autenticar.gerarToken(usuario);

            if (usuario == null || token == null) {
                JOptionPane.showMessageDialog(null, "Login ou senha incorretos. Tente novamente.");
                token = null; // Resetar o token se a autenticação falhar
            }
        } else {
            System.exit(0); // Se o usuário cancelar, encerre o programa
        }
    }

    private void telaPrincipal() {
        
        JFrame frame = new JFrame("Gerenciador de Tarefas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JButton btnCriarTarefa = new JButton("Criar Tarefa");
        JButton btnListarTarefas = new JButton("Listar Tarefas");
        JButton btnModificarTarefa = new JButton("Modificar Tarefa");
        JButton btnExcluirTarefa = new JButton("Excluir Tarefa");
        JButton btnSair = new JButton("Sair");

        btnCriarTarefa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean autentificacaoDeTokenAdicionar = autenticar.verificarToken(token); 
                if (autentificacaoDeTokenAdicionar==false) {
                    
                    JOptionPane.showMessageDialog(null, "Token inválido ou expirado. Por favor entre novamente no sistema.");
                    return;
                }
                criarTarefa();
            }
        });

        btnListarTarefas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean autentificacaoDeTokenListar = autenticar.verificarToken(token); 
                if (autentificacaoDeTokenListar==false) {
                    
                    JOptionPane.showMessageDialog(null, "Token inválido ou expirado. Por favor entre novamente no sistema.");
                    return;
                }

                listarTarefas();
            }
        });

        btnModificarTarefa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean autentificacaoDeTokenModificar = autenticar.verificarToken(token); 
                if (autentificacaoDeTokenModificar==false) {
                    
                    JOptionPane.showMessageDialog(null, "Token inválido ou expirado. Por favor entre novamente no sistema.");
                    return;
                }

                modificarTarefa();
            }
        });

        btnExcluirTarefa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean autentificacaoDeTokenExcluir = autenticar.verificarToken(token); 
                if (autentificacaoDeTokenExcluir==false) {
                    
                    JOptionPane.showMessageDialog(null, "Token inválido ou expirado. Por favor entre novamente no sistema.");
                    return;
                }
                excluirTarefa();
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(btnCriarTarefa);
        frame.add(btnListarTarefas);
        frame.add(btnModificarTarefa);
        frame.add(btnExcluirTarefa);
        frame.add(btnSair);

        frame.setVisible(true);
    }

    private void criarTarefa() {
        JTextField txtTitulo = new JTextField();
    JTextField txtDescricao = new JTextField();
    JTextField txtDataInicio = new JTextField();
    JTextField txtDataConclusao = new JTextField();

    JPanel painel = new JPanel();
    painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

    painel.add(new JLabel("Título da Tarefa:"));
    painel.add(txtTitulo);
    painel.add(new JLabel("Descrição da Tarefa:"));
    painel.add(txtDescricao);
    painel.add(new JLabel("Data de Início (formato: dd/MM/yyyy):"));
    painel.add(txtDataInicio);
    painel.add(new JLabel("Data de Conclusão Prevista (formato: dd/MM/yyyy):"));
    painel.add(txtDataConclusao);

    int resultado = JOptionPane.showConfirmDialog(null, painel,
            "Criar Nova Tarefa", JOptionPane.OK_CANCEL_OPTION);

    if (resultado == JOptionPane.OK_OPTION) {
        String titulo = txtTitulo.getText();
        String descricao = txtDescricao.getText();
        String dataInicio = txtDataInicio.getText();
        String dataConclusao = txtDataConclusao.getText();

        // Criar uma nova tarefa
        Tarefas novaTarefa = new Tarefas(usuario, titulo, descricao, dataInicio, dataConclusao);
        gerenciador.adicionar(novaTarefa);

        JOptionPane.showMessageDialog(null, "Tarefa criada com sucesso!");
    }
    }

    private void listarTarefas() {
        JTextField txtDataInicio = new JTextField();
        JTextField txtDataConclusao = new JTextField();
        JTextField txtStatus = new JTextField();
    
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
    
        painel.add(new JLabel("Digite a data de início desejada (formato: dd/MM/yyyy):"));
        painel.add(txtDataInicio);
        painel.add(new JLabel("Digite a data de conclusão desejada (formato: dd/MM/yyyy):"));
        painel.add(txtDataConclusao);
        painel.add(new JLabel("Digite o status desejado (1 para concluídas, 0 para não concluídas):"));
        painel.add(txtStatus);
    
        int resultado = JOptionPane.showConfirmDialog(null, painel,
                "Listar Tarefas", JOptionPane.OK_CANCEL_OPTION);
    
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String dataInicio = txtDataInicio.getText();
                String dataConclusao = txtDataConclusao.getText();
                String statusText = txtStatus.getText();
    
                if (!dataInicio.isEmpty()) {
                    gerenciador.filtrarPorDataInicio(dataInicio);
                } else if (!dataConclusao.isEmpty()) {
                    gerenciador.filtrarPorDataConclusao(dataConclusao);
                } else if (!statusText.isEmpty()) {
                    boolean status = Integer.parseInt(statusText) == 1;
                    gerenciador.filtrarPorStatus(status);
                } else {
                    gerenciador.exibirTodasTarefas();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Digite valores válidos.");
            }
        }
    }

    private void modificarTarefa() {
        JTextField txtIndice = new JTextField();
        JTextField txtNovoTitulo = new JTextField();
        JTextField txtNovaDescricao = new JTextField();
        JTextField txtNovaDataInicio = new JTextField();
        JTextField txtNovaDataConclusao = new JTextField();
    
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
    
        painel.add(new JLabel("Digite o índice da tarefa que deseja modificar:"));
        painel.add(txtIndice);
        painel.add(new JLabel("Digite o novo título da tarefa:"));
        painel.add(txtNovoTitulo);
        painel.add(new JLabel("Digite a nova descrição da tarefa:"));
        painel.add(txtNovaDescricao);
        painel.add(new JLabel("Digite a nova data de início da tarefa:"));
        painel.add(txtNovaDataInicio);
        painel.add(new JLabel("Digite a nova data de conclusão prevista da tarefa:"));
        painel.add(txtNovaDataConclusao);
    
        int resultado = JOptionPane.showConfirmDialog(null, painel,
                "Modificar Tarefa", JOptionPane.OK_CANCEL_OPTION);
    
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int indice = Integer.parseInt(txtIndice.getText());
                String novoTitulo = txtNovoTitulo.getText();
                String novaDescricao = txtNovaDescricao.getText();
                String novaDataInicio = txtNovaDataInicio.getText();
                String novaDataConclusao = txtNovaDataConclusao.getText();
    
                // Modificar a tarefa
                Tarefas novaTarefa = new Tarefas(usuario, novoTitulo, novaDescricao, novaDataInicio, novaDataConclusao);
                gerenciador.editar(indice, novaTarefa);
    
                JOptionPane.showMessageDialog(null, "Tarefa modificada com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Digite um índice válido.");
            }
        }
    }

    private void excluirTarefa() {

        JTextField txtIndice = new JTextField();

    JPanel painel = new JPanel();
    painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

    painel.add(new JLabel("Digite o índice da tarefa que deseja excluir:"));
    painel.add(txtIndice);

    int resultado = JOptionPane.showConfirmDialog(null, painel,
            "Excluir Tarefa", JOptionPane.OK_CANCEL_OPTION);

    if (resultado == JOptionPane.OK_OPTION) {
        try {
            int indice = Integer.parseInt(txtIndice.getText());

            // Excluir a tarefa
            gerenciador.removerTarefa(indice);
        JOptionPane.showMessageDialog(null, "Tarefa removida com sucesso!");
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Digite um índice válido.");
    }
}
    }
}