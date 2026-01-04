import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {

    /**
     * Parses a playlist URL and returns a list of media URLs.
     */
    public static List<String> parsePlaylist(String playlistUrl) {
        List<String> tracks = new ArrayList<>();
        try {
            // URI.create is safer and replaces deprecated new URL(string)
            URL url = java.net.URI.create(playlistUrl).toURL();
            try (InputStream is = url.openStream()) {
                return parsePlaylist(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tracks;
    }

    /**
     * Parses playlist content from an InputStream.
     * Useful for testing without network.
     */
    public static List<String> parsePlaylist(InputStream inputStream) {
        List<String> tracks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                tracks.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tracks;
    }
}
