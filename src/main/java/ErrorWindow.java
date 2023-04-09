import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorWindow {
    private JFrame frame;
    private final String text;

    public ErrorWindow(String text) {
        this.text = text;
        this.build();
    }

    public void build() {
        // Create frame and set size
        this.frame = new JFrame("Error");
        this.frame.setSize(360,110);
        this.frame.setResizable(false);

        // Create the main panel and fill things in
        JPanel mainPanel = new JPanel();
        JLabel errLabel = SwingUtils.addText(text, mainPanel);
        errLabel.setVerticalAlignment(SwingConstants.CENTER);
        errLabel.setBorder(BorderFactory.createEmptyBorder(0,100 ,0,100));
        SwingUtils.addButton("Ok", mainPanel, new OkAction());

        // Add the panel to the frame
        this.frame.add(mainPanel);

        // Show the panel
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    // Close the window upon pressing ok
    private class OkAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }

    public String getText() {
        return this.text;
    }
}
