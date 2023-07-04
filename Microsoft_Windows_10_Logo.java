import java.awt.Color;

import javax.swing.JFrame;

public class TurtleGraphics {
    public static void main(String[] args) {
        Turtle turtle = new Turtle();

        turtle.setPenSpeed(1);
        turtle.setScreenColor(Color.BLACK);
        turtle.penUp();
        turtle.goTo(-50, 60);
        turtle.penDown();
        turtle.setColor(new Color(0, 173, 239));
        turtle.beginFill();
        turtle.goTo(100, 100);
        turtle.goTo(100, -100);
        turtle.goTo(-50, -60);
        turtle.goTo(-50, 60);
        turtle.endFill();
        turtle.setColor(Color.BLACK);
        turtle.goTo(15, 100);
        turtle.setColor(Color.BLACK);
        turtle.setWidth(10);
        turtle.goTo(15, -100);
        turtle.penUp();
        turtle.goTo(100, 0);
        turtle.penDown();
        turtle.goTo(-100, 0);

        JFrame frame = new JFrame();
        frame.add(turtle);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
