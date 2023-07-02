import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WifiProfileReader {
    public static void main(String[] args) {
        try {
            Process process = Runtime.getRuntime().exec("netsh wlan show profiles");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            List<String> profiles = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("    All User Profile")) {
                    String profileName = line.substring(line.indexOf(":") + 1).trim();
                    profiles.add(profileName);
                }
            }

            List<WifiProfile> wifiList = new ArrayList<>();

            for (String profile : profiles) {
                Process profileProcess = Runtime.getRuntime().exec("netsh wlan show profile \"" + profile + "\" key=clear");
                BufferedReader profileReader = new BufferedReader(new InputStreamReader(profileProcess.getInputStream()));
                String profileLine;
                StringBuilder profileInfo = new StringBuilder();

                while ((profileLine = profileReader.readLine()) != null) {
                    profileInfo.append(profileLine).append("\n");
                }

                if (profileInfo.toString().contains("Security key           : Absent")) {
                    continue;
                } else {
                    WifiProfile wifiProfile = new WifiProfile();
                    wifiProfile.setSsid(profile);

                    Pattern passwordPattern = Pattern.compile("Key Content            : (.*)");
                    Matcher passwordMatcher = passwordPattern.matcher(profileInfo.toString());

                    if (passwordMatcher.find()) {
                        wifiProfile.setPassword(passwordMatcher.group(1));
                    } else {
                        wifiProfile.setPassword(null);
                    }

                    wifiList.add(wifiProfile);
                }
            }

            for (WifiProfile wifiProfile : wifiList) {
                System.out.println(wifiProfile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class WifiProfile {
    private String ssid;
    private String password;

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SSID: " + ssid + ", Password: " + password;
    }
}
