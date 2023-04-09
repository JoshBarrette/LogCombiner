import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuccessWindow {
    private JFrame frame;
    private final String text;
    private final String url;

    public SuccessWindow(String text, String url) {
        this.text = text;
        this.url = url;
        this.build();
    }

    public void build() {
        // Create frame and set size
        this.frame = new JFrame("Success");
        this.frame.setSize(360,140);
        this.frame.setResizable(false);

        // Create the main panel and fill things in
        JPanel mainPanel = new JPanel();
        JLabel textLabel = SwingUtils.addText(text, mainPanel);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);
        textLabel.setBorder(BorderFactory.createEmptyBorder(0,100 ,0,100));
        JTextField copyURL = SwingUtils.addTextField(url, 25, mainPanel);
        copyURL.setHorizontalAlignment(JTextField.CENTER);
        copyURL.setEditable(false);
        SwingUtils.addButton("Close", mainPanel, new CloseAction());
        SwingUtils.addButton("Copy", mainPanel, new CopyAction());

        // Add the panel to the frame
        this.frame.add(mainPanel);

        // Show the panel
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    // Close the window upon pressing close
    private class CloseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }

    // Copy the log URL to clipboard upon pressing copy
    private class CopyAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringSelection stringSelection = new StringSelection(url);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }

    public String getText() {
        return this.text;
    }

    public String getURL() {
        return this.url;
    }
}
