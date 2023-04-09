import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogURLHandlerTest {
    String[] nullURLs = {
            "",
            "https://www.youtube.com/",
            "https://www.google.com/",
            "http://logs.tf/",
            "https://log.tf/3386921"
    };

    String[] goodURLs = {
            "https://logs.tf/3386916#76561197970669109",
            "https://logs.tf/3386911#76561197970669109",
            "https://logs.tf/3386907#76561197970669109",
            "https://logs.tf/3386922",
            "https://logs.tf/3386921/",
            "https://logs.tf/3386920"
    };

    String[] goodIDs = {
            "3386916",
            "3386911",
            "3386907",
            "3386922",
            "3386921",
            "3386920"
    };

    @Test
    public void nullURLs() {
        assertEquals(LogURLHandler.getIDs(nullURLs).length, 0);
    }

    @Test
    public void goodURLs() {
        String[] ids = LogURLHandler.getIDs(goodURLs);
        assertArrayEquals(ids, goodIDs);
    }
}