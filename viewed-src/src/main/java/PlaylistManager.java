import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlaylistManager {

    /**
     * Parses a playlist URL and returns a list of media URLs.
     * Currently supports basic M3U/PLS format where each line is a URL or a
     * comment.
     */
    public static List<String> parsePlaylist(String playlistUrl) {
        List<String> tracks = new ArrayList<>();
        try {
            URL url = new URL(playlistUrl);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    tracks.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tracks;
    }
}
