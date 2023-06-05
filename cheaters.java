import java.util.*;
import java.io.*;

/**
 * Read GitHub cheaters directly from GitHub downloads, match with BJP problems
 *   and output a marked file for use by PracticeItGrader
 *   
 * Version 1.0 6/7/20
 * 
 */
public class Cheaters {
    static boolean ifDebug = false;

    public static void main(String[] args) throws FileNotFoundException {
        File output = new File("Cheaters Generated.txt");
        PrintStream ps = new PrintStream(output);

        ArrayList<Problem> problems = new ArrayList<Problem>();

        ///////////////////////////////////////////////////////////////////
        // Read BJP problem names for matching in cheaters files
        ///////////////////////////////////////////////////////////////////

        // open file of BJP problem names
        File BJPNames = new File("BJP Problem Names.csv");
        if (!BJPNames.canRead())
            System.out.println("Can't read BJP Problem Names.csv");

        // read each problem name and store 
        Scanner sc = new Scanner (BJPNames);
        while (sc.hasNextLine()) {
            /*
             * CSV gives us whole "BJP4 Exercise 8.07: addTimeSpan" which needs to be split out
             *                       0       1     2       3
             */
            String line = sc.nextLine();
            String[] results = line.split("[: ]+");
            int[] chapterVerse = Problem.splitProblemNumber(results[2]);
            Problem p = new Problem(results[1], chapterVerse[0], chapterVerse[1], results[3]);
            if (ifDebug)
                System.out.println("Read BJP " + p + " " + p.getName());
            problems.add(p);
        }
        sc.close();

        ///////////////////////////////////////////////////////////////////
        // Read cheater problems from GitHub files and match to numbers
        // Strip comments
        // Output to file
        ///////////////////////////////////////////////////////////////////

        File cheatersFolder = new File("..\\Github Cheaters");
        if (!cheatersFolder.canRead() || !cheatersFolder.isDirectory())
            System.out.println("Can't read Github Cheaters directory");
        // iterate directory of cheaters
        for (File cheat : cheatersFolder.listFiles()) {
            // get cheater name
            String cheaterName = cheat.getName();
            System.out.println("Directory: " + cheaterName);

            // validate directory
            if (!cheat.isDirectory()) {
                System.out.printf("ERROR %s not a directory", cheat.getName());
                continue;
            }

            // iterate chapters
            System.out.print("Chapters: ");
            for (File chapter : cheat.listFiles()) {
                // get chapter name
                String chapterName = chapter.getName();

                // get chapter number
                int index = 0;
                int len = chapterName.length();
                while(!Character.isDigit(chapterName.charAt(index)))
                    index++;
                int end = index; 
                while(end < len && Character.isDigit(chapterName.charAt(end)))
                    end++;
                int chNum = Integer.parseInt(chapterName.substring(index,  end));

                System.out.printf(chNum + " ");

                // validate directory
                if (!chapter.isDirectory()) {
                    System.out.printf("ERROR %s not a directory", chapter.getName());
                    continue;
                }

                // iterate files
                for (File problem : chapter.listFiles()) {
                    // get problem name by stripping .JAVA
                    String problemName = problem.getName().substring(0, problem.getName().indexOf('.'));

                    if (ifDebug)
                        System.out.printf("File %s problem %s\n", problem.getName(), problemName);

                    // Find full problem number based on chapter & name
                    boolean ifMatched = false;
                    Problem currentProblem = null;
                    for (Problem p : problems) {
                        if (chNum == p.getChapter() && problemName.equalsIgnoreCase(p.getName())) {
                            if (ifDebug)
                                System.out.printf("Matched %s to problem: %s\n", problemName, p);
                            ifMatched = true;
                            currentProblem = p;
                        }
                    }

                    // Warn if we didn't match up to a problem
                    // Some problems were removed after BJP3
                    if (!ifMatched) {
                        List<String> knownBad = Arrays.asList("Spiral", "CollegeAdmit", "swapPairs");
                        if (!knownBad.contains(problemName))
                            System.out.printf("WARNING - failed to match problem name %s for cheater %s in Chapter %d\n", problemName, cheaterName, chNum);
                        continue;
                    }

                    //////////////////////////////////////////////////////////    
                    // Output problem to file
                    //////////////////////////////////////////////////////////

                    // output header **<cheatername> <Exercise/Self-Check> <Chapter:Number>
                    String result = String.format("**%s %s %d:%d", cheaterName, currentProblem.getType(), currentProblem.getChapter(), currentProblem.getNumber());
                    ps.println(result);

                    // Skip comments, put rest into output
                    Scanner scCode = new Scanner(problem);
                    boolean fInMultiline = false;
                    while (scCode.hasNextLine()) {
                        String line = scCode.nextLine();
                        // assume full line comment starts the line
                        if (line.startsWith("//")) {
                            //                            System.out.printf("Skipping %s for %s %s %d:%d\n",line, cheaterName, currentProblem.getType(), currentProblem.getChapter(), currentProblem.getNumber());
                            continue;
                        }

                        // Multiline comments generally start & end a line
                        if (line.startsWith("/*")) {
                            fInMultiline = true;
                            continue;
                        }
                        if (line.endsWith("*/")) {
                            if (!fInMultiline)
                                System.out.printf("ERROR: should be in comment **%s %s %d:%d %s\n", cheaterName, currentProblem.getType(), currentProblem.getChapter(), currentProblem.getNumber(), currentProblem.getName());
                            fInMultiline = false;
                            continue;
                        }
                        //                        if (ifInMultiline) 
                        //                            System.out.printf("Skipping %s for %s %s %d:%d\n",line, cheaterName, currentProblem.getType(), currentProblem.getChapter(), currentProblem.getNumber());

                        // print code to output
                        if (!fInMultiline)
                            ps.println(line);
                    } // end code lines
                } // end problem
            } // end chapter
            System.out.println();
        } // end cheat
    } // end main
}
