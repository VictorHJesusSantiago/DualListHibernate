package br.com.projeto.view;

import br.com.projeto.controller.LoginController;
import br.com.projeto.model.Usuario;
import br.com.projeto.MainApp;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginView extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cards;
    private final LoginController controller;

    public LoginView() {
        this.controller = new LoginController();

        setTitle("Acesso ao Sistema");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel mainContainer = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(59, 130, 246), getWidth(), getHeight(), new Color(147, 51, 234));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setOpaque(false);

        cards.add(createAuthPanel(true), "LOGIN");
        cards.add(createAuthPanel(false), "REGISTER");

        mainContainer.add(cards);
        add(mainContainer);
    }

    private JPanel createAuthPanel(boolean isLogin) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(380, 480));

        panel.putClientProperty(FlatClientProperties.STYLE, "arc: 20; border: 1,1,1,1, #E5E7EB");
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.weightx = 1;

        JLabel lblTitle = new JLabel(isLogin ? "Bem-vindo" : "Criar Conta");
        lblTitle.setFont(new Font("Inter", Font.BOLD, 28));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(31, 41, 55));

        JLabel lblSub = new JLabel(isLogin ? "Faça login para continuar" : "Preencha os dados abaixo");
        lblSub.setFont(new Font("Inter", Font.PLAIN, 14));
        lblSub.setForeground(new Color(107, 114, 128));
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField txtUser = new JTextField();
        txtUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Usuário");
        txtUser.putClientProperty(FlatClientProperties.STYLE, "arc: 10; margin: 5,10,5,10");
        txtUser.setPreferredSize(new Dimension(0, 40));

        JPasswordField txtPass = new JPasswordField();
        txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Senha");
        txtPass.putClientProperty(FlatClientProperties.STYLE, "arc: 10; margin: 5,10,5,10; showRevealButton: true");
        txtPass.setPreferredSize(new Dimension(0, 40));

        JButton btnAction = new JButton(isLogin ? "Entrar" : "Cadastrar");
        btnAction.setBackground(new Color(37, 99, 235));
        btnAction.setForeground(Color.WHITE);
        btnAction.setFont(new Font("Inter", Font.BOLD, 15));
        btnAction.putClientProperty(FlatClientProperties.STYLE, "arc: 10; borderWidth: 0");
        btnAction.setPreferredSize(new Dimension(0, 45));

        JButton btnSwitch = new JButton(isLogin ? "Não tem conta? Cadastre-se" : "Já tem conta? Entrar");
        btnSwitch.setFont(new Font("Inter", Font.PLAIN, 13));
        btnSwitch.setForeground(new Color(75, 85, 99));
        btnSwitch.setContentAreaFilled(false);
        btnSwitch.setBorderPainted(false);
        btnSwitch.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 0; panel.add(lblTitle, gbc);
        gbc.gridy = 1; panel.add(lblSub, gbc);
        gbc.gridy = 2; panel.add(Box.createVerticalStrut(30), gbc);
        gbc.gridy = 3; panel.add(new JLabel("Usuário"), gbc);
        gbc.gridy = 4; panel.add(txtUser, gbc);
        gbc.gridy = 5; panel.add(Box.createVerticalStrut(15), gbc);
        gbc.gridy = 6; panel.add(new JLabel("Senha"), gbc);
        gbc.gridy = 7; panel.add(txtPass, gbc);
        gbc.gridy = 8; panel.add(Box.createVerticalStrut(25), gbc);
        gbc.gridy = 9; panel.add(btnAction, gbc);
        gbc.gridy = 10; panel.add(Box.createVerticalStrut(10), gbc);
        gbc.gridy = 11; panel.add(btnSwitch, gbc);

        // Eventos
        btnSwitch.addActionListener(e -> cardLayout.show(cards, isLogin ? "REGISTER" : "LOGIN"));

        btnAction.addActionListener(e -> {
            String u = txtUser.getText();
            String p = new String(txtPass.getPassword());
            if (isLogin) {
                Usuario user = controller.autenticar(u, p);
                if (user != null) {
                    dispose();
                    SwingUtilities.invokeLater(() -> new MainApp(user).setVisible(true));
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciais inválidas", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                try {
                    controller.registrarUsuario(u, p);
                    JOptionPane.showMessageDialog(this, "Conta criada! Faça login.");
                    cardLayout.show(cards, "LOGIN");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
                }
            }
        });

        return panel;
    }
}