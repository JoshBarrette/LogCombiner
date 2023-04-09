import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainWindowTest {
    private final String APIKey = ""; // Find API key at https://logs.tf/uploader
    private final String[] goodURLs = {"https://logs.tf/3393320", "https://logs.tf/3393334"};
    private final String[] nonUniqueURLs = {goodURLs[0], goodURLs[0]};

    @Test
    public void invalidAPIKey() {
        MainWindow win = new MainWindow();
        win.build();

        win.setLogName("test");
        win.setMapName("map");
        win.setLogURLS(goodURLs);
        win.setAPIKey("APIKey");

        win.pressCombine();

        assertEquals("Invalid API key", win.getErrorWindow().getText());
    }

    @Test
    public void logNameMissing() {
        MainWindow win = new MainWindow();
        win.build();

        win.pressCombine();

        assertEquals("Please provide a name", win.getErrorWindow().getText());
    }

    @Test
    public void logMapMissing() {
        MainWindow win = new MainWindow();
        win.build();

        win.setLogName("name");

        win.pressCombine();

        assertEquals("Please provide a map", win.getErrorWindow().getText());
    }

    @Test
    public void missingAPIKey() {
        MainWindow win = new MainWindow();
        win.build();

        win.setLogName("test");
        win.setMapName("map");

        win.pressCombine();

        assertEquals("Please provide an API key", win.getErrorWindow().getText());
    }

    @Test
    public void nonUniqueLogs() {
        MainWindow win = new MainWindow();
        win.build();

        win.setLogName("test");
        win.setMapName("map");
        win.setLogURLS(nonUniqueURLs);
        win.setAPIKey(APIKey);

        win.pressCombine();

        assertEquals("Please provide more than one unique log", win.getErrorWindow().getText());
    }
}