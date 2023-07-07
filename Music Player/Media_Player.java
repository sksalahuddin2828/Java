import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MediaPlayer implements ActionListener {
    private JFrame frame;
    private JFileChooser fileChooser;
    private DefaultListModel<String> playlistModel;
    private JList<String> playlist;
    private JButton selectButton;
    private JButton playPauseButton;
    private JButton stopButton;
    private JButton rewindButton;
    private JButton fastForwardButton;
    private JButton removeButton;
    private JButton clearButton;
    private JLabel playlistLabel;
    private JLabel volumeLabel;
    private JSlider volumeSlider;
    private JLabel statusLabel;

    private List<String> playlistPaths;
    private int currentIndex;
    private Clip currentClip;
    private boolean isPlaying;
    private float volume;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MediaPlayer());
    }

    public MediaPlayer() {
        playlistPaths = new ArrayList<>();
        currentIndex = 0;
        isPlaying = false;
        volume = 0.5f;

        frame = new JFrame("Media Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new MediaFileFilter());

        playlistModel = new DefaultListModel<>();
        playlist = new JList<>(playlistModel);
        playlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playlist.setBackground(Color.decode("#3498db"));
        playlist.setForeground(Color.WHITE);
        playlist.addListSelectionListener(e -> {
            int selectedIndex = playlist.getSelectedIndex();
            if (selectedIndex != -1 && selectedIndex != currentIndex) {
                currentIndex = selectedIndex;
                stopMusic();
                playMusic();
            }
        });

        selectButton = new JButton("Select File");
        selectButton.addActionListener(this);

        playPauseButton = new JButton("Play");
        playPauseButton.addActionListener(this);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);

        rewindButton = new JButton("Rewind");
        rewindButton.addActionListener(this);

        fastForwardButton = new JButton("Fast Forward");
        fastForwardButton.addActionListener(this);

        removeButton = new JButton("Remove");
        removeButton.addActionListener(this);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);

        playlistLabel = new JLabel("Playlist");

        volumeLabel = new JLabel("Volume");

        volumeSlider = new JSlider(0, 100);
        volumeSlider.setValue((int) (volume * 100));
        volumeSlider.addChangeListener(e -> {
            float newVolume = volumeSlider.getValue() / 100f;
            setVolume(newVolume);
        });

        statusLabel = new JLabel("", SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        buttonPanel.add(selectButton);
        buttonPanel.add(playPauseButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(rewindButton);
        buttonPanel.add(fastForwardButton);

        JPanel playlistPanel = new JPanel(new BorderLayout());
        playlistPanel.add(playlistLabel, BorderLayout.NORTH);
        playlistPanel.add(new JScrollPane(playlist), BorderLayout.CENTER);
        playlistPanel.add(removeButton, BorderLayout.WEST);
        playlistPanel.add(clearButton, BorderLayout.SOUTH);

        JPanel volumePanel = new JPanel(new BorderLayout());
        volumePanel.add(volumeLabel, BorderLayout.NORTH);
        volumePanel.add(volumeSlider, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.CENTER);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        frame.add(buttonPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        frame.add(playlistPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 0.0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        frame.add(volumePanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        frame.add(statusPanel, constraints);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectButton) {
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                addToPlaylist(selectedFile.getAbsolutePath());
            }
        } else if (e.getSource() == playPauseButton) {
            if (isPlaying) {
                pauseResumeMusic();
            } else {
                playMusic();
            }
        } else if (e.getSource() == stopButton) {
            stopMusic();
        } else if (e.getSource() == rewindButton) {
            rewindMusic();
        } else if (e.getSource() == fastForwardButton) {
            fastForwardMusic(10);
        } else if (e.getSource() == removeButton) {
            int selectedIndex = playlist.getSelectedIndex();
            if (selectedIndex != -1) {
                removeFromPlaylist(selectedIndex);
            }
        } else if (e.getSource() == clearButton) {
            clearPlaylist();
        }
    }

    private void addToPlaylist(String filePath) {
        playlistPaths.add(filePath);
        playlistModel.addElement(new File(filePath).getName());
    }

    private void removeFromPlaylist(int index) {
        playlistPaths.remove(index);
        playlistModel.remove(index);
        if (index < currentIndex) {
            currentIndex--;
        } else if (index == currentIndex) {
            stopMusic();
        }
    }

    private void clearPlaylist() {
        playlistPaths.clear();
        playlistModel.clear();
        stopMusic();
        currentIndex = 0;
    }

    private void playMusic() {
        if (playlistPaths.isEmpty()) {
            showMessage("Error", "No file selected.");
            return;
        }

        try {
            File file = new File(playlistPaths.get(currentIndex));
            currentClip = AudioSystem.getClip();
            currentClip.open(AudioSystem.getAudioInputStream(file));
            currentClip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP && !currentClip.isRunning()) {
                    isPlaying = false;
                    playPauseButton.setText("Play");
                    playlist.clearSelection();
                    currentIndex = (currentIndex + 1) % playlistPaths.size();
                    playMusic();
                }
            });
            currentClip.start();
            isPlaying = true;
            playPauseButton.setText("Pause");
            statusLabel.setText("Now playing: " + file.getName());
        } catch (Exception e) {
            showMessage("Error", "Failed to play the selected file.");
        }
    }

    private void pauseResumeMusic() {
        if (isPlaying) {
            currentClip.stop();
            isPlaying = false;
            playPauseButton.setText("Resume");
        } else {
            currentClip.start();
            isPlaying = true;
            playPauseButton.setText("Pause");
        }
    }

    private void stopMusic() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
            isPlaying = false;            
            playPauseButton.setText("Play");
            statusLabel.setText("");
            playlist.clearSelection();
        }
    }

    private void rewindMusic() {
        if (currentClip != null) {
            currentClip.setMicrosecondPosition(0);
        }
    }

    private void fastForwardMusic(int seconds) {
        if (currentClip != null) {
            long currentPosition = currentClip.getMicrosecondPosition();
            long newPosition = currentPosition + (seconds * 1000000L);
            long clipLength = currentClip.getMicrosecondLength();
            if (newPosition > clipLength) {
                newPosition = clipLength;
            }
            currentClip.setMicrosecondPosition(newPosition);
        }
    }

    private void setVolume(float volume) {
        if (currentClip != null) {
            try {
                FloatControl gainControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
                this.volume = volume;
            } catch (IllegalArgumentException e) {
                showMessage("Error", "Failed to set the volume.");
            }
        }
    }

    private void showMessage(String title, String message) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private class MediaFileFilter extends javax.swing.filechooser.FileFilter {
        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }
            String name = file.getName().toLowerCase();
            return name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".ogg") ||
                    name.endsWith(".mp4") || name.endsWith(".avi") || name.endsWith(".mkv") ||
                    name.endsWith(".flv") || name.endsWith(".mov") || name.endsWith(".wmv") ||
                    name.endsWith(".webm");
        }

        @Override
        public String getDescription() {
            return "Audio/Video Files (*.mp3, *.wav, *.ogg, *.mp4, *.avi, *.mkv, *.flv, *.mov, *.wmv, *.webm)";
        }
    }
}
