import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class ColorGame extends JFrame implements ActionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private static final String[] COLORS = {"Red", "Orange", "White", "Black", "Green", "Blue", "Brown", "Purple", "Cyan", "Yellow", "Pink", "Magenta"};
    private static final int TIMER_DURATION = 60;

    private Timer timer;
    private int score;
    private String displayedWordColor;

    private JLabel gameScore;
    private JLabel displayWords;
    private JLabel timeLeft;
    private JTextField colorEntry;
    private JButton startButton;
    private JButton resetButton;

    public ColorGame() {
        setTitle("Color Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        Font appFont = new Font("Helvetica", Font.PLAIN, 12);

        String gameDescription = "Game Description: Enter the color of the words displayed below.\nAnd keep in mind not to enter the word text itself";
        JLabel gameDescriptionLabel = new JLabel(gameDescription);
        gameDescriptionLabel.setFont(appFont);
        gameDescriptionLabel.setForeground(Color.GRAY);
        add(gameDescriptionLabel);

        score = 0;
        gameScore = new JLabel("Your Score: " + score);
        gameScore.setFont(new Font("Arial", Font.PLAIN, 16));
        gameScore.setForeground(Color.GREEN);
        add(gameScore);

        displayedWordColor = "";

        displayWords = new JLabel();
        displayWords.setFont(new Font("Arial", Font.PLAIN, 28));
        displayWords.setOpaque(true);
        displayWords.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        displayWords.setPreferredSize(new Dimension(300, 60));
        add(displayWords);

        timeLeft = new JLabel("Game Ends in: -");
        timeLeft.setFont(new Font("Arial", Font.PLAIN, 14));
        timeLeft.setForeground(Color.ORANGE);
        add(timeLeft);

        colorEntry = new JTextField(30);
        colorEntry.addKeyListener(this);
        add(colorEntry);

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.setFocusable(false);
        startButton.setForeground(Color.BLACK);
        startButton.setBackground(Color.PINK);
        startButton.setPreferredSize(new Dimension(120, 40));
        add(startButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setFocusable(false);
        resetButton.setForeground(Color.BLACK);
        resetButton.setBackground(Color.LIGHT_GRAY);
        resetButton.setPreferredSize(new Dimension(120, 40));
        resetButton.setEnabled(false);
        add(resetButton);
    }

    public void startGame() {
        if (timer == null) {
            timer = new Timer(1000, new ActionListener() {
                int countdown = TIMER_DURATION;

                public void actionPerformed(ActionEvent e) {
                    countdown--;
                    timeLeft.setText("Game Ends in: " + countdown + "s");

                    if (countdown == 0) {
                        stopGame();
                    }
                }
            });
        }
        timer.start();
        startButton.setEnabled(false);
        resetButton.setEnabled(true);
        nextWord();
    }

    public void stopGame() {
        timer.stop();
        startButton.setEnabled(true);
        resetButton.setEnabled(false);
        colorEntry.setText("");
        displayWords.setText("");
    }

    public void nextWord() {
        if (timer.isRunning()) {
            displayedWordColor = COLORS[new Random().nextInt(COLORS.length)];
            String displayedWordText = COLORS[new Random().nextInt(COLORS.length)];
            displayWords.setText(displayedWordText);
            displayWords.setForeground(getColor(displayedWordColor));
            colorEntry.setText("");
            colorEntry.requestFocus();
        }
    }

    public void checkWord() {
        if (timer.isRunning()) {
            String inputColor = colorEntry.getText().trim().toLowerCase();
            if (inputColor.equals(displayedWordColor.toLowerCase())) {
                score++;
                gameScore.setText("Your Score: " + score);
            }
            nextWord();
        }
    }

    private Color getColor(String colorName) {
        try {
            return (Color) Color.class.getField(colorName.toLowerCase()).get(null);
        } catch (Exception e) {
            return Color.BLACK;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startGame();
        } else if (e.getSource() == resetButton) {
            stopGame();
            score = 0;
            gameScore.setText("Your Score: " + score);
            timeLeft.setText("Game Ends in: -");
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            checkWord();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ColorGame().setVisible(true);
            }
        });
    }
}
