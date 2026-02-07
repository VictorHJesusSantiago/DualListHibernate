package br.com.projeto.view;

import br.com.projeto.dao.UsuarioDAO;
import br.com.projeto.model.Usuario;
import br.com.projeto.util.SecurityUtil;
import br.com.projeto.MainApp;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginView extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(LoginView.class.getName());
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public LoginView() {
        setTitle("Acesso ao Sistema");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createRegisterPanel(), "REGISTER");

        add(mainPanel);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Bem-vindo");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField txtLogin = new JTextField();
        txtLogin.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha");

        JButton btnEntrar = createStyledButton("Entrar", new Color(33, 150, 243));
        JButton btnIrCadastro = new JButton("Não tem conta? Cadastre-se");
        btnIrCadastro.setBorderPainted(false);
        btnIrCadastro.setContentAreaFilled(false);
        btnIrCadastro.setForeground(Color.BLUE);
        btnIrCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 0; panel.add(title, gbc);
        gbc.gridy = 1; panel.add(Box.createVerticalStrut(20), gbc);
        gbc.gridy = 2; panel.add(txtLogin, gbc);
        gbc.gridy = 3; panel.add(txtSenha, gbc);
        gbc.gridy = 4; panel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy = 5; panel.add(btnEntrar, gbc);
        gbc.gridy = 6; panel.add(btnIrCadastro, gbc);

        btnIrCadastro.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER"));

        btnEntrar.addActionListener(e -> {
            String login = txtLogin.getText();
            String senha = new String(txtSenha.getPassword());
            Usuario u = usuarioDAO.buscarPorLogin(login);
            if (u != null && SecurityUtil.checkPassword(senha, u.getSenhaHash())) {
                abrirSistemaPrincipal(u);
            } else {
                JOptionPane.showMessageDialog(this, "Login ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel title = new JLabel("Nova Conta");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField txtLogin = new JTextField();
        txtLogin.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Escolha um Usuário");
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Escolha uma Senha");

        JButton btnCadastrar = createStyledButton("Criar Conta", new Color(76, 175, 80));
        JButton btnVoltar = new JButton("Voltar ao Login");
        btnVoltar.setBorderPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setForeground(Color.GRAY);

        gbc.gridy = 0; panel.add(title, gbc);
        gbc.gridy = 1; panel.add(Box.createVerticalStrut(20), gbc);
        gbc.gridy = 2; panel.add(txtLogin, gbc);
        gbc.gridy = 3; panel.add(txtSenha, gbc);
        gbc.gridy = 4; panel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy = 5; panel.add(btnCadastrar, gbc);
        gbc.gridy = 6; panel.add(btnVoltar, gbc);

        btnVoltar.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));

        btnCadastrar.addActionListener(e -> {
            String login = txtLogin.getText();
            String senha = new String(txtSenha.getPassword());
            if (login.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }
            try {
                String hash = SecurityUtil.hashPassword(senha);
                Usuario novo = new Usuario(login, hash);
                usuarioDAO.salvar(novo);
                JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
                cardLayout.show(mainPanel, "LOGIN");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao criar conta. Usuário já existe?");
            }
        });
        return panel;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.putClientProperty(FlatClientProperties.STYLE, "arc: 10; margin: 8,20,8,20; borderWidth:0");
        return btn;
    }

    private void abrirSistemaPrincipal(Usuario usuario) {
        this.dispose();
        SwingUtilities.invokeLater(() -> new MainApp(usuario).setVisible(true));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Erro ao configurar LookAndFeel", e);
        }
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}