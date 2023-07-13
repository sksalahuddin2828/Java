import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import matplotlib.pyplot as plt;
import mpl_toolkits.mplot3d.Axes3D;
import requests;

public class SatelliteVisualization {

    public static void main(String[] args) {
        try {
            // Step 1: Retrieve satellite data from the API
            String satelliteDataApiUrl = "API_URL_HERE";
            String satelliteDataJson = sendGetRequest(satelliteDataApiUrl);
            JSONArray satelliteData = new JSONArray(satelliteDataJson);

            // Step 2: Parse TLE data using skyfield
            List<String[]> tleData = new ArrayList<>();
            for (int i = 0; i < satelliteData.length(); i++) {
                JSONObject satellite = satelliteData.getJSONObject(i);
                String line1 = satellite.getString("tle_line1");
                String line2 = satellite.getString("tle_line2");
                tleData.add(new String[] { line1, line2 });
            }

            // Step 3: Visualize satellite orbits in 3D
            fig = plt.figure();
            ax = fig.add_subplot(111, projection='3d');

            for (String[] tle : tleData) {
                // Calculate the satellite's position over time
                TimeScale ts = TimeScalesFactory.getUTC();
                AbsoluteDate start = AbsoluteDate.J2000_EPOCH;
                AbsoluteDate end = AbsoluteDate.J2000_EPOCH.shiftedBy(3600);
                List<AbsoluteDate> dates = new ArrayList<>();
                for (AbsoluteDate date = start; date.compareTo(end) <= 0; date = date.shiftedBy(60)) {
                    dates.add(date);
                }
                Satellite satellite = new Satellite(new TLE(tle[0], tle[1]), CelestialBodyFactory.getEarth());
                List<PVCoordinates> pvCoordinatesList = satellite.getPVCoordinatesList(dates);
                List<GeodeticPoint> subpoints = new ArrayList<>();
                for (PVCoordinates pvCoordinates : pvCoordinatesList) {
                    subpoints.add(Ellipsoid.WGS84.transform(pvCoordinates.getPosition(), FramesFactory.getICRF(),
                            AbsoluteDate.J2000_EPOCH));
                }

                // Extract latitude, longitude, and altitude
                List<Double> latitudeList = new ArrayList<>();
                List<Double> longitudeList = new ArrayList<>();
                List<Double> altitudeList = new ArrayList<>();
                for (GeodeticPoint subpoint : subpoints) {
                    latitudeList.add(subpoint.getLatitude());
                    longitudeList.add(subpoint.getLongitude());
                    altitudeList.add(subpoint.getAltitude());
                }

                // Plot the satellite's trajectory in 3D
                ax.plot(longitudeList, latitudeList, altitudeList);
            }

            ax.set_xlabel("Longitude");
            ax.set_ylabel("Latitude");
            ax.set_zlabel("Altitude (km)");

            // Step 4: Map satellites to countries using the satellite database API
            String satelliteDbApiUrl = "SATELLITE_DB_API_URL_HERE";
            String satelliteDbJson = sendGetRequest(satelliteDbApiUrl);
            JSONArray satelliteDb = new JSONArray(satelliteDbJson);

            // Mapping satellite names to countries
            Map<String, String> satelliteCountryMap = new HashMap<>();
            for (int i = 0; i < satelliteData.length(); i++) {
                JSONObject satellite = satelliteData.getJSONObject(i);
                String name = satellite.getString("name");
                for (int j = 0; j < satelliteDb.length(); j++) {
                    JSONObject entry = satelliteDb.getJSONObject(j);
                    if (entry.getString("name").equals(name)) {
                        String country = entry.getString("country");
                        satelliteCountryMap.put(name, country);
                        break;
                    }
                }
            }

            // Printing satellite information
            for (int i = 0; i < satelliteData.length(); i++) {
                JSONObject satellite = satelliteData.getJSONObject(i);
                String name = satellite.getString("name");
                double angle = satellite.getDouble("angle");
                String country = satelliteCountryMap.getOrDefault(name, "Unknown");

                System.out.println("Satellite Name: " + name);
                System.out.println("Orbital Angle: " + angle + " degrees");
                System.out.println("Country: " + country);
                System.out.println();
            }

            // Show the 3D plot
            plt.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sendGetRequest(String url) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();

        try {
            URL requestUrl = new URL(url);
            connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }
}
