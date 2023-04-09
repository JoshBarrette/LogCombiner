import java.util.ArrayList;

public class LogURLHandler {

    private static String getIDFromURL(String url) {
        if (!url.contains("logs.tf/")) {
            return null;
        }

        try {
            String id = url.replaceAll(".*tf/", "").split("#")[0].split("/")[0];
            if (id.length() == 0) {
                return null;
            }
            return id;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public static String[] getIDs(String[] urls) {
        ArrayList<String> ids = new ArrayList<>();
        String id;
        for (String s : urls) {
            id = getIDFromURL(s);
            if (id != null && !ids.contains(id)) {
                ids.add(id);
            }
        }
        return ids.toArray(new String[0]);
    }
}
