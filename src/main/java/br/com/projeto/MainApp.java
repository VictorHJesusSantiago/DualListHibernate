package br.com.projeto;

import br.com.projeto.dao.AlunoDAO;
import br.com.projeto.dao.UsuarioDAO;
import br.com.projeto.model.Aluno;
import br.com.projeto.util.DatabaseSeeder;
import br.com.projeto.view.AlunoFormDialog;
import br.com.projeto.view.DualListSelector;
import br.com.projeto.view.LoginView;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainApp extends JFrame {

    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final DualListSelector<Aluno> selector;

    public MainApp() {
        setTitle("Sistema Acadêmico - Painel Administrativo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(new EmptyBorder(5, 5, 5, 5));
        JButton btnNovo = createToolbarButton("Novo Aluno", new Color(0, 150, 136));
        JButton btnEditar = createToolbarButton("Ver/Editar", new Color(33, 150, 243));
        JButton btnExcluir = createToolbarButton("Excluir", new Color(244, 67, 54));
        toolbar.add(btnNovo); toolbar.add(Box.createHorizontalStrut(10));
        toolbar.add(btnEditar); toolbar.add(Box.createHorizontalStrut(10));
        toolbar.add(btnExcluir);
        add(toolbar, BorderLayout.NORTH);

        selector = new DualListSelector<>();
        add(selector, BorderLayout.CENTER);

        btnNovo.addActionListener(e -> {
            AlunoFormDialog dialog = new AlunoFormDialog(this, null);
            dialog.setVisible(true);
            if (dialog.isConfirmado()) { alunoDAO.salvarOuAtualizar(dialog.getAluno()); atualizarListas(); }
        });

        btnEditar.addActionListener(e -> {
            Aluno selecionado = selector.getSelectedSourceItem();
            if(selecionado != null) {
                AlunoFormDialog dialog = new AlunoFormDialog(this, selecionado);
                dialog.setVisible(true);
                if(dialog.isConfirmado()) { alunoDAO.salvarOuAtualizar(dialog.getAluno()); atualizarListas(); }
            } else { JOptionPane.showMessageDialog(this, "Selecione na esquerda para editar."); }
        });

        btnExcluir.addActionListener(e -> {
            Aluno selecionado = selector.getSelectedSourceItem();
            if (selecionado != null && JOptionPane.showConfirmDialog(this, "Excluir?", "Confirma", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                alunoDAO.excluir(selecionado); atualizarListas();
            }
        });

        JButton btnMatricular = new JButton("Salvar Matrículas");
        btnMatricular.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnMatricular.setBackground(new Color(76, 175, 80));
        btnMatricular.setForeground(Color.WHITE);
        btnMatricular.putClientProperty(FlatClientProperties.STYLE, "arc: 10; margin: 10,0,10,0");

        btnMatricular.addActionListener(e -> {
            List<Aluno> listaEsquerda = selector.getSourceItems();
            List<Aluno> listaDireita = selector.getTargetItems();

            for (Aluno a : listaDireita) {
                a.setMatriculadoNaDisciplina(true);
                alunoDAO.salvarOuAtualizar(a);
            }
            for (Aluno a : listaEsquerda) {
                a.setMatriculadoNaDisciplina(false);
                alunoDAO.salvarOuAtualizar(a);
            }
            JOptionPane.showMessageDialog(this, "Matrículas atualizadas com sucesso!");
        });

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(new EmptyBorder(10, 20, 10, 20));
        footer.add(btnMatricular, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        atualizarListas();
    }

    private void atualizarListas() {
        List<Aluno> todos = alunoDAO.listarTodos();
        List<Aluno> naoMatriculados = todos.stream()
                .filter(a -> !a.isMatriculadoNaDisciplina())
                .collect(Collectors.toList());

        List<Aluno> matriculados = todos.stream()
                .filter(Aluno::isMatriculadoNaDisciplina)
                .collect(Collectors.toList());

        selector.setSourceItems(naoMatriculados);
        selector.setTargetItems(matriculados);
    }

    private JButton createToolbarButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.putClientProperty(FlatClientProperties.STYLE, "arc: 10; margin: 5,15,5,15; borderWidth:0");
        return btn;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch (Exception ex) {}
        try {
            new UsuarioDAO().criarUsuarioAdminSeNaoExistir();
            DatabaseSeeder.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}