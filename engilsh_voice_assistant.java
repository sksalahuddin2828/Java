import java.util.Scanner;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DigitalAssistant {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Sk. Salahuddin - Khulna");

        while (true) {
            System.out.println("How may I assist you?");
            String userCommand = scanner.nextLine().toLowerCase();

            if (userCommand.contains("exit") || userCommand.contains("close") || userCommand.contains("off") ||
                    userCommand.contains("good bye") || userCommand.contains("bye") || userCommand.contains("ok bye") ||
                    userCommand.contains("turn off") || userCommand.contains("shutdown") || userCommand.contains("no thanks") ||
                    userCommand.contains("stop")) {
                System.out.println("Assistant Shut Down");
                System.out.println("Take care and see you later");
                break;
            }

            System.out.println("Please wait");
            performAction(userCommand);
        }
    }

    public static void performAction(String userCommand) {
        if (userCommand.contains("weather") || userCommand.contains("weather report") || userCommand.contains("today's weather report")) {
            System.out.println("Sure, which city?");
            Scanner scanner = new Scanner(System.in);
            String city = scanner.nextLine().toLowerCase();
            openWeatherReport(city);
        } else if (userCommand.contains("bangabandhu sheikh mujibur rahman") || userCommand.contains("bangabandhu") ||
                userCommand.contains("sheikh mujibur rahman") || userCommand.contains("father of the nation of bangladesh") ||
                userCommand.contains("father of the nation")) {
            fatherOfTheNationOfBangladesh();
        } else if (userCommand.contains("ip address") || userCommand.contains("internet protocol") || userCommand.contains("ip")) {
            getIPAddress();
        } else if (userCommand.contains("opening wikipedia")) {
            openWikipedia();
        } else if (userCommand.contains("search on wikipedia")) {
            searchOnWikipedia();
        } else if (userCommand.contains("search on youtube")) {
            searchOnYouTube();
        } else if (userCommand.contains("play on youtube") || userCommand.contains("play from youtube") ||
                userCommand.contains("play a song from youtube") || userCommand.contains("play a movie from youtube") ||
                userCommand.contains("play something on youtube") || userCommand.contains("play something from youtube")) {
            playOnYouTube();
        } else if (userCommand.contains("open youtube") || userCommand.contains("opening youtube")) {
            openYouTube();
        } else if (userCommand.contains("date and time")) {
            getDateAndTime();
        } else if (userCommand.contains("today's time") || userCommand.contains("local time") || userCommand.contains("time")) {
            getLocalTime();
        } else if (userCommand.contains("today's date") || userCommand.contains("today date") || userCommand.contains("date")) {
            getTodayDate();
        } else if (userCommand.contains("opening facebook")) {
            openFacebook();
        } else if (userCommand.contains("facebook profile")) {
            openFacebookProfile();
        } else if (userCommand.contains("facebook settings")) {
            openFacebookSettings();
        } else if (userCommand.contains("facebook reels")) {
            openFacebookReel();
        } else if (userCommand.contains("facebook messenger")) {
            openFacebookMessenger();
        } else if (userCommand.contains("facebook video")) {
            openFacebookVideo();
        } else if (userCommand.contains("facebook notification")) {
            openFacebookNotification();
        } else if (userCommand.contains("opening google")) {
            openGoogleBrowser();
        } else if (userCommand.contains("opening gmail")) {
            openGoogleMail();
        } else if (userCommand.contains("google earth")) {
            openGoogleEarth();
        } else if (userCommand.contains("google city") || userCommand.contains("city on google") ||
                userCommand.contains("city from earth") || userCommand.contains("city on earth")) {
            googleEarthSpecifyCity();
        } else if (userCommand.contains("google map") || userCommand.contains("map") || userCommand.contains("map on google")) {
            openGoogleMap();
        } else if (userCommand.contains("city from map") || userCommand.contains("map city") ||
                userCommand.contains("city on map") || userCommand.contains("google map city")) {
            googleMapSpecifyCity();
        } else if (userCommand.contains("translate to english") || userCommand.contains("translate into english") ||
                userCommand.contains("word translate") || userCommand.contains("translate a sentence")) {
            googleTranslateSpecifyWord();
        } else if (userCommand.contains("listen a joke") || userCommand.contains("tell me a joke")) {
            tellJoke();
        } else if (userCommand.contains("translation between two language") || userCommand.contains("translated language") ||
                userCommand.contains("translate from google") || userCommand.contains("language translation") ||
                userCommand.contains("language")) {
            translateLanguages();
        } else if (userCommand.contains("what can you do") || userCommand.contains("available commands") || userCommand.contains("help")) {
            availableCommands();
        } else if (userCommand.contains("who made you")) {
            whoMadeYou();
        } else if (userCommand.contains("what is your name") || userCommand.contains("your name")) {
            whatIsYourName();
        } else if (userCommand.contains("ask")) {
            computationalGeographicalQuestion();
        } else {
            System.out.println("Sorry, I didn't understand that command. Please try again!");
        }
    }

    public static void openWeatherReport(String city) {
        System.out.println("Opening the weather report for " + city + ".");
        try {
            Document doc = Jsoup.connect("https://www.weather-forecast.com/locations/" + city + "/forecasts/latest").get();
            Elements weatherElements = doc.select(".b-forecast__table-description-content");
            for (Element element : weatherElements) {
                System.out.println(element.text());
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch weather information. Please try again later.");
        }
    }

    public static void fatherOfTheNationOfBangladesh() {
        System.out.println("The Father of the Nation Bangabandhu Sheikh Mujibur Rahman is the architect of independent Bangladesh.");
        System.out.println("He played a vital role in the liberation movement and is revered as a national hero.");
    }

    public static void getIPAddress() {
        try {
            Document doc = Jsoup.connect("https://checkip.amazonaws.com").get();
            String ipAddress = doc.body().text();
            System.out.println("Your IP address is: " + ipAddress);
        } catch (IOException e) {
            System.out.println("Failed to retrieve IP address. Please try again later.");
       }

    public static void openWikipedia() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.wikipedia.org/"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Wikipedia. Please try again later.");
        }
    }

    public static void searchOnWikipedia() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to search on Wikipedia?");
        String query = scanner.nextLine();
        try {
            Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/" + query));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to search on Wikipedia. Please try again later.");
        }
    }

    public static void searchOnYouTube() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to search on YouTube?");
        String query = scanner.nextLine();
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/results?search_query=" + query));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to search on YouTube. Please try again later.");
        }
    }

    public static void playOnYouTube() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to play on YouTube?");
        String query = scanner.nextLine();
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/results?search_query=" + query));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to play on YouTube. Please try again later.");
        }
    }

    public static void openYouTube() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open YouTube. Please try again later.");
        }
    }

    public static void getDateAndTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date currentDate = Calendar.getInstance().getTime();
        String dateTime = dateFormat.format(currentDate);
        System.out.println("The current date and time is: " + dateTime);
    }

    public static void getLocalTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = Calendar.getInstance().getTime();
        String localTime = timeFormat.format(currentTime);
        System.out.println("The current local time is: " + localTime);
    }

    public static void getTodayDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = Calendar.getInstance().getTime();
        String todayDate = dateFormat.format(currentDate);
        System.out.println("Today's date is: " + todayDate);
    }

    public static void openFacebook() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Facebook. Please try again later.");
        }
    }

    public static void openFacebookProfile() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/profile.php"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Facebook profile. Please try again later.");
        }
    }

    public static void openFacebookSettings() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/settings"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Facebook settings. Please try again later.");
        }
    }

    public static void openFacebookReel() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/reels"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Facebook Reels. Please try again later.");
        }
    }

    public static void openFacebookMessenger() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.messenger.com/"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Facebook Messenger. Please try again later.");
        }
    }

    public static void openFacebookVideo() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/videos"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Facebook videos. Please try again later.");
        }
    }

    public static void openFacebookNotification() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.facebook.com/notifications"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Facebook notifications. Please try again later.");
        }
    }

    public static void openGoogleBrowser() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.google.com/"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Google. Please try again later.");
        }
    }

    public static void openGoogleMail() {
        try {
            Desktop.getDesktop().browse(new URI("https://mail.google.com/"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Google Mail. Please try again later.");
        }
    }

    public static void openGoogleEarth() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.google.com/earth/"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Google Earth. Please try again later.");
        }
    }

    public static void googleEarthSpecifyCity() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which city do you want to see on Google Earth?");
        String city = scanner.nextLine().toLowerCase();
        try {
            Desktop.getDesktop().browse(new URI("https://www.google.com/earth/geo/" + city + "/"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Google Earth for the specified city. Please try again later.");
        }
    }

    public static void openGoogleMap() {
        try {
            Desktop.getDesktop().browse(new URI("https://www.google.com/maps/"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Google Map. Please try again later.");
        }
    }

    public static void googleMapSpecifyCity() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which city do you want to see on Google Map?");
        String city = scanner.nextLine().toLowerCase();
        try {
            Desktop.getDesktop().browse(new URI("https://www.google.com/maps/place/" + city + "/"));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Google Map for the specified city. Please try again later.");
        }
    }

    public static void googleTranslateSpecifyWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which word or sentence do you want to translate to English?");
        String text = scanner.nextLine();
        try {
            Desktop.getDesktop().browse(new URI("https://translate.google.com/#auto/en/" + text));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Google Translate. Please try again later.");
        }
    }

    public static void tellJoke() {
        try {
            Document doc = Jsoup.connect("https://www.jokes4us.com/miscellaneousjokes/cleanjokes.html").get();
            Elements jokeElements = doc.select("div[style='font-size:medium;']");
            for (Element element : jokeElements) {
                System.out.println(element.text());
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch a joke. Please try again later.");
        }
    }

    public static void translateLanguages() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which language do you want to translate from?");
        String fromLanguage = scanner.nextLine().toLowerCase();
        System.out.println("Which language do you want to translate to?");
        String toLanguage = scanner.nextLine().toLowerCase();
        System.out.println("What do you want to translate?");
        String text = scanner.nextLine();
        try {
            Desktop.getDesktop().browse(new URI("https://translate.google.com/#" + fromLanguage + "/" + toLanguage + "/" + text));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Failed to open Google Translate. Please try again later.");
        }
    }

    public static void availableCommands() {
        System.out.println("Available commands:");
        System.out.println("- Weather: Get the weather report of a city");
        System.out.println("- Bangabandhu Sheikh Mujibur Rahman: Learn about the Father of the Nation of Bangladesh");
        System.out.println("- IP address: Get your IP address");
        System.out.println("- Opening Wikipedia: Open the Wikipedia homepage");
        System.out.println("- Search on Wikipedia: Search for a specific topic on Wikipedia");
        System.out.println("- Search on YouTube: Search for a video on YouTube");
        System.out.println("- Play on YouTube: Search and play a video on YouTube");
        System.out.println("- Open YouTube: Open the YouTube homepage");
        System.out.println("- Date and Time: Get the current date and time");
        System.out.println("- Today's Time: Get the current local time");
        System.out.println("- Today's Date: Get today's date");
        System.out.println("- Opening Facebook: Open the Facebook homepage");
        System.out.println("- Facebook Profile: Open your Facebook profile");
        System.out.println("- Facebook Settings: Open the Facebook settings page");
        System.out.println("- Facebook Reels: Open Facebook Reels");
        System.out.println("- Facebook Messenger: Open Facebook Messenger");
        System.out.println("- Facebook Video: Open Facebook videos");
        System.out.println("- Facebook Notification: Open Facebook notifications");
        System.out.println("- Opening Google: Open the Google homepage");
        System.out.println("- Opening Gmail: Open Google Mail");
        System.out.println("- Google Earth: Open Google Earth");
        System.out.println("- Google City: View a city on Google Earth");
        System.out.println("- Google Map: Open Google Map");
        System.out.println("- City from Map: View a city on Google Map");
        System.out.println("- Translate to English: Translate a word or sentence to English");
        System.out.println("- Listen a Joke: Listen to a joke");
        System.out.println("- Translation between two languages: Translate text between two languages");
        System.out.println("- What can you do: Get the list of available commands");
        System.out.println("- Who made you: Know who made this digital assistant");
        System.out.println("- What is your name: Know the name of this digital assistant");
        System.out.println("- Ask: Ask a computational or geographical question");
    }

    public static void whoMadeYou() {
        System.out.println("I was created by Sk. Salahuddin from Khulna, Bangladesh.");
    }

    public static void whatIsYourName() {
        System.out.println("My name is Digital Assistant.");
    }

    public static void computationalGeographicalQuestion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please ask your question:");
        String question = scanner.nextLine();
        System.out.println("Sorry, I'm unable to answer computational or geographical questions at the moment.");
    }
}
