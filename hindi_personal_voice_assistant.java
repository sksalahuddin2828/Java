import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.gtranslate.GoogleTranslate;

public class VoiceAssistant {

    public static void main(String[] args) {
        clockTime();
        speak("हैलो मेरा नाम शेख सलाहुद्दीन है, बताइये में आपकी क्या मदद कर सक्ती हूं");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("बोलिए और क्या मदद चाहिए: ");
            String command = scanner.nextLine();
            String translated = translateToEnglish(command);

            if (translated.toLowerCase().contains("time") || translated.toLowerCase().contains("date")) {
                String date = getCurrentDate();
                String clock = getCurrentTime();
                speak("आज की तिथि है " + date + " और वर्तमान समय है " + clock);
            } else if (translated.toLowerCase().contains("ip address")) {
                String ipAddress = getIpAddress();
                speak("आपका इंटरनेट प्रोटोकोल (आई पी) है: " + ipAddress);
            } else if (translated.toLowerCase().contains("youtube")) {
                openWebPage("https://www.youtube.com/");
            } else if (translated.toLowerCase().contains("google")) {
                openWebPage("https://www.google.com/");
            } else if (translated.toLowerCase().contains("wikipedia")) {
                openWebPage("https://wikipedia.org/");
            } else if (translated.toLowerCase().contains("who made you") || translated.toLowerCase().contains("creator")) {
                speak("नाम: शेख सलाहुद्दीन ने मुझे बनाया।");
                speak("Full Name: Sk. Salahuddin, Address: House/Holding No. 173, Village/Road: Maheshwar Pasha Kalibari, Post Office: KUET, Postal Code: 9203, Police Station: Daulatpur, District: Khulna, Country: Bangladesh, Mobile No. +8801767902828, Email: sksalahuddin2828@gmail.com");
                openWebPage("https://github.com/sksalahuddin2828");
            } else if (translated.toLowerCase().contains("close") || translated.toLowerCase().contains("exit") || translated.toLowerCase().contains("good bye") || translated.toLowerCase().contains("ok bye") || translated.toLowerCase().contains("turn off") || translated.toLowerCase().contains("shut down")) {
                speak("अपना ध्यान रखना, बाद में मिलते हैं! धन्यवाद");
                System.out.println("Stopping Program");
                break;
            } else {
                System.out.println("Command not recognized. Please try again.");
            }
        }
    }

    public static void clockTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 12) {
            speak("शुभ प्रभात");
        } else if (hour >= 12 && hour < 18) {
            speak("अभी दोपहर");
        } else if (hour >= 18 && hour < 20) {
            speak("अभी शाम");
        } else {
            speak("शुभ रात्रि");
        }
    }

    public static String getCurrentDate() {
        Date now = new Date();
        return now.toString(); // Format the date as required
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return hour + ":" + minute; // Format the time as required
    }

    public static String getIpAddress() {
        try {
            URL url = new URL("https://api.ipify.org");
            Scanner scanner = new Scanner(url.openStream());
            String ipAddress = scanner.nextLine();
            scanner.close();
            return ipAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void openWebPage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static String translateToEnglish(String text) {
        String translatedText = "";
        try {
            translatedText = GoogleTranslate.translate("hi", "en", text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return translatedText;
    }

    public static void speak(String text) {
        System.out.println(text);

        // Code for speech synthesis using FreeTTS library
        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice("kevin16"); // Choose a voice from available options
        if (voice != null) {
            voice.allocate();
            voice.speak(text);
            voice.deallocate();
        } else {
            System.out.println("No voice available");
        }
    }
}
