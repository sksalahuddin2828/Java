import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class TesseractProjection extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Define the vertices of a unit tesseract in 4D space
        double[][] vertices = {
                {-0.5, -0.5, -0.5, -0.5},
                {0.5, -0.5, -0.5, -0.5},
                {0.5, 0.5, -0.5, -0.5},
                {-0.5, 0.5, -0.5, -0.5},
                {-0.5, -0.5, 0.5, -0.5},
                {0.5, -0.5, 0.5, -0.5},
                {0.5, 0.5, 0.5, -0.5},
                {-0.5, 0.5, 0.5, -0.5},
                {-0.5, -0.5, -0.5, 0.5},
                {0.5, -0.5, -0.5, 0.5},
                {0.5, 0.5, -0.5, 0.5},
                {-0.5, 0.5, -0.5, 0.5},
                {-0.5, -0.5, 0.5, 0.5},
                {0.5, -0.5, 0.5, 0.5},
                {0.5, 0.5, 0.5, 0.5},
                {-0.5, 0.5, 0.5, 0.5},
        };

        // Define the edges of the tesseract
        int[][] edges = {
                {0, 1}, {1, 2}, {2, 3}, {3, 0},
                {4, 5}, {5, 6}, {6, 7}, {7, 4},
                {0, 4}, {1, 5}, {2, 6}, {3, 7},
                {8, 9}, {9, 10}, {10, 11}, {11, 8},
                {12, 13}, {13, 14}, {14, 15}, {15, 12},
                {8, 12}, {9, 13}, {10, 14}, {11, 15},
                {0, 8}, {1, 9}, {2, 10}, {3, 11},
                {4, 12}, {5, 13}, {6, 14}, {7, 15},
        };

        Group root = new Group();

        // Project the tesseract's vertices to 3D space
        double[][] projection3D = new double[vertices.length][3];
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < 3; j++) {
                projection3D[i][j] = (vertices[i][j] + vertices[i][j + 1]) / 2.0;
            }
        }

        // Plot the edges
        for (int[] edge : edges) {
            Line line = new Line(projection3D[edge[0]][0] * 100 + 150, projection3D[edge[0]][1] * 100 + 150,
                    projection3D[edge[1]][0] * 100 + 150, projection3D[edge[1]][1] * 100 + 150);
            line.setStroke(Color.BLUE);
            root.getChildren().add(line);
        }

        // Plot the faces of the tesseract
        int[][] faces = {
                {0, 1, 2, 3}, {4, 5, 6, 7}, {0, 1, 5, 4},
                {2, 3, 7, 6}, {0, 3, 7, 4}, {1, 2, 6, 5},
                {8, 9, 10, 11}, {12, 13, 14, 15}, {8, 9, 13, 12},
                {10, 11, 15, 14}, {8, 11, 15, 12}, {9, 10, 14, 13},
                {0, 8, 12, 4}, {1, 9, 13, 5}, {2, 10, 14, 6}, {3, 11, 15, 7}
        };
        for (int[] face : faces) {
            Polygon polygon = new Polygon();
            for (int idx : face) {
                polygon.getPoints().addAll(projection3D[idx][0] * 100 + 150, projection3D[idx][1] * 100 + 150);
            }
            polygon.setStroke(Color.RED);
            polygon.setFill(Color.CYAN);
            root.getChildren().add(polygon);
        }

        Scene scene = new Scene(root, 300, 300, Color.WHITE);

        primaryStage.setTitle("3D Projection of a Tesseract (4D Hypercube)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
