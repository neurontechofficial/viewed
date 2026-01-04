import javax.swing.*;
import java.awt.*;

public class AlbumArtPanel extends JPanel {

    private final JLabel artLabel;
    private final JLabel titleLabel;
    private final JLabel detailsLabel;

    public AlbumArtPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);

        // Art Center
        artLabel = new JLabel();
        artLabel.setHorizontalAlignment(SwingConstants.CENTER);
        artLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(artLabel, BorderLayout.CENTER);

        // Metadata Bottom
        JPanel metaPanel = new JPanel();
        metaPanel.setLayout(new BoxLayout(metaPanel, BoxLayout.Y_AXIS));
        metaPanel.setBackground(Color.BLACK);
        metaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        titleLabel = new JLabel(" ");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailsLabel = new JLabel(" ");
        detailsLabel.setForeground(Color.LIGHT_GRAY);
        detailsLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        detailsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        metaPanel.add(titleLabel);
        metaPanel.add(Box.createVerticalStrut(5));
        metaPanel.add(detailsLabel);

        add(metaPanel, BorderLayout.SOUTH);
    }

    public void setMediaInfo(AlbumArtLoader.MediaInfo info) {
        if (info == null) {
            artLabel.setIcon(null);
            artLabel.setText("No Media Info");
            titleLabel.setText(" ");
            detailsLabel.setText(" ");
            return;
        }

        if (info.artwork != null) {
            artLabel.setIcon(info.artwork);
            artLabel.setText("");
        } else {
            artLabel.setIcon(null);
            artLabel.setText("No Artwork");
            artLabel.setForeground(Color.GRAY);
        }

        titleLabel.setText(info.title != null && !info.title.isEmpty() ? info.title : "Unknown Title");
        String artist = info.artist != null && !info.artist.isEmpty() ? info.artist : "Unknown Artist";
        String album = info.album != null && !info.album.isEmpty() ? info.album : "Unknown Album";
        detailsLabel.setText(artist + " - " + album);
    }
}
