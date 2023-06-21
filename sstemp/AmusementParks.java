import java.util.Scanner;

//ABSTRACT CLASS
abstract class Themepark{
    int afee = 500, cfee = 300;
    int calc(int n, int m){
        return (n*afee) + (m*cfee);
    }
    abstract void playGame(int j);
}

//QUEENSLAND
class Queensland extends Themepark{
    int i;
    Boolean games[] = new Boolean[30];

    Queensland(){
        System.out.println("Welcome to Queensland!");
        for(i = 0; i < games.length; i++){
            games[i] = false;
        }
    }

    void playGame(int j){
        if(games[j] == true){
            System.out.println("You've already played game "+j);
        }
        else{
            System.out.println("Playing Game " + j + " at Queensland");
            games[j] = true;
        }
    }
}

//WONDERLA
class Wonderla extends Themepark{
    int i;
    Boolean games[] = new Boolean[40];

    Wonderla(){
        System.out.println("Welcome to Wonderla!");
        for(i = 0; i < games.length; i++){
            games[i] = false;
        }
    }

    void playGame(int j){
        if(games[j] == true){
            System.out.println("You've already played game "+j);
        }
        else{
            System.out.println("Playing Game " + j + " at Wonderla");
            games[j] = true;
        }
    }
}

//MAIN FUNCTION
public class AmusementParks {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i=0;
        
        int c = Integer.parseInt(sc.nextLine());
        String line = sc.nextLine();
        String[] g = line.split(" ");
        int[] games = new int[g.length];
        int size = g.length;
        
        for(i=0;i<g.length;i++) {
        	games[i] = Integer.parseInt(g[i]);
        }
        
        switch(c){
            case 1:
                Queensland q = new Queensland();
                for(i=0;i<size;i++) {
                	q.playGame(games[i]);
                }
                break;

            case 2:
                Wonderla w = new Wonderla();
                for(i=0;i<size;i++) {
                	w.playGame(games[i]);
                }
                break;

            default:
                System.out.println("Invalid");
                break;
        }
    }
}
