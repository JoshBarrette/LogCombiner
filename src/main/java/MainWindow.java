import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainWindow {
    // Visual elements
    private JPanel mainPanel;
    private JTextField logNameField;
    private JTextField logMapField;
    private JTextField APIKey;
    private JTextField[] textFields;
    private JButton combineButton;
    private JButton resetButton;

    // The object that holds the data from the response
    private LogsResponse response;

    // Error and success windows. Mainly used for testing
    private ErrorWindow errorWindow;
    private SuccessWindow successWindow;

    public MainWindow() {
        this.build();
    }

    public void build() {
        // Create frame and set size
        JFrame frame = new JFrame("Log Combiner");
        frame.setSize(450,320);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel and fill things in
        this.mainPanel = new JPanel();
        JLabel title = SwingUtils.addText("Combine some logs", this.mainPanel);
        title.setBorder(BorderFactory.createEmptyBorder(0,150,0,150));
        this.textFields = new JTextField[4];
        this.createTextFields();
        this.createButtons();

        // Add the panel to the frame
        frame.add(this.mainPanel);

        // Show the panel
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createTextFields() {
        JTextField field;
        JLabel text;

        text = SwingUtils.addText("Log name:", this.mainPanel);
        text.setBorder(BorderFactory.createEmptyBorder(0,1 ,0,1));
        this.logNameField =SwingUtils.addTextField("", 25, this.mainPanel);

        SwingUtils.addText("Map name:", this.mainPanel);
        this.logMapField = SwingUtils.addTextField("", 25, this.mainPanel);

        for (int i = 0; i < 4; i++) {
            text = SwingUtils.addText("Log #" + (i + 1) + ": ", this.mainPanel);
            text.setBorder(BorderFactory.createEmptyBorder(0,11 ,0,11));
            field = SwingUtils.addTextField("", 25, this.mainPanel);

            textFields[i] = field;
        }

        text = SwingUtils.addText("API Key:", this.mainPanel);
        text.setBorder(BorderFactory.createEmptyBorder(0,9 ,0,9));
        this.APIKey = SwingUtils.addTextField("", 25, this.mainPanel);
    }

    private void createButtons() {
        this.combineButton = SwingUtils.addButton("Combine", this.mainPanel, new CombineAction());
        this.resetButton = SwingUtils.addButton("Reset", this.mainPanel, new ResetAction());
    }

    // Reset the text boxes into empty strings
    private class ResetAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            logNameField.setText("");
            logMapField.setText("");
            for (JTextField field: textFields) {
                field.setText("");
            }
            APIKey.setText("");
        }
    }

    // Pull the data from the text boxes and send it to the combiner object and comine
    private class CombineAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File logFile;
            String logName = logNameField.getText();
            String logMap = logMapField.getText();
            String APIKeyString = APIKey.getText();
            String[] urls = getTextFieldData();
            String[] ids = LogURLHandler.getIDs(urls);

            if (!checkForErrors(logName, logMap, APIKeyString, ids)) {
                return;
            }

            try {
                logFile = LogFileHandler.combine(ids, "combined");
            } catch (IOException ex) {
                errorWindow = new ErrorWindow("Error when combining log files");
                LogFileHandler.deleteFolder("./temp");
                return;
            }

            try {
                response = LogUploader.uploadLog(logFile, logName, logMap, APIKeyString);
            } catch (IOException ex) {
                errorWindow = new ErrorWindow("Error when uploading logs");
                LogFileHandler.deleteFolder("./temp");
                return;
            }

            if (response.isSuccess()) {
                successWindow = new SuccessWindow("Success", "logs.tf/" + response.getLog_id());
            } else {
                if (response.getError().contains("Log already exists")) {
                    successWindow = new SuccessWindow("Log Already exists", "logs.tf/" + response.getLog_id());
                } else if (response.getError().contains("Invalid API key")) {
                    errorWindow = new ErrorWindow(response.getError());
                } else {
                    errorWindow = new ErrorWindow(response.getError());
                }
            }
            LogFileHandler.deleteFolder("./temp");
        }
    }

    // Check for invalid inputs in the text boxes
    private boolean checkForErrors(String logName, String logMap, String APIKeyString, String[] ids) {
        if (logName.length() < 1) {
            errorWindow = new ErrorWindow("Please provide a name");
            return false;
        } else if (logName.length() > 40) {
            errorWindow = new ErrorWindow("Name must not exceed 40 characters");
            return false;
        } else if (logMap.length() < 1) {
            errorWindow = new ErrorWindow("Please provide a map");
            return false;
        } else if (logMap.length() > 24) {
            errorWindow = new ErrorWindow("Map must not exceed 24 characters");
            return false;
        } else if (APIKeyString.length() < 1) {
            errorWindow = new ErrorWindow("Please provide an API key");
            return false;
        } else if (ids.length < 2) {
            errorWindow = new ErrorWindow("Please provide more than one unique log");
            return false;
        }

        return true;
    }

    // Put the log URLs into an array for easier processing
    private String[] getTextFieldData() {
        String[] data = new String[4];
        for (int i = 0; i < 4; i++) {
            data[i] = this.textFields[i].getText();
        }
        return data;
    }

    // Getters and setters for testing ------------------------------------

    public void setLogURLS(String[] urls) {
        int index = 0;
        while (index < urls.length && index < 4) {
            this.textFields[index].setText(urls[index]);
            index++;
        }
    }

    public void setLogName(String name) {
        this.logNameField.setText(name);
    }

    public void setMapName(String name) {
        this.logMapField.setText(name);
    }

    public void setAPIKey(String key) {
        this.APIKey.setText(key);
    }

    public LogsResponse getResponse() {
        return this.response;
    }

    public void pressCombine() {
        this.combineButton.doClick();
    }

    public void pressReset() {
        this.resetButton.doClick();
    }

    public SuccessWindow getSuccessWindow() {
        return this.successWindow;
    }

    public ErrorWindow getErrorWindow() {
        return this.errorWindow;
    }
}
