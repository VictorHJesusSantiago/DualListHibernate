package br.com.projeto.view;

import br.com.projeto.model.Aluno;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
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

        setTitle(alunoParaEditar == null ? "Novo Aluno" : "Editar/Visualizar Aluno");
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        initUI();
        if (alunoParaEditar != null) preencherCampos();
    }

    private void initUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        addLabelAndField("Nome Completo:", txtNome, gbc, 0);
        addLabelAndField("Matrícula:", txtMatricula, gbc, 2);
        addLabelAndField("E-mail:", txtEmail, gbc, 4);
        addLabelAndField("Telefone:", txtTelefone, gbc, 6);

        JPanel btnPanel = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.setBackground(new Color(0, 120, 215));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        btnSalvar.addActionListener(e -> {
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();

            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                JOptionPane.showMessageDialog(this, "E-mail inválido! Por favor verifique.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String telefoneLimpo = telefone.replaceAll("[^0-9]", "");
            if (telefoneLimpo.length() < 10 || telefoneLimpo.length() > 11) {
                JOptionPane.showMessageDialog(this, "Telefone inválido! Insira um número com DDD (10 ou 11 dígitos).", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }

            aluno.setNome(txtNome.getText());
            aluno.setMatricula(txtMatricula.getText());
            aluno.setEmail(email);
            aluno.setTelefone(telefone);
            confirmado = true;
            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        btnPanel.add(btnSalvar);
        btnPanel.add(btnCancelar);

        gbc.gridy = 8;
        add(btnPanel, gbc);
    }

    private void addLabelAndField(String text, JTextField field, GridBagConstraints gbc, int y) {
        gbc.gridy = y;
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        add(label, gbc);
        gbc.gridy = y + 1;
        add(field, gbc);
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