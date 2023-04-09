import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SwingUtils {
    public static JLabel addText(String text, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Sans Serif", Font.BOLD, 16));
        panel.add(label);

        return label;
    }

    public JLabel addText(String text, JPanel panel, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setFont(new Font("Sans Serif", Font.BOLD, 16));
        panel.add(label);

        return label;
    }

    public static JButton addButton(String text, JPanel panel, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Sans Serif", Font.BOLD, 16));
        button.addActionListener(action);
        panel.add(button);

        return button;
    }

    public static JTextField addTextField(String text, int size, JPanel panel) {
        JTextField field = new JTextField(size);
        field.setText(text);
        field.setFont(new Font("Sans Serif", Font.PLAIN, 16));
        panel.add(field);

        return field;
    }
}
