import java.io.*;
import java.util.*;
import java.time.format.*;
import java.time.*;

/**
 * Class Problem
 * 
 * Stores a PracticeIt Problem (e.g. SC 1.1 or Ex 3G.11a) - type ("SC"/"Ex"), chapter, verse, completed
 * 
 * See readProblemNumber for details on how to handle 3G or 11a
 * 
 * @author George Hu
 * 
 * Version 1.3 - 12/30/18 Split out Problem & Student into their own files & 
 *                      changed static student & problem data structures to locals passed as parameters
 * Version 1.3.1 - 12/30/18 added ifEncrypt to scramble student usernames
 * Version 1.4 - 12/30/18 added date field to count problems by deadline
 * Version 1.5 - 9/30/19 changed from using TXT scraping to CSV file due to PracticeIt changes
 * Version 2.0 - 5/30/20 added cheat checks for tries, times, code hash, red flags
 * Version 2.0.1 - 5/31/20 cleanup - added Flag class, catch null pointers
 * Version 2.0.2 - 6/5/20 print cleanup
 * Version 2.0.3 - 6/6/20 removed filters for code size & uniqueness, debug hash variables
 * Version 2.0.2.1 - 6/7/20 added problem name to support the auto-generation of cheaters.txt through the cheaters.java file
 * 
 * Problem is used in several situations
 *   When reading a list of assigned problems it fills the type/chapter/number fields
 *     It will also check for a cheaters file and fill in the codeHash & redFlag
 *     
 *   When reading student problems it fills in all fields
 *     Student HAS-A list of Problems
 *     It will record single Map entries for the student's tries, codeHash
 *     
 *   When printing out the problems it will do analysis across all students and
 *     fill the problem Maps with all student codeHash (as well as known cheaters)
 *     and list of tries and times.
 * 
 */
class Problem implements Comparable<Problem> {
    private String type; // Must be "Self-Check" or will be interpreted to be "Exercise"
    private int chapter; // Chapter in BJP 
    private int number; // Problem number in BJP chapter
    private String name; // Problem name
    private boolean ifCompleted; // Successfully completed
    private LocalDateTime date; // timestamp when completed
    private Map<String, Integer> tries; // map of userName -> number of attempts on problem
    private Map<String, Integer> codeHash; // map of userName -> hash value of problem
    private Map<String, Long> times; // map of userName -> seconds to do problem
    private String redFlag; // code that's a red flag for cheating

    // Set this to problem number & student to output the hash values for the cheater files & a student
    static boolean ifDebugHash = false;
    static int chapterDebugHash = 11;
    static int problemDebugHash = 13;
    static String userDebugHash = "nguyentina";
    static String cheaterDebugHash = "**ramakastriot";

    /**
     * Full constructor with most all fields
     *  Maps are created blank
     *  
     * @param type
     * @param chapter
     * @param number
     * @param ifCompleted
     * @param date
     * @param name 
     */
    public Problem(String type, int chapter, int number, boolean ifCompleted, LocalDateTime date, String name) {
        super();
        this.type = type;
        this.chapter = chapter;
        this.number = number;
        this.ifCompleted = ifCompleted;
        this.date = date;
        this.tries = new HashMap<String, Integer>();
        this.codeHash = new HashMap<String, Integer>();
        this.times = new HashMap<String, Long>();
        this.redFlag = null;
        this.name = name;
    }

    /**
     * Simple constructor used for just reading problem definitions
     *   and not student problem completions
     *   
     * @param type
     * @param chapter
     * @param number
     */
    public Problem(String type, int chapter, int number) {
        this(type, chapter, number, false, LocalDateTime.now(), null);
    }
    
    public Problem(String type, int chapter, int number, String name) {
        this(type, chapter, number, false, LocalDateTime.now(), name);
    }
    
