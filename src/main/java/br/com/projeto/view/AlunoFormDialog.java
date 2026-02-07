package br.com.projeto.view;

import br.com.projeto.model.Aluno;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AlunoFormDialog extends JDialog {
    private final JTextField txtNome = new JTextField();
    private final JTextField txtMatricula = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextField txtTelefone = new JTextField();
    private boolean confirmado = false;
    private final Aluno aluno;

    public AlunoFormDialog(Frame parent, Aluno alunoParaEditar) {
        super(parent, true);
        this.aluno = (alunoParaEditar == null) ? new Aluno() : alunoParaEditar;

        setTitle(alunoParaEditar == null ? "Novo Aluno" : "Editar Aluno");
        setSize(450, 480);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        ((JPanel)getContentPane()).setBackground(Color.WHITE);

        add(createContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        if (alunoParaEditar != null) preencherCampos();
    }

    private JPanel createContent() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        addInput(panel, gbc, "Nome Completo", txtNome, 0);
        addInput(panel, gbc, "Matrícula", txtMatricula, 2);
        addInput(panel, gbc, "E-mail Acadêmico", txtEmail, 4);
        addInput(panel, gbc, "Telefone", txtTelefone, 6);

        return panel;
    }

    private void addInput(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int y) {
        gbc.gridy = y;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Inter", Font.BOLD, 12));
        lbl.setForeground(new Color(55, 65, 81));
        panel.add(lbl, gbc);

        gbc.gridy = y + 1;
        gbc.insets = new Insets(5, 0, 15, 0);
        field.setPreferredSize(new Dimension(0, 35));
        field.putClientProperty(FlatClientProperties.STYLE, "arc: 8; borderColor: #E5E7EB");
        panel.add(field, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);
    }

    private JPanel createFooter() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(249, 250, 251));
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.putClientProperty(FlatClientProperties.STYLE, "arc: 8; borderWidth: 1; borderColor: #D1D5DB");

        JButton btnSalvar = new JButton("Salvar Dados");
        btnSalvar.setBackground(new Color(37, 99, 235));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Inter", Font.BOLD, 13));
        btnSalvar.putClientProperty(FlatClientProperties.STYLE, "arc: 8; borderWidth: 0");

        btnCancelar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> salvar());

        panel.add(btnCancelar);
        panel.add(btnSalvar);
        return panel;
    }

    private void salvar() {
        if (txtNome.getText().isEmpty() || txtMatricula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Matrícula são obrigatórios.");
            return;
        }
        aluno.setNome(txtNome.getText());
        aluno.setMatricula(txtMatricula.getText());
        aluno.setEmail(txtEmail.getText());
        aluno.setTelefone(txtTelefone.getText());
        confirmado = true;
        dispose();
    }

    private void preencherCampos() {
        txtNome.setText(aluno.getNome());
        txtMatricula.setText(aluno.getMatricula());
        txtEmail.setText(aluno.getEmail());
        txtTelefone.setText(aluno.getTelefone());
    }

    public boolean isConfirmado() { return confirmado; }
    public Aluno getAluno() { return aluno; }
}