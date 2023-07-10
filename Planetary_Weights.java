import java.util.Scanner;

public class PlanetaryWeights {
    public static void main(String[] args) {
        final double MERCURY_GRAVITY = 0.376;
        final double VENUS_GRAVITY = 0.889;
        final double MARS_GRAVITY = 0.378;
        final double JUPITER_GRAVITY = 2.36;
        final double SATURN_GRAVITY = 1.081;
        final double URANUS_GRAVITY = 0.815;
        final double NEPTUNE_GRAVITY = 1.14;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a weight on Earth: ");
        double earthWeight = scanner.nextDouble();

        System.out.print("Enter a planet: ");
        String planet = scanner.next().toLowerCase().capitalize();

        while (!isValidPlanet(planet)) {
            if (planet.equals("Earth")) {
                System.out.println("Please select a planet other than Earth.");
            } else {
                System.out.println("Error: " + planet + " is not a planet.");
            }
            
            System.out.print("Enter a planet: ");
            planet = scanner.next().toLowerCase().capitalize();
        }

        double planetWeight;
        
        switch (planet) {
            case "Mercury":
                planetWeight = earthWeight * MERCURY_GRAVITY;
                break;
            case "Venus":
                planetWeight = earthWeight * VENUS_GRAVITY;
                break;
            case "Mars":
                planetWeight = earthWeight * MARS_GRAVITY;
                break;
            case "Jupiter":
                planetWeight = earthWeight * JUPITER_GRAVITY;
                break;
            case "Saturn":
                planetWeight = earthWeight * SATURN_GRAVITY;
                break;
            case "Uranus":
                planetWeight = earthWeight * URANUS_GRAVITY;
                break;
            default:
                planetWeight = earthWeight * NEPTUNE_GRAVITY;
                break;
        }

        double roundedWeight = Math.round(planetWeight * 100.0) / 100.0;

        System.out.println("The equivalent weight on " + planet + ": " + roundedWeight);
    }

    public static boolean isValidPlanet(String planet) {
        String[] validPlanets = { "Mercury", "Venus", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune" };
        for (String validPlanet : validPlanets) {
            if (validPlanet.equalsIgnoreCase(planet)) {
                return true;
            }
        }
        return false;
    }
}
