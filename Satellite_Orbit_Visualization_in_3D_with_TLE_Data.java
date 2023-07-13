import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.orekit.bodies.*;
import org.orekit.data.*;
import org.orekit.frames.*;
import org.orekit.orbits.*;
import org.orekit.time.*;
import org.orekit.utils.*;

public class SatelliteOrbitVisualization extends Application {

    // Define satellite data class
    private static class SatelliteData {
        private String name;
        private String line1;
        private String line2;
        private Color color;

        public SatelliteData(String name, String line1, String line2, Color color) {
            this.name = name;
            this.line1 = line1;
            this.line2 = line2;
            this.color = color;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Read TLE data from a file
        String tleFile = "tle_data.txt";  // Path to the TLE file
        List<SatelliteData> satelliteDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(tleFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String name = line.trim();
                String line1 = br.readLine().trim();
                String line2 = br.readLine().trim();
                Color color = Color.rgb(0, 0, 0);  // Set a default color
                satelliteDataList.add(new SatelliteData(name, line1, line2, color));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Set up the JavaFX scene
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setTitle("Satellite Orbits");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize the Earth
        Sphere earth = new Sphere(100, 32);
        earth.setTranslateX(scene.getWidth() / 2);
        earth.setTranslateY(scene.getHeight() / 2);
        earth.setTranslateZ(0);
        earth.setMaterial(new PhongMaterial(Color.BLUE));
        root.getChildren().add(earth);

        // Initialize the satellite paths
        List<Cylinder> satellitePaths = new ArrayList<>();
        for (SatelliteData satelliteData : satelliteDataList) {
            Cylinder satellitePath = new Cylinder(0.2, 1000);
            satellitePath.setTranslateX(scene.getWidth() / 2);
            satellitePath.setTranslateY(scene.getHeight() / 2);
            satellitePath.setTranslateZ(0);
            satellitePath.setMaterial(new PhongMaterial(satelliteData.color));
            root.getChildren().add(satellitePath);
            satellitePaths.add(satellitePath);

            // Label each satellite
            Text satelliteLabel = new Text(satelliteData.name);
            satelliteLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            satelliteLabel.setFill(satelliteData.color);
            root.getChildren().add(satelliteLabel);
        }

        // Animate the visualization
        final long startNanoTime = System.nanoTime();
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1_000_000_000.0; // Time in seconds

                // Update satellite paths
                for (int i = 0; i < satelliteDataList.size(); i++) {
                    SatelliteData satelliteData = satelliteDataList.get(i);
                    Orbit orbit = createOrbit(satelliteData.line1, satelliteData.line2);
                    PVCoordinates pvCoordinates = orbit.getPVCoordinates(getDate(t));
                    double x = pvCoordinates.getPosition().getX() / 1_000_000; // Scale the coordinates
                    double y = pvCoordinates.getPosition().getY() / 1_000_000;
                    double z = pvCoordinates.getPosition().getZ() / 1_000_000;

                    Cylinder satellitePath = satellitePaths.get(i);
                    satellitePath.getTransforms().clear();
                    satellitePath.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
                    satellitePath.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
                    satellitePath.setTranslateX(scene.getWidth() / 2 + x);
                    satellitePath.setTranslateY(scene.getHeight() / 2 + y);
                    satellitePath.setTranslateZ(z);
                }
            }
        }.start();
    }

    private AbsoluteDate getDate(double t) {
        return AbsoluteDate.J2000_EPOCH.shiftedBy(t);
    }

    private Orbit createOrbit(String line1, String line2) {
        try {
            DataProvidersManager manager = DataProvidersManager.getInstance();
            manager.addProvider(new DirectoryCrawler(new java.io.File(".")));

            TLE tle = new TLE(line1, line2);
            Frame earthFrame = FramesFactory.getICRF();
            Orbit orbit = tle.getOrbit();

            return orbit.shiftedBy(getDate(0)).changeFrame(earthFrame);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
