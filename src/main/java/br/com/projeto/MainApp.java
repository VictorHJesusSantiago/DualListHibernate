package br.com.projeto;

import br.com.projeto.controller.MatriculaController;
import br.com.projeto.dao.UsuarioDAO;
import br.com.projeto.model.Aluno;
import br.com.projeto.model.Disciplina;
import br.com.projeto.model.Usuario;
import br.com.projeto.util.DatabaseSeeder;
import br.com.projeto.view.AlunoFormDialog;
import br.com.projeto.view.DualListSelector;
import br.com.projeto.view.LoginView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(MainApp.class.getName());
    private final MatriculaController controller;
    private final DualListSelector<Aluno> selector;
    private final Usuario usuarioLogado;
    private JComboBox<Disciplina> comboDisciplinas;

    private final Color PRIMARY_COLOR = new Color(37, 99, 235);
    private final Color BG_COLOR = new Color(243, 244, 246);

    public MainApp(Usuario usuario) {
        this.usuarioLogado = usuario;
        this.controller = new MatriculaController();

        setTitle("Sistema Acadêmico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_COLOR);

        add(createNavbar(), BorderLayout.NORTH);

        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(BG_COLOR);
        contentWrapper.setBorder(new EmptyBorder(20, 20, 20, 20));

        selector = new DualListSelector<>();
        selector.setUsuarioLogado(usuario);

        selector.putClientProperty(FlatClientProperties.STYLE,
                "background: #FFFFFF; arc: 20; border: 1,1,1,1, #E5E7EB");

        contentWrapper.add(selector, BorderLayout.CENTER);
        add(contentWrapper, BorderLayout.CENTER);

        add(createFooterPanel(), BorderLayout.SOUTH);

        carregarDisciplinas();
    }

    private JPanel createNavbar() {
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(Color.WHITE);
        navbar.setBorder(new EmptyBorder(15, 25, 15, 25));
        navbar.putClientProperty(FlatClientProperties.STYLE, "border: 0,0,1,0, #E5E7EB");

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("Gestão de Matrículas");
        lblTitle.setFont(new Font("Inter", Font.BOLD, 20));
        lblTitle.setForeground(new Color(17, 24, 39));

        comboDisciplinas = new JComboBox<>();
        comboDisciplinas.setPreferredSize(new Dimension(250, 35));
        comboDisciplinas.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
        comboDisciplinas.addActionListener(e -> atualizarListas());

        JButton btnNovaDisciplina = createIconButton("+", new Color(229, 231, 235), Color.BLACK);
        btnNovaDisciplina.setToolTipText("Nova Disciplina");
        btnNovaDisciplina.addActionListener(e -> criarNovaDisciplina());

        leftPanel.add(lblTitle);
        leftPanel.add(Box.createHorizontalStrut(20));
        leftPanel.add(new JLabel("Disciplina:"));
        leftPanel.add(comboDisciplinas);
        leftPanel.add(btnNovaDisciplina);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        JButton btnNovoAluno = createButton("Novo Aluno", PRIMARY_COLOR, true);
        JButton btnEditar = createButton("Editar", new Color(255, 255, 255), false);
        JButton btnExcluir = createButton("Excluir", new Color(254, 226, 226), false);
        btnExcluir.setForeground(new Color(220, 38, 38)); // Texto Vermelho

        JButton btnLogout = createButton("Sair", new Color(75, 85, 99), true);

        rightPanel.add(btnNovoAluno);
        rightPanel.add(btnEditar);
        rightPanel.add(btnExcluir);
        rightPanel.add(Box.createHorizontalStrut(15));
        rightPanel.add(btnLogout);

        navbar.add(leftPanel, BorderLayout.WEST);
        navbar.add(rightPanel, BorderLayout.EAST);

        btnNovoAluno.addActionListener(e -> {
            AlunoFormDialog dialog = new AlunoFormDialog(this, null);
            dialog.setVisible(true);
            if (dialog.isConfirmado()) {
                controller.salvarAluno(dialog.getAluno());
                atualizarListas();
                JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
            }
        });

        btnEditar.addActionListener(e -> {
            Aluno selecionado = selector.getSelectedSourceItem();
            if (selecionado != null) {
                AlunoFormDialog dialog = new AlunoFormDialog(this, selecionado);
                dialog.setVisible(true);
                if (dialog.isConfirmado()) {
                    controller.salvarAluno(dialog.getAluno());
                    atualizarListas();
                }
            } else {
                showWarning("Selecione um aluno na lista 'Disponíveis' para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            Aluno selecionado = selector.getSelectedSourceItem();
            if (selecionado != null) {
                if (JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir " + selecionado.getNome() + "?",
                        "Confirmar Exclusão", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    controller.excluirAluno(selecionado);
                    atualizarListas();
                }
            } else {
                showWarning("Selecione um aluno na lista 'Disponíveis' para excluir.");
            }
        });

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        return navbar;
    }

    private JPanel createFooterPanel() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(BG_COLOR);
        footer.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton btnSalvar = new JButton("Salvar Alterações de Matrícula");
        btnSalvar.setFont(new Font("Inter", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(16, 185, 129)); // Verde Esmeralda
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setPreferredSize(new Dimension(300, 45));
        btnSalvar.putClientProperty(FlatClientProperties.STYLE,
                "arc: 50; margin: 0,20,0,20; hoverBackground: #059669");

        btnSalvar.addActionListener(e -> {
            Disciplina disciplina = (Disciplina) comboDisciplinas.getSelectedItem();
            if (disciplina != null) {
                controller.salvarMatriculas(disciplina, selector.getTargetItems());
                JOptionPane.showMessageDialog(this, "Matrículas atualizadas com sucesso!");
                carregarDisciplinas();
                comboDisciplinas.setSelectedItem(disciplina);
            } else {
                showWarning("Crie uma disciplina primeiro.");
            }
        });

        footer.add(btnSalvar);
        return footer;
    }


    private JButton createButton(String text, Color bg, boolean isPrimary) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Inter", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(isPrimary ? Color.WHITE : new Color(55, 65, 81));
        if (!isPrimary) {
            btn.putClientProperty(FlatClientProperties.STYLE, "arc: 12; margin: 6,16,6,16; borderWidth: 1; borderColor: #E5E7EB; focusWidth: 0");
        } else {
            btn.putClientProperty(FlatClientProperties.STYLE, "arc: 12; margin: 6,16,6,16; borderWidth: 0; focusWidth: 0");
        }
        return btn;
    }

    private JButton createIconButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Inter", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.putClientProperty(FlatClientProperties.STYLE,
                "arc: 10; margin: 0,10,0,10; borderWidth: 0");
        return btn;
    }

    private void showWarning(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    private void carregarDisciplinas() {
        List<Disciplina> disciplinas = controller.listarDisciplinas(usuarioLogado);
        comboDisciplinas.removeAllItems();
        for (Disciplina d : disciplinas) comboDisciplinas.addItem(d);
        if (!disciplinas.isEmpty()) comboDisciplinas.setSelectedIndex(0);
        atualizarListas();
    }

    private void atualizarListas() {
        Disciplina disciplina = (Disciplina) comboDisciplinas.getSelectedItem();
        selector.setSourceItems(controller.listarAlunosDisponiveis(disciplina));
        selector.setTargetItems(controller.listarAlunosMatriculados(disciplina));
    }

    private void criarNovaDisciplina() {
        String nome = JOptionPane.showInputDialog(this, "Nome da Nova Disciplina:");
        if (nome != null && !nome.trim().isEmpty()) {
            controller.criarDisciplina(nome, usuarioLogado);
            carregarDisciplinas();
            comboDisciplinas.setSelectedIndex(comboDisciplinas.getItemCount() - 1);
        }
    }

    public static void main(String[] args) {
        try {
            FlatMacLightLaf.setup();
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 12);
            UIManager.put("TextComponent.arc", 12);
            UIManager.put("ScrollBar.width", 10);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "Erro L&F", ex);
        }

        try {
            new UsuarioDAO().criarUsuarioAdminSeNaoExistir();
            DatabaseSeeder.run();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro init", e);
        }

        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}