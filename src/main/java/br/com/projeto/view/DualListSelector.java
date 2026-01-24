package br.com.projeto.view;

import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class DualListSelector<T> extends JPanel {

    private DefaultListModel<T> sourceModel;
    private DefaultListModel<T> targetModel;
    private JList<T> sourceList;
    private JList<T> targetList;

    // Botões customizados
    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnAddAll;
    private JButton btnRemoveAll;

    public DualListSelector() {
        initComponents();
        layoutComponents();
        initListeners();
        styleComponents();
    }

    private void initComponents() {
        sourceModel = new DefaultListModel<>();
        targetModel = new DefaultListModel<>();
        sourceList = new JList<>(sourceModel);
        targetList = new JList<>(targetModel);

        // Instanciando nossos botões desenhados manualmente
        btnAdd = new ArrowButton(ArrowButton.Type.RIGHT_SINGLE);
        btnRemove = new ArrowButton(ArrowButton.Type.LEFT_SINGLE);
        btnAddAll = new ArrowButton(ArrowButton.Type.RIGHT_DOUBLE);
        btnRemoveAll = new ArrowButton(ArrowButton.Type.LEFT_DOUBLE);
    }

    private void styleComponents() {
        setBackground(new Color(240, 242, 245));

        // Renderer customizado
        StudentRenderer renderer = new StudentRenderer();
        sourceList.setCellRenderer((ListCellRenderer<? super T>) renderer);
        targetList.setCellRenderer((ListCellRenderer<? super T>) renderer);

        sourceList.setFixedCellHeight(45);
        targetList.setFixedCellHeight(45);
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Painel Esquerdo
        JPanel leftPanel = createCardPanel("Alunos Disponíveis", sourceList, new Color(255, 255, 255));
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 20, 10, 10);
        add(leftPanel, gbc);

        // Botões Centrais
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        centerPanel.setOpaque(false);
        centerPanel.add(btnAdd);
        centerPanel.add(btnRemove);
        centerPanel.add(btnAddAll);
        centerPanel.add(btnRemoveAll);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.add(centerPanel);

        gbc.gridx = 1;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 10, 0);
        add(centerWrapper, gbc);

        // Painel Direito
        JPanel rightPanel = createCardPanel("Matriculados", targetList, new Color(245, 250, 255));
        gbc.gridx = 2;
        gbc.weightx = 0.45;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 20);
        add(rightPanel, gbc);
    }

    private JPanel createCardPanel(String title, JList<T> list, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);
        panel.putClientProperty(FlatClientProperties.STYLE, "arc: 15; border: 1,1,1,1, #e0e0e0, , 10");

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(new Color(100, 100, 100));
        lblTitle.setBorder(new EmptyBorder(15, 15, 10, 15));

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(bgColor);

        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // --- Métodos Públicos de Acesso aos Dados (CORREÇÃO AQUI) ---

    public void setTargetItems(List<T> items) {
        targetModel.clear();
        for (T item : items) {
            targetModel.addElement(item);
        }
    }

    public void setSourceItems(List<T> items) {
        sourceModel.clear();
        for (T item : items) {
            sourceModel.addElement(item);
        }
    }

    public List<T> getTargetItems() {
        List<T> items = new ArrayList<>();
        for (int i = 0; i < targetModel.getSize(); i++) {
            items.add(targetModel.getElementAt(i));
        }
        return items;
    }

    // Método que faltava para listar o que está na esquerda (para o MainApp atualizar a lista)
    public List<T> getSourceItems() {
        List<T> items = new ArrayList<>();
        for (int i = 0; i < sourceModel.getSize(); i++) {
            items.add(sourceModel.getElementAt(i));
        }
        return items;
    }

    // Método que faltava para pegar o item selecionado para Edição/Exclusão
    public T getSelectedSourceItem() {
        return sourceList.getSelectedValue();
    }

    // --- Classes Internas (Renderer e Icon) ---
    // (Mantido igual ao anterior, omitido para economizar espaço, mas deve estar presente)
    class StudentRenderer extends DefaultListCellRenderer {
        private final Color SELECTION_BG = new Color(220, 240, 255);
        private final Icon USER_ICON = new UserIcon(18, new Color(150, 150, 150));
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setIcon(USER_ICON);
            label.setIconTextGap(15);
            label.setBorder(new EmptyBorder(0, 15, 0, 10));
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            if (isSelected) {
                label.setBackground(SELECTION_BG);
                label.setForeground(new Color(0, 80, 200));
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            } else {
                label.setBackground(list.getBackground());
                label.setForeground(new Color(60, 60, 60));
            }
            return label;
        }
    }

    class UserIcon implements Icon {
        private int size;
        private Color color;
        public UserIcon(int size, Color color) { this.size = size; this.color = color; }
        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
        @Override public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillOval(x + size/4, y, size/2, size/2);
            g2.fillArc(x, y + size/2, size, size, 0, 180);
            g2.dispose();
        }
    }

    static class ArrowButton extends JButton {
        enum Type { RIGHT_SINGLE, LEFT_SINGLE, RIGHT_DOUBLE, LEFT_DOUBLE }
        private final Type type;
        public ArrowButton(Type type) {
            this.type = type;
            setPreferredSize(new Dimension(40, 40));
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            putClientProperty(FlatClientProperties.STYLE, "arc: 10; background: #ffffff; borderColor: #d0d0d0; hoverBackground: #eef5ff");
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth(); int h = getHeight(); int cx = w / 2; int cy = h / 2;
            g2.setColor(new Color(80, 80, 80));
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int size = 6; int gap = 4;
            switch (type) {
                case RIGHT_SINGLE: drawChevron(g2, cx - 2, cy, size, 1); break;
                case LEFT_SINGLE:  drawChevron(g2, cx + 2, cy, size, -1); break;
                case RIGHT_DOUBLE: drawChevron(g2, cx - 2 - gap/2, cy, size, 1); drawChevron(g2, cx - 2 + gap, cy, size, 1); break;
                case LEFT_DOUBLE: drawChevron(g2, cx + 2 - gap, cy, size, -1); drawChevron(g2, cx + 2 + gap/2, cy, size, -1); break;
            }
            g2.dispose();
        }
        private void drawChevron(Graphics2D g2, int x, int y, int size, int direction) {
            Path2D path = new Path2D.Double();
            path.moveTo(x - (size * direction), y - size);
            path.lineTo(x, y);
            path.lineTo(x - (size * direction), y + size);
            g2.draw(path);
        }
    }

    private void initListeners() {
        btnAdd.addActionListener(e -> moveItems(sourceList, sourceModel, targetModel));
        btnRemove.addActionListener(e -> moveItems(targetList, targetModel, sourceModel));
        btnAddAll.addActionListener(e -> moveAll(sourceModel, targetModel));
        btnRemoveAll.addActionListener(e -> moveAll(targetModel, sourceModel));
    }

    private void moveItems(JList<T> fromList, DefaultListModel<T> fromModel, DefaultListModel<T> toModel) {
        List<T> selectedValues = fromList.getSelectedValuesList();
        if (selectedValues.isEmpty()) return;
        for (T item : selectedValues) {
            toModel.addElement(item);
            fromModel.removeElement(item);
        }
    }

    private void moveAll(DefaultListModel<T> fromModel, DefaultListModel<T> toModel) {
        for (int i = 0; i < fromModel.getSize(); i++) {
            toModel.addElement(fromModel.getElementAt(i));
        }
        fromModel.clear();
    }
}