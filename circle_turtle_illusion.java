import processing.core.PApplet;

public class CircleDanceAnimation extends PApplet {
    private int population;
    private int resolution;
    private int loops;
    private int flip;
    private int lines;
    private float radius;
    private Turtle[] turtles;

    public static void main(String[] args) {
        PApplet.main("CircleDanceAnimation");
    }

    public void settings() {
        size(800, 600);
    }

    public void setup() {
        population = args.length > 0 ? Integer.parseInt(args[0]) : 11;
        resolution = args.length > 1 ? Integer.parseInt(args[1]) : 480;
        loops = args.length > 2 ? Integer.parseInt(args[2]) : 1;
        flip = args.length > 3 ? Integer.parseInt(args[3]) : 0;
        lines = args.length > 4 ? Integer.parseInt(args[4]) : 0;
        radius = 250;
        surface.setTitle("Circle Dance Animation");
        noLoop();

        turtles = new Turtle[population];
        for (int i = 0; i < population; i++) {
            turtles[i] = new Turtle(i, population);
        }
    }

    public void draw() {
        background(0);
        if (lines == 1) {
            arrangeLines();
        }
        drawDancers();
        loop();
    }

    private void arrangeLines() {
        push();
        stroke(255);
        for (int n = 0; n < population; n++) {
            float angle = n / (float) population * PI;
            float x = width / 2 + cos(angle) * radius;
            float y = height / 2 + sin(angle) * radius;
            line(width / 2, height / 2, x, y);
        }
        pop();
    }

    private void drawDancers() {
        for (int i = 0; i < population; i++) {
            float phase = frameCount / (float) resolution * TWO_PI;
            turtles[i].draw(phase, loops, flip, radius);
        }
    }

    private class Turtle {
        private float x;
        private float y;
        private float heading;
        private float size;
        private int color;

        public Turtle(int index, int population) {
            heading = index / (float) population * PI;
            size = 2;
            color = color(random(255 * 0.9f), 127 + random(128), random(255 * 0.7f));
        }

        public void draw(float phase, int loops, int flip, float radius) {
            float individualPhase = (phase + heading * loops) % TWO_PI;
            float distance = radius * sin(individualPhase);
            x = width / 2 + cos(heading) * distance;
            y = height / 2 + sin(heading) * distance;

            push();
            translate(x, y);
            if (flip == 1) {
                if (PI / 2 < individualPhase && individualPhase <= 3 * PI / 2) {
                    rotate(PI);
                }
            }
            noStroke();
            fill(color);
            shape(TURTLE, 0, 0, size, size);
            pop();
        }
    }
}
