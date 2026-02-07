package br.com.projeto.view;

import br.com.projeto.model.Aluno;
import br.com.projeto.model.Usuario;
import br.com.projeto.util.SecurityUtil;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class DualListSelector<T> extends JPanel {

    private DefaultListModel<T> sourceModel;
    private DefaultListModel<T> targetModel;
    private JList<T> sourceList;
    private JList<T> targetList;
    private Usuario usuarioLogado;

    public DualListSelector() {
        initComponents();
        layoutComponents();
    }

    public void setUsuarioLogado(Usuario u) { this.usuarioLogado = u; }

    private void initComponents() {
        sourceModel = new DefaultListModel<>();
        targetModel = new DefaultListModel<>();

        sourceList = createStyledList(sourceModel);
        targetList = createStyledList(targetModel);
    }

    private JList<T> createStyledList(DefaultListModel<T> model) {
        JList<T> list = new JList<>(model);
        list.setCellRenderer((ListCellRenderer<? super T>) new ModernStudentRenderer());
        list.setBackground(Color.WHITE);
        list.setSelectionBackground(new Color(239, 246, 255));
        list.setSelectionForeground(Color.BLACK);
        list.setFixedCellHeight(60);
        return list;
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.45; gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH; gbc.insets = new Insets(0, 0, 0, 0);
        add(createListPanel("Alunos Disponíveis", sourceList, new Color(249, 250, 251)), gbc);

        gbc.gridx = 1; gbc.weightx = 0.1; gbc.fill = GridBagConstraints.NONE;
        add(createButtonsPanel(), gbc);

        gbc.gridx = 2; gbc.weightx = 0.45; gbc.fill = GridBagConstraints.BOTH;
        add(createListPanel("Alunos Matriculados", targetList, new Color(240, 253, 244)), gbc);
    }

    private JPanel createListPanel(String title, JList<T> list, Color headerBg) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, new Color(229, 231, 235)));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Inter", Font.BOLD, 12));
        lblTitle.setForeground(new Color(107, 114, 128));
        lblTitle.setBorder(new EmptyBorder(15, 15, 10, 15));
        lblTitle.setOpaque(true);
        lblTitle.setBackground(headerBg);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(0, 15, 0, 15));

        JButton btnAdd = createArrowBtn(">", false);
        JButton btnRemove = createArrowBtn("<", false);
        JButton btnAddAll = createArrowBtn(">>", true);
        JButton btnRemoveAll = createArrowBtn("<<", true);

        btnAdd.addActionListener(e -> moveItems(sourceList, sourceModel, targetModel));
        btnRemove.addActionListener(e -> moveItems(targetList, targetModel, sourceModel));
        btnAddAll.addActionListener(e -> { if (checkAuth()) moveAll(sourceModel, targetModel); });
        btnRemoveAll.addActionListener(e -> { if (checkAuth()) moveAll(targetModel, sourceModel); });

        panel.add(btnAdd); panel.add(btnRemove);
        panel.add(btnAddAll); panel.add(btnRemoveAll);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(panel);
        return wrapper;
    }

    private JButton createArrowBtn(String text, boolean isDouble) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Consolas", Font.BOLD, 14));
        btn.setForeground(isDouble ? new Color(37, 99, 235) : new Color(75, 85, 99));
        btn.setBackground(new Color(243, 244, 246));
        btn.setPreferredSize(new Dimension(45, 45));
        btn.putClientProperty(FlatClientProperties.STYLE, "arc: 15; borderWidth: 0; hoverBackground: #E5E7EB");
        return btn;
    }

    public void addSourceItem(T item) { sourceModel.addElement(item); }
    public void removeSourceItem(T item) { sourceModel.removeElement(item); }
    public void repaintLists() { sourceList.repaint(); targetList.repaint(); }
    public void setTargetItems(List<T> items) { targetModel.clear(); items.forEach(targetModel::addElement); }
    public void setSourceItems(List<T> items) { sourceModel.clear(); items.forEach(sourceModel::addElement); }
    public List<T> getTargetItems() { return listToList(targetModel); }
    public List<T> getSourceItems() { return listToList(sourceModel); }
    public T getSelectedSourceItem() { return sourceList.getSelectedValue(); }

    private List<T> listToList(DefaultListModel<T> model) {
        List<T> list = new ArrayList<>();
        for(int i=0; i<model.getSize(); i++) list.add(model.getElementAt(i));
        return list;
    }

    private void moveItems(JList<T> from, DefaultListModel<T> fromM, DefaultListModel<T> toM) {
        for (T item : from.getSelectedValuesList()) { toM.addElement(item); fromM.removeElement(item); }
    }

    private void moveAll(DefaultListModel<T> fromM, DefaultListModel<T> toM) {
        for (int i = 0; i < fromM.getSize(); i++) toM.addElement(fromM.getElementAt(i));
        fromM.clear();
    }

    private boolean checkAuth() {
        if (usuarioLogado == null) return true;
        JPasswordField pf = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(this, pf, "Senha de Admin Necessária", JOptionPane.OK_CANCEL_OPTION);
        return ok == JOptionPane.OK_OPTION && SecurityUtil.checkPassword(new String(pf.getPassword()), usuarioLogado.getSenhaHash());
    }

    class ModernStudentRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JPanel panel = new JPanel(new BorderLayout(15, 0));
            panel.setBorder(new EmptyBorder(8, 10, 8, 10));
            panel.setOpaque(true);

            if (isSelected) {
                panel.setBackground(new Color(239, 246, 255));
            } else {
                panel.setBackground(Color.WHITE);
            }

            Aluno aluno = (Aluno) value;

            JLabel avatar = new JLabel(getInitials(aluno.getNome())) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(generateColor(aluno.getNome()));
                    g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));
                    super.paintComponent(g2);
                    g2.dispose();
                }
            };
            avatar.setPreferredSize(new Dimension(40, 40));
            avatar.setHorizontalAlignment(SwingConstants.CENTER);
            avatar.setForeground(Color.WHITE);
            avatar.setFont(new Font("Inter", Font.BOLD, 14));

            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            textPanel.setOpaque(false);

            JLabel lblName = new JLabel(aluno.getNome());
            lblName.setFont(new Font("Inter", Font.BOLD, 14));
            lblName.setForeground(new Color(31, 41, 55));

            JLabel lblInfo = new JLabel(aluno.getMatricula() + " • " + aluno.getEmail());
            lblInfo.setFont(new Font("Inter", Font.PLAIN, 12));
            lblInfo.setForeground(new Color(107, 114, 128));

            textPanel.add(lblName);
            textPanel.add(lblInfo);

            panel.add(avatar, BorderLayout.WEST);
            panel.add(textPanel, BorderLayout.CENTER);

            return panel;
        }

        private String getInitials(String name) {
            if (name == null || name.isEmpty()) return "?";
            String[] parts = name.split(" ");
            if (parts.length == 1) return parts[0].substring(0, 1).toUpperCase();
            return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
        }

        private Color generateColor(String str) {
            int hash = str.hashCode();
            int r = (hash & 0xFF0000) >> 16;
            int g = (hash & 0x00FF00) >> 8;
            int b = hash & 0x0000FF;
            return new Color((r + 100) / 2, (g + 100) / 2, (b + 100) / 2);
        }
    }
}