    public Problem(String type, int chapter, int number, boolean ifCompleted, LocalDateTime date) {
        this(type, chapter, number, ifCompleted, date, null);
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getChapter() {
        return chapter;
    }
    public void setChapter(int chapter) {
        this.chapter = chapter;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDateTime getDate() {
        return this.date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isIfCompleted() {
        return ifCompleted;
    }
    public void setIfCompleted(boolean ifCompleted) {
        this.ifCompleted = ifCompleted;
    }
    
    public Map<String, Integer>getTries() {
        return this.tries;
    }

    public Map<String, Integer> getCodeHash() {
        return this.codeHash;
    }

    public Map<String, Long> getTimes() {
        return this.times;
    }

    public String getRedFlag() {
        return this.redFlag;
    }

    public void setRedFlag(String redFlag) {
        this.redFlag = redFlag;
    }

    @Override
    public String toString() {
        if (type.equals("Self-Check")) 
            return "SC " + chapter + ":" + number + " ";
        else
            return "Ex " + chapter + ":" + number + " ";
    }

    @Override
    public int compareTo(Problem p) {
        // Self-Check before Exercise
        if (!p.type.equals(this.type)) {
            // reverse the normal string order
            return p.type.compareTo(this.type);
            //                              int pType = p.type.equals("Self-check") ? 1 : 2;
            //                              int tType = this.type.equals("Self-check") ? 1 : 2;
            //                              return tType < pType ? -1 : 1;
        }
        // Chapters first
        if (p.chapter != this.chapter) {
            return this.chapter < p.chapter ? -1 : 1;
        }
        // then Numbers
        if (p.number != this.number) {
            return this.number < p.number ? -1 : 1;
        }
        // must be equal
        return 0;

    }
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true  
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Student or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Problem)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members 
        Problem p = (Problem) o;

        Boolean f = p.getType().equalsIgnoreCase(this.getType()) &&
                p.getChapter() == this.getChapter() &&
                p.getNumber() == this.getNumber();

        return (f);

    }

    /**
     * splitProblemNumber
     * 
     * Splits "3:10" into array of chapter, verse
     * 
     * @param token
     * @return
     *      int array[0] = chapter.  If chapter = 3G, chapter reassigned to unused chapter 20
     *      int array[1] = verse.  If problem = 11a (or any 2 digit + a/b/c...), problem reassigned 100 + 11 + 1=a,2=b
     */
    public static int[] splitProblemNumber(String token) {
        int[] chapterVerse = new int[2];
        String[] problem = token.split("[ .:]+");
        if (problem[0].length() == 2 && problem[0].charAt(1) == 'G') {
            // this must be Chapter 3G
            // Currently chapters can only be integers, so assign it chapter 20
            chapterVerse[0] = 20;
        } else
            chapterVerse[0] = Integer.parseInt(problem[0]);
        if (problem[1].length() == 3 && !Character.isDigit(problem[1].charAt(2))) {
            // this must be a problem 11a, etc
            // Currently problems can only be integers, so assign it problem 100+11+a=1,b=2,etc
            chapterVerse[1] = 100 + Integer.parseInt(problem[1].substring(0, 2)) + problem[1].charAt(2) - 'a';
        } else
            chapterVerse[1] = Integer.parseInt(problem[1]);
        return chapterVerse;
    }

    /**
     * readAssignedProblems - reads problem list from file
     * 
     * Assumes file starts with "type" which is either "SC" for self-check or anything else is treated as an Exercise
     * 
     * This can handle either a list of multiple problems after a SC/EX type
     *  SC 1.1 1.5 1.7 Ex 1.1 1.2 1.10
     * or a type before each problem
     *  SC 1.1 SC 1.5 SC 1.7 Ex 1.1 Ex 1.2 Ex 1.10
     * 
     * @return ArrayList of Problems assigned
     * @throws FileNotFoundException
     */
    public static ArrayList<Problem> readAssignedProblems() throws FileNotFoundException {
        // If "Assigned Problems.txt" exists, load it, use it at end in printing
        // Exercise  9:11  9:4  9:9 Self-Check  9:10  9:3  9:8  9:9 
        // SC 1:2 3:4 Ex 5:6 7:8
        ArrayList<Problem> problemList = new ArrayList<Problem>();
        File fProblems = new File("Assigned Problems.txt");
        if (fProblems.canRead()) {
            Scanner scProblems = new Scanner (fProblems);
            String token = scProblems.next();
            String problemType = token;

            // loop with "current" token which could be problem type or chapter:verse
            do {
                // if not number, must be problem type
                if (!Character.isDigit(token.charAt(0))) {
                    if (token.equals("SC"))
                        problemType = "Self-Check";
                    else
                        problemType = "Exercise";
                }
                else 
                {
                    int[] chapterVerse = Problem.splitProblemNumber(token);
                    problemList.add(new Problem(problemType, chapterVerse[0], chapterVerse[1]));
                }
                // this is for the next outer loop
                token = scProblems.hasNext() ? scProblems.next() : null;
            } while (token != null);
            // go back to next problem type or end
        }
        
        ////////////////////////////////////////////////////////////////////
        // Read file of cheater problems
        //
        // Cheaters.txt
        //
        // **userName Exercise 12:18 result.lastIndexOf
        // public static void waysToClimb(int n) {
        //     waysToClimb(n, 0, "[");
        // }
        //
        // ** flags beginning of line
        // last single token (no whitespace) is a red flag for cheaters
        // code will continue until next ** at head of line
        ////////////////////////////////////////////////////////////////////
        File fCheaters = new File("Cheaters.txt");
        if (fCheaters.canRead()) {
            Scanner scCheaters = new Scanner (fCheaters);
            String type = null;
            int[] chapterVerse = null; // Used as flag for reading code
            String userName = null;
            int codeHash = 0;
            String redFlag = null;
            while (scCheaters.hasNextLine()) {
                String line = scCheaters.nextLine();

                //////////////////////////////////////////////////////////////
                // Starting New Problem 
                //   "**" <username> <Exercise | Self-Check> <Chapter>:<Verse> <redFlag>                    
                //////////////////////////////////////////////////////////////
                if (line.length() >= 2 && line.indexOf("**") == 0) {
                    ///////////////////////////////////
                    // End of code - store codeHash & redFlags into assigned problem  
                    ///////////////////////////////////
                    if (chapterVerse != null) {
                        // create temp problem just to find it in list of assigned
                        int index = problemList.indexOf(new Problem(type, chapterVerse[0], chapterVerse[1]));
                        if (index != -1) { // maybe it's not assigned
                            problemList.get(index).getCodeHash().put(userName,  codeHash);
                            if (redFlag != null)
                                problemList.get(index).setRedFlag(redFlag);
                        }
                        redFlag = null;
                        codeHash = 0;
                        chapterVerse = null;
                    }
                    
                    // read fields for new problem
                    Scanner sc = new Scanner(line);
                    userName = sc.next();
                    type = sc.next();
                    // validate type
                    if (!type.equals("Self-Check") && !type.equals("Exercise")) {
                        System.out.println("ERROR - expecting new problem, found " + userName + type);
                    }
                    chapterVerse = Problem.splitProblemNumber(sc.next());
                    
                    ///////////////////////////////////
                    // redFlag is optional last argument
                    ///////////////////////////////////
                    if (sc.hasNext()) {
                        redFlag = sc.next();
                        // grab everything between quotes if they exist
                        if (redFlag.startsWith("\"")) {
                            int first = line.indexOf(redFlag);
                            int last = line.lastIndexOf('"');
                            if (last == -1 || last == first)
                                System.out.printf("ERROR: cheater %s missing end quote for red flag %s",userName, line);
                            redFlag = line.substring(line.indexOf(redFlag)+1, line.lastIndexOf('"'));
                        } else
                        // verify that this is the end of the line
                        if (sc.hasNext())
                            System.out.printf("ERROR: unexpected %s in cheaters.txt\n", sc.next());
                    }
                } else {
                    // validate that we aren't missing a problem declaration
                    if (chapterVerse == null)
                        System.out.printf("ERROR: expected new problem **username but found %s\n", line);
                    codeHash += computeCodeHash(line, false, false);
                    
                    // debughash breakpoint - enable to see why hash doesn't match student
                    if (ifDebugHash && chapterVerse[0] == chapterDebugHash && chapterVerse[1] == problemDebugHash 
                            && type.equals("Exercise") && userName.equals(cheaterDebugHash))
                        System.out.printf("cheater %s Ex %d.%d codehash %d\n", userName, chapterVerse[0], chapterVerse[1], codeHash);
                } // end in code
            } // end line
        } // end reading cheaters
        return problemList;
    }

    /**
     * Strips the "" from around a quoted string from a CSV file
     * 
     * @param quoted - "Hello"
     * @return Hello
     */
    public static String stripQuotes(String quoted) {
        if (PracticeItGrader.ifDebug && 
                (quoted.charAt(0) != '"' || quoted.charAt(quoted.length()-1) != '"'))
            System.out.printf("stripQuotes - encountered %s but expected quotes around it", quoted);
        return quoted.substring(1, quoted.length()-1);
    }

    public static enum CSV {USER, LAST, FIRST, PROBLEM, SOLVED, DATETIME, TRIES, CODE} 

    /**
     * Splits the PracticeIt Problem combined descriptor into separate fields
     * 
     * CSV gives us whole "BJP4 Exercise 8.07: addTimeSpan" which needs to be split out
     *                       0       1      2        3
     * 
     * @param results - array of CSV fields, 4th item contains the problem description
     * @param chapterVerse - modified here to hold the chapter & problem number
     * @return String with Exercise/Self-Check
     */
    public static String splitPIProblem(String results[], int[] chapterVerse) {
        // Field PROBLEM needs to be split up
        // CSV gives us whole "BJP4 Exercise 8.07: addTimeSpan" which needs to be split out
        //                      0       1      2        3
        String piTypeCombined = results[CSV.PROBLEM.ordinal()];
        String[] piTypes = piTypeCombined.split("[ ]+");

        // Validate problem format
        if (piTypes.length != 4 ||
                !piTypes[0].substring(0, 3).equals("BJP") ||
                (!(piTypes[1].equals("Exercise") || piTypes[1].equals("Self-Check"))))
            System.out.printf("readProblems unexpected problem format: %s\n", piTypeCombined);

        // Problem Subfield 2: Split 1:10 into 1 and 10
        int[] chapterVerseTemp = Problem.splitProblemNumber(piTypes[2]);
        System.arraycopy(chapterVerseTemp, 0, chapterVerse, 0, chapterVerseTemp.length);

        // Problem Subfield 0: We ignore BJPX field
        // Problem Subfield 3: We ignore problem name for now
        // Problem Subfield 1: Exercise or Self-Check
        return piTypes[1];
    }
    
    /**
     * Computes the hash value for a line of code
     *   can remove leading or trailing quote mark
     *   will convert BJP .CSV quote style of ""hello"" -> "hello"
     * 
     * @param code
     * @param fRemoveLeadingQuote - line starts with a leading quote eg "public static void main
     * @param fRemoveTrailingQuote - line ends with a trailing quote eg }"
     */
    public static int computeCodeHash(String line, boolean fRemoveLeadingQuote, boolean fRemoveTrailingQuote) {
        int codeHash = 0;
        if (fRemoveLeadingQuote || fRemoveTrailingQuote)
            line = line.substring((fRemoveLeadingQuote ? 1 : 0), line.length() - (fRemoveTrailingQuote ? 1 : 0));
        line = line.replace("\"\"", "\"");
        Scanner scCode = new Scanner (line);
        while (scCode.hasNext()) {
            String token = scCode.next();
            codeHash += token.hashCode();
        }

        return codeHash;
    }
    
    /**
     * readProblems - reads all problems from PracticeIt student results, stores into class arrays
     * 
     * Format of header line
     *        "Username","Last","First","Problem","Solved?","Date/Time","Tries","Solution Code"
     *             0        1      2        3         4         5          6         7
     *        "abcmoney6","Doe","John","BJP4 Exercise 8.07: addTimeSpan","No","2019-09-25 16:45:33","1","//test code"
     * After header is usually code lines which are skipped
     * 
     * @param studentList - list of students we want to read problems for, all others ignored
     * @param assignedProblems - assigned Problems - needed to watch for red flags
     */
    public static ArrayList<Student> readProblems(
            ArrayList<Student> studentList,
            ArrayList<Problem> assignedProblems) throws FileNotFoundException {
        boolean ifInCode = false;
        int problemNum = 1;
        String line; // raw line
        String results[]; // split line
        String ignoredStudent = "";   // debug only
        PrintStream ps = null;
        String redFlag = null;

        if (PracticeItGrader.ifDebug) {
            System.out.println();
            System.out.println("readProblems Begin");
        }

        // If no list of class members, must initialize this 
        Boolean ifClassList = studentList == null ? false : true;
        if (studentList == null)
            studentList = new ArrayList<Student>();

        File f = new File("practice-it.csv");
        if (!f.canRead()) {
            System.out.println("Can't find file");
        }
        Scanner sc = new Scanner(f);

        // If we're encrypting the student usernames, write the file out
        if (PracticeItGrader.ifEncrypt) {
            File fEncrypt = new File("Encrypted Results.txt");
            ps = new PrintStream(fEncrypt);
        }

        // get headers & verify
        line = sc.nextLine(); 
        results = line.split("[,]+");
        if (results.length != 8 || 
                !stripQuotes(results[CSV.USER.ordinal()]).equals("Username") ||
                !stripQuotes(results[CSV.LAST.ordinal()]).equals("Last") ||
                !stripQuotes(results[CSV.CODE.ordinal()]).equals("Solution Code"))
            System.out.printf("readProblems expected first line to be headers but found %s",line);

        // These variables can exist between lines if we're scanning code lines
        Problem currentProblem = null;
        String userName = null;
        int[] chapterVerse = new int[2];
        int codeHash = 0; // hash of code
        String type = null;
        int studentNum = -1;
        int codeLength = 0;
        
        // Loop through all lines
        while (sc.hasNextLine()) {
            ///////////////////////////////////////////////////////
            // Read lines
            ///////////////////////////////////////////////////////

            line = sc.nextLine();
            // debugging help - put the item item # here and set breakpoint 
//            if (problemNum == 246)
//             System.out.println(line);               

            // If we're in code, need to skip lines.
            if (ifInCode) {
                // debugging for codehash for a particular student & problem
//                if (fInProblem(currentProblem, 12, 18)  && userName.equals("studentname")) {
//                    System.out.println("breakpoint");
//                }
                
                //////////////////////////////////////////////////////////////
                //   Code ends with a line ending in one quote
                //////////////////////////////////////////////////////////////
                if (line.length() != 0) {
                    if (line.charAt(line.length()-1) == '"' &&
                            line.charAt(line.length()-2) != '"') {
                        ////////////////////////////////////////
                        // Store code hash of this problem
                        ////////////////////////////////////////
                        codeHash += computeCodeHash(line, false, true);
                        codeLength += line.length();
                        // If you want to remove short code lengths, use this but it makes
                        //   it harder to catch cheating for large datasets
//                        if (codeLength < 200)
//                            codeHash = 0;
                        currentProblem.getCodeHash().put(userName,  codeHash);
                        
                        // 1 of 3 debughash printing to determine why codeHash doesn't match cheater
                        if (ifDebugHash && chapterVerse[0] == chapterDebugHash && chapterVerse[1] == problemDebugHash && type.equals("Exercise") && userName.equals(userDebugHash))
                            System.out.printf("user %s Ex %d.%d codeHash %d\n", userName, chapterVerse[0], chapterVerse[1], codeHash);
                            
                        // reset code status
                        codeHash = 0;
                        redFlag = null;
                        ifInCode = false;
                        codeLength = 0;
                    } else {
                        // calculate code has by adding hash of each token
                        codeHash += computeCodeHash(line, false, false);
                        codeLength += line.length();
                        
                        // 2 of 3 debughash printing to determine why codeHash doesn't match cheater
                        if (ifDebugHash && chapterVerse[0] == chapterDebugHash && chapterVerse[1] == problemDebugHash && type.equals("Exercise") && userName.equals(userDebugHash))
                            System.out.printf("user %s Ex %d.%d codeHash %d\n", userName, chapterVerse[0], chapterVerse[1], codeHash);
                    }
                } 
                ////////////////////////////////////
                // check for any red flags
                ////////////////////////////////////
                if (redFlag != null && line.contains(redFlag)) {
                    PracticeItGrader.flagCheater(studentList, userName, currentProblem, "Red Flag");
                }

                // If encrypting file, still need to output code lines
                if (PracticeItGrader.ifEncrypt && ifInCode)
                    ps.println(line);

                continue; // go to next line
            } // end code 
            
            // Print out each problem header
            if (PracticeItGrader.ifDebug) {
                System.out.printf("%d, %s\n", problemNum, line);
                // Enable this to stop at a particular line
//                if (problemNum == 2469)
//                    System.out.println("Breakpoint here");
            }
            
            ///////////////////////////////////////////////////////
            // Code starts by ending a line without a quote
            ///////////////////////////////////////////////////////
            if (line.charAt(line.length()-1) != '"' 
                    //   except for the weird case where it just has a single quote to open the code on next line
                    //   which we can identify by a comma right before the quote
                    || line.charAt(line.length()-2) == ',' 
                    //   or the line ends with a double ""
                    || line.charAt(line.length()-2) == '"') {
                ifInCode = true;
                codeHash = 0;
            }
            problemNum++;

            ///////////////////////////////////////////////////////
            // Split header into fields needed to construct Student & Problem 
            ///////////////////////////////////////////////////////

            // Format of line
            // 
            // This is the new CSV format that we are given
            // "Username","Last","First","Problem","Solved?","Date/Time","Tries","Solution Code"
            // New  0        1      2        3         4         5          6         7
            // "abcmoney6","Doe","John","BJP4 Exercise 8.07: addTimeSpan","No","2019-09-25 16:45:33","1","//test code"

            // Split header into fields and strip quotes
            results = line.split("[,]+");
            for (int index = CSV.USER.ordinal(); index < CSV.CODE.ordinal(); index++)
                results[index] = stripQuotes(results[index]);

            // Field USER, LAST, FIRST used directly
            userName = results[CSV.USER.ordinal()];
            String lastName = results[CSV.LAST.ordinal()];
            String firstName = results[CSV.FIRST.ordinal()];
            int tries = Integer.parseInt(results[CSV.TRIES.ordinal()]);

            // if encrypting, output the line but replace student names with encrypted
            if (PracticeItGrader.ifEncrypt) {
                line = line.replace(userName, Student.toHash(userName));
                line = line.replace(lastName, Student.toHash(lastName));
                line = line.replace(firstName, Student.toHash(firstName));
                ps.println(line);
            }

            type = splitPIProblem(results, chapterVerse);

            // Field SOLVED is either Y or N
            Boolean comp = results[CSV.SOLVED.ordinal()].toUpperCase().charAt(0) == 'Y';

            // Field DATETIME, TRIES, CODE unused at this time
            // Eventually we should examine DATETIME to find average time taken & unusual times
            
            // calculate code hash by adding hash of each token
            String code = results[CSV.CODE.ordinal()];
            // a comma inside the code quote will fool the splitter - concat extra params
            if (results.length > CSV.CODE.ordinal()+1) {
                int index = CSV.CODE.ordinal()+1;
                while (index < results.length) {
                    code += ", " + results[index++];
                }
            }
            // validate and remove leading double quote on first line of code
            if (code == null || code.charAt(0) != '"') {
                System.out.println("Error: Code should start with \" but instead found " + code);
            }

            ////////////////////////////////////////////////////////////
            // compute code hash on initial line to check for cheating
            ////////////////////////////////////////////////////////////
            if (code.length() > 1) {  // line could start or end without any code 
                // Validate that it's just a quote
                if (code.charAt(0) != '"')
                    System.out.println("ERROR: Expected only char on code line was quote but found " + code.charAt(0) + " on line " + line);
                codeHash += computeCodeHash(code, true, false);
                codeLength += code.length();
            }
            // 3 of 3debughash printing to determine why codeHash doesn't match cheater
            if (ifDebugHash && chapterVerse[0] == chapterDebugHash && chapterVerse[1] == problemDebugHash && type.equals("Exercise") && userName.equals(userDebugHash))
                System.out.printf("user %s Ex %d.%d codeHash %d\n", userName, chapterVerse[0], chapterVerse[1], codeHash);
            
            ///////////////////////////////////////////////////////
            // Done reading the file, now creating data structures 
            ///////////////////////////////////////////////////////
            
            // heavy debugging - print out parsed fields
            // System.out.printf("user=%s, first=%s, last=%s, type=%s, chapter=%d, verse=%d, solved=%b\n", userName, firstName, lastName, type, chapterVerse[0], chapterVerse[1], comp);

            // build or lookup the student
            Student s = new Student(userName, firstName, lastName);
            studentNum = studentList.indexOf(s);
            if (!ifClassList) {
                if (studentNum == -1)
                    // Build Class List if not supplied
                    studentList.add(s);
            } else {
                // Print student's name once, skip repeats
                if (PracticeItGrader.ifDebug && !s.getUserName().equalsIgnoreCase(ignoredStudent)) {
                    if (ignoredStudent.length() != 0)
                        // No need for line break after problems if this is the first student 
                        System.out.println();
                    System.out.println((studentNum == -1 ? "Skipping " : "Starting ") + s);
                    // We don't want to see this student name anymore
                    ignoredStudent = s.getUserName(); 
                    // reset problem type for correct linebreaks
                    // currentPIType = null; see below for old usage 
                }
                if (studentNum != -1)
                    // Find existing student record
                    s = studentList.get(studentNum);
            }
            
            // Get time problem was submitted
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "yyyy-MM-dd HH:mm:ss" , Locale.ENGLISH );
            LocalDateTime date = LocalDateTime.parse(results[CSV.DATETIME.ordinal()], formatter);

            // Add problem to the student
            currentProblem = new Problem(type, chapterVerse[0], chapterVerse[1], comp, date);
            if (studentNum != -1) {
                s.getProblems().add(currentProblem);
            }

            //////////////////////////////////////////////////////////////////
            // Cheat Checking
            //////////////////////////////////////////////////////////////////
            // Set number of tries
            currentProblem.getTries().put(userName,  tries);

            // If student has a one-liner this is the only place we store the codeHash
            currentProblem.getCodeHash().put(userName,  codeHash);

            // get any red flag string for the assigned problem - once per problem 
            int assignedProbIndex = assignedProblems.indexOf(currentProblem);
            if (assignedProbIndex != -1)
                redFlag = assignedProblems.get(assignedProbIndex).getRedFlag();

        }
        if (PracticeItGrader.ifDebug) {
            System.out.println();
            System.out.println("readProblems End");
        }

        return studentList;     // in case it was null to being with
    }
    
    /**
     * Debugging helper to set breakpoints at a particular problem
     * 
     * @param p
     * @param chapter
     * @param number
     * @return
     */
    public static boolean fInProblem(Problem p, int chapter, int number) {
        return p.getChapter() == chapter && p.getNumber() == number;
    }
}
    


