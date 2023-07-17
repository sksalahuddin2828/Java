import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tesseract3DProjection extends Application {

    // Define tesseract vertices
    private double[][] vertices = new double[][]{
            {-1, -1, -1, -1, -1},
            {-1, -1, -1, -1, 1},
            {-1, -1, -1, 1, -1},
            {-1, -1, -1, 1, 1},
            {-1, -1, 1, -1, -1},
            {-1, -1, 1, -1, 1},
            {-1, -1, 1, 1, -1},
            {-1, -1, 1, 1, 1},
            {-1, 1, -1, -1, -1},
            {-1, 1, -1, -1, 1},
            {-1, 1, -1, 1, -1},
            {-1, 1, -1, 1, 1},
            {-1, 1, 1, -1, -1},
            {-1, 1, 1, -1, 1},
            {-1, 1, 1, 1, -1},
            {-1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1} // Second dimension vertex
    };

    // Define edges of the tesseract
    private int[][] edges = new int[][]{
            {0, 1}, {0, 2}, {0, 4}, {1, 3}, {1, 5}, {2, 3}, {2, 6}, {3, 7},
            {4, 5}, {4, 6}, {5, 7}, {6, 7}, {8, 9}, {8, 10}, {8, 12}, {9, 11},
            {9, 13}, {10, 11}, {10, 14}, {11, 15}, {12, 13}, {12, 14}, {13, 15},
            {14, 15}, {0, 8}, {1, 9}, {2, 10}, {3, 11}, {4, 12}, {5, 13}, {6, 14},
            {7, 15}
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Group to hold all the shapes
        Group root = new Group();

        // Project vertices onto 3D space (select the first three components)
        double[][] projectedVertices = new double[vertices.length][3];
        for (int i = 0; i < vertices.length; i++) {
            projectedVertices[i][0] = vertices[i][0];
            projectedVertices[i][1] = vertices[i][1];
            projectedVertices[i][2] = vertices[i][2];
        }

        // Plot the tesseract edges
        for (int[] edge : edges) {
            Line line = new Line(
                    projectedVertices[edge[0]][0], projectedVertices[edge[0]][1],
                    projectedVertices[edge[1]][0], projectedVertices[edge[1]][1]
            );
            line.setStroke(Color.GRAY);
            root.getChildren().add(line);
        }

        // Plot projected vertices with labels
        for (int i = 0; i < projectedVertices.length; i++) {
            Text text = new Text(projectedVertices[i][0], projectedVertices[i][1], Integer.toString(i));
            text.setFont(new Font(8));
            text.setFill(Color.BLACK);
            text.setTranslateZ(5); // To make the label visible above the edges
            root.getChildren().add(text);
        }

        // Create illusion lines connecting projected vertices
        for (int i = 0; i < projectedVertices.length; i++) {
            for (int j = i + 1; j < projectedVertices.length; j++) {
                Line line = new Line(
                        projectedVertices[i][0], projectedVertices[i][1],
                        projectedVertices[j][0], projectedVertices[j][1]
                );
                line.setStroke(Color.BLACK);
                line.setOpacity(0.3);
                root.getChildren().add(line);
            }
        }

        // Create the Scene and add it to the Stage
        Scene scene = new Scene(root, 800, 800, true);
        primaryStage.setTitle("3D Projection of a Tesseract (4D Hypercube)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
