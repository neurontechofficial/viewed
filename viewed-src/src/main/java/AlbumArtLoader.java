import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import javax.imageio.ImageIO;

public class AlbumArtLoader {

    public static class MediaInfo {
        public ImageIcon artwork;
        public String title;
        public String artist;
        public String album;

        public MediaInfo(ImageIcon artwork, String title, String artist, String album) {
            this.artwork = artwork;
            this.title = title;
            this.artist = artist;
            this.album = album;
        }
    }

    public static MediaInfo loadMediaInfo(File mp3File) {
        ImageIcon icon = null;
        String title = mp3File.getName();
        String artist = "Unknown Artist";
        String album = "Unknown Album";

        try {
            AudioFile f = AudioFileIO.read(mp3File);
            Tag tag = f.getTag();

            if (tag != null) {
                // Text Metadata
                if (tag.hasField(FieldKey.TITLE))
                    title = tag.getFirst(FieldKey.TITLE);
                if (tag.hasField(FieldKey.ARTIST))
                    artist = tag.getFirst(FieldKey.ARTIST);
                if (tag.hasField(FieldKey.ALBUM))
                    album = tag.getFirst(FieldKey.ALBUM);

                // Artwork
                Artwork art = tag.getFirstArtwork();
                if (art != null) {
                    byte[] data = art.getBinaryData();
                    ByteArrayInputStream bis = new ByteArrayInputStream(data);
                    BufferedImage img = ImageIO.read(bis);
                    if (img != null) {
                        Image scaled = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(scaled);
                    }
                }
            }
        } catch (Exception e) {
            // Fallback to searching for local images if tagging fails or no tag
        }

        if (icon == null) {
            icon = loadLocalImage(mp3File);
        }

        return new MediaInfo(icon, title, artist, album);
    }

    private static ImageIcon loadLocalImage(File mp3File) {
        try {
            String base = mp3File.getAbsolutePath().replaceAll("\\.mp3$", "");
            File jpg = new File(base + ".jpg");
            File png = new File(base + ".png");

            File imageFile = jpg.exists() ? jpg : (png.exists() ? png : null);
            if (imageFile != null) {
                BufferedImage img = ImageIO.read(imageFile);
                Image scaled = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
