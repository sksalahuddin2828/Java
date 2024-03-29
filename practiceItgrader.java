import java.util.*;
import java.io.*;
import java.security.MessageDigest;
import java.time.*;

public class PracticeItGrader {
    // Set to true to output diagnostic debugging info
    static boolean ifDebug = false;
            
    // Set to true to change real student names (George) into hashed letters (AFLTZ)
    static boolean ifEncrypt = false;
    // Set to year,mo,day,h,m,s to calculate # of problems before that time
    static LocalDateTime dtDeadline = LocalDateTime.of(2020,05,20,23,59,59); // LocalDateTime.of(2019,1,20,23,59,59);
    
    public static void main(String[] args) throws FileNotFoundException {
        // Problem class - type, number, time
        ArrayList<Problem> problemList = null;

        // Student class - name, array of problems
        ArrayList<Student> studentList;
        
        // true if a list of student names text file exists to filter the results
        boolean ifClassList;

        studentList = Student.readStudents();
        ifClassList = studentList == null ? false : true;

        // Reads list of assigned problems to filter results by
        problemList = Problem.readAssignedProblems();

        // Reads problems performed by students and stores into studentList, will create list if null
        // problemList passed in for red flag detection
        studentList = Problem.readProblems(studentList, problemList);

        printResults(ifClassList, problemList, studentList);
    }

    public static void printResults(Boolean ifClassList, ArrayList<Problem> problemList, ArrayList<Student> studentList) {
        // Output class list
        int iStudent = 1;
        
        if (ifDebug) {
            System.out.println();
            System.out.println("printResults Begin");
        }
        
        for (Student s : studentList) {
            // Always print out the student usernames, if no class list this is all we'll do
            System.out.printf("%s %s %s #%d ", 
                    // Print encrypted names to hide student info when sharing samples
                    ifEncrypt ? Student.toHash(s.getUserName()) : s.getUserName(), 
                    ifEncrypt ? Student.toHash(s.getFirstName()) : s.getFirstName(), 
                    ifEncrypt ? Student.toHash(s.getLastName()) : s.getLastName(), 
                    iStudent++); 

            // Only print problems if we already have the class list
            if (ifClassList) {
                ArrayList<Problem> assigned = (ArrayList<Problem>) problemList.clone();
                ArrayList<Problem> extras = new ArrayList<Problem>();
                ArrayList<Problem> failed = new ArrayList<Problem>();

                int countAttemptByDeadline = 0, countOld = 0;
                
                // This is an arbitrary # of days to flag "old" problems done for a previous class, etc
                LocalDateTime dtStart = dtDeadline.minusDays(10);
                
                // This is a list of all problem submission times
                LinkedList<LocalDateTime> dates = new LinkedList<>();
                
                for (int iProblem = 0; iProblem<s.getProblems().size(); iProblem++) {
                    Problem p = s.getProblems().get(iProblem);

                    int indexAssignedProblem = problemList.indexOf(p);
                    if (indexAssignedProblem != -1) {
                        Problem assignedProblem = problemList.get(indexAssignedProblem);
                        String studentName = s.getUserName();
                        if (p.getCodeHash().size() > 1)
                            System.out.printf("ERROR - student %s problem %s should only have one code hash\n", studentName, p);
                        assignedProblem.getCodeHash().put(studentName, p.getCodeHash().get(studentName));
                        assignedProblem.getTries().put(studentName, p.getTries().get(studentName));
                    }
                    
                    int iRemove = -1;
                    if (assigned != null && (iRemove = assigned.indexOf(p)) != -1) {
                        // Problem is on the assigned list
                        // check if it's done by deadline
                        if (dtDeadline != null) {
                                if (p.getDate().compareTo(dtDeadline) <= 0)
                                    countAttemptByDeadline++;
                                if (p.getDate().isBefore(dtStart))
                                    countOld++;
                                dates.add(p.getDate());
                        }
                        
                        if (!p.isIfCompleted())
                            failed.add(p);
                        // remove it from list of assigned problems
                        assigned.remove(iRemove);
                    } else {
                        // add it to the extras list
                        extras.add(p);
                    }
                }
                
                // Continue Print # attemped out of assigned, but don't count any extras
                System.out.printf("Attempted %d of %d assigned\n", 
                        s.getProblems().size() - extras.size(), problemList.size());

                Collections.sort(assigned);
                Collections.sort(failed);
                Collections.sort(extras);

                // Print "Missing SC 1.2", "Failed: SC 2.3", "Extras: Ex 3.4"
                System.out.printf("\tMissing %d:", assigned.size());
                for (Problem p: assigned)
                    System.out.printf(p.toString());
                System.out.println();
                System.out.printf("\tFailed %d: ", failed.size());
                for (Problem p: failed)
                    System.out.printf(p.toString());
                System.out.println();
                System.out.printf("\tExtras %d: ", extras.size());
                for (Problem p: extras)
                    System.out.printf(p.toString());
                System.out.println();
                
                // Print how many completed by deadline
                if (dtDeadline != null) {
                    System.out.println("\t" + countAttemptByDeadline + 
                            " assigned attempted before " + 
                            dtDeadline.toString().substring(0,10));
                    System.out.println("\t" + countOld +
                            " done before " + dtStart.toString().substring(0,10));
                }                    

                Collections.sort(dates);
                LocalDateTime dtCurrent = null;
                if (!dates.isEmpty())
                    dtCurrent = dates.getFirst();
                for (LocalDateTime dtNext : dates) {
                    // Find problem based on dtNext and store student's times
                    for (Problem p : s.getProblems()) {
                        if (p.getDate().equals(dtNext)) {
                            // Store this <Student, time> into the original problem list's map
                            int index = problemList.indexOf(p);

                            problemList.get(index).getTimes().put(s.userName, Duration.between(dtCurrent, dtNext).getSeconds());
                        }
                    }
                    dtCurrent = dtNext;
                } // end examining all timestamps
            } // end printing problems for a student in class
            System.out.println(); // newline for each student          

        } // done with all students
        
        System.out.println("********  BY PROBLEM ANALYSIS **********");
        for (Problem p : problemList) {
            
            // Only check the exercises
            if (p.getType().equals("Self-Check"))
                continue;
            
            // debug breakpoint for a specific problem
//          if (Problem.fInProblem(p, 9, 2))
//              System.out.println("Breakpoint");
            
            String result = "";
            boolean fPrintedTimes = false;
            
            // We have a map of all <Student Name, duration in min>
            // Sort map into ArrayList based on durations 
            List<Map.Entry<String, Long>> studentTimeEntries = new ArrayList<> (p.getTimes().entrySet());
            studentTimeEntries.sort(Map.Entry.comparingByValue());


            // Eliminate bottom 0's which are student's first problems
            while (studentTimeEntries.size() != 0 && studentTimeEntries.get(0).getValue() == 0)
                studentTimeEntries.remove(0);

            // Eliminate top times 
            //    > 60 min which are likely after a break
            //    > 3x median outliers
            int last = studentTimeEntries.size();
            while (last != 0) {
                long median = studentTimeEntries.get(studentTimeEntries.size()/2).getValue();
                long maxTime = Math.max(median,  60*60);
                if (studentTimeEntries.get(last-1).getValue() > maxTime) {
                    studentTimeEntries.remove(last-1);
                    last--;
                } else
                    break;
            }

            if (studentTimeEntries.size() != 0) {
                // Print out the whole range of times to see
                result += String.format("(min)Times for %s are: ", p);
                for (Map.Entry<String, Long>studentEntry : studentTimeEntries)
                    result += String.format(studentEntry.getValue()/60 + " ");
                result += String.format("\n");

                // Examine bottom third and flag times < half of median
                int size = studentTimeEntries.size();
                long medianTime = studentTimeEntries.get(size/2).getValue();
                if (medianTime > 5*60) {// don't  look at easy problems
                    int topTimeIndex = size/3;
                    result += String.format("(min)Times: median %d vs: ", medianTime/60);
                    for (int index = 0; index < topTimeIndex; index++) {
                        if (studentTimeEntries.get(index).getValue() < medianTime / 2) {
                            Map.Entry<String, Long> cheaterEntry = studentTimeEntries.get(index); 
                            result += String.format("%s=%d ", cheaterEntry.getKey(), cheaterEntry.getValue()/60, medianTime/60);
                            flagCheater(studentList, cheaterEntry.getKey(), p, "Times");
                            fPrintedTimes = true;
                        }
                    }
                    result += String.format("\n");  // end Times line
                }
            } // reduced student times exist
            
            if (fPrintedTimes)
                System.out.print(result);
            
            boolean fPrintedTries = false;
            result = null;
            
            List<Map.Entry<String, Integer>> triesEntries = new ArrayList<> (p.getTries().entrySet());
            triesEntries.sort(Map.Entry.comparingByValue());

            if (triesEntries.size() != 0) {
                result += String.format("# Tries for %s are: ", p);
                for (Map.Entry<String, Integer>studentEntry : triesEntries)
                    result += String.format(studentEntry.getValue() + " ");
                result += String.format("\n");

                int medianTries = triesEntries.get(triesEntries.size()/2).getValue();
                if (triesEntries.get(0).getValue() < medianTries / 2) { 
                    result += String.format("# Tries median %d vs: ", medianTries);
                    for (int index = 0; index < triesEntries.size(); index++) {
                        if (triesEntries.get(index).getValue() < medianTries / 2) {
                            Map.Entry<String, Integer> cheaterEntry = triesEntries.get(index); 
                            result += String.format("%s=%d ", cheaterEntry.getKey(), cheaterEntry.getValue());
                            flagCheater(studentList, cheaterEntry.getKey(), p, "Tries");
                            fPrintedTries = true;
                        }
                    }
                    result += String.format("\n"); // new line after tries
                } // cheaters to print
            } // tries
            if (fPrintedTries)
                System.out.print(result);
            
            Map<String, Integer> hashesMap = p.getCodeHash();

            // only look for duplicates if 3/4th are unique
            // only exercises worth checking
            // put into set to determine uniqueness
            
            // debughash - use this to find why duplicate code checking not catching
            if (Problem.ifDebugHash && Problem.fInProblem(p, Problem.chapterDebugHash, Problem.problemDebugHash))
                System.out.println("breakpoint");
            Set<Integer> uniqueHashes = new HashSet<Integer>(hashesMap.values());
            // filtering out problems with common dups prevented seeing true level of cheating
            //    instead you should rely upon large datasets identifying the real cheats
            if (p.getType().equals("Exercise")) { 
                Map<Integer, ArrayList<String>> mapHashToNames = findCommonFlags(hashesMap);
                // loop through each hash
                for (int hash : mapHashToNames.keySet()) {
                    // print list of names
                    if (hash == 0) // ignore code that is too small
                        continue; 
                    System.out.printf("Problem %s: duplicated by: %s\n", p, mapHashToNames.get(hash));
                    // if ** known cheater is in group, change to a red flag
                    String reason = "Code";
                    for (String name : mapHashToNames.get(hash)) {
                        if (name.startsWith("**"))
                            reason = "Red Flag";
                    }
                    for (String name : mapHashToNames.get(hash)) {
                        flagCheater(studentList, name, p, reason);
                        if (name.startsWith("**") == false)
                            System.out.printf("\t%s tried %d times in %d min\n", name, p.getTries().get(name), p.getTimes().get(name)/60);
                    }
                }
            } // looking for duplicate code hash
            if ((fPrintedTimes || fPrintedTries) && p.getTimes().size() != 0)
                System.out.println(); // separating line per problem if we've printed
        } 

        // Sort by cheater scores
        // This uses a anonymous class instead of creating a separate comparator class
        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s2.getCheatingIndex() - s1.getCheatingIndex();
            }
        });

        // Only print top 1/3rd of non-zero items
        if (studentList.size() != 0) {
            int end = studentList.size()-1;
            while (end >= 0 && studentList.get(end).getCheatingIndex() == 0)
                end--;
            System.out.println("********  FINAL CHEATING ANALYSIS **********");
            System.out.printf("Median cheating index was %d, Top 1/3rd suspicious are:\n", 
                    studentList.get(end/2).getCheatingIndex());
            int indexStudent = 0;
            for (; indexStudent < studentList.size() / 3; indexStudent++)
                printStudentCheatingReport(studentList.get(indexStudent));
            
            System.out.printf("********  Other students with red flags ********\n");
            Flag fake = new Flag(null, "red flag");
            for (;indexStudent < studentList.size(); indexStudent++) {
                if (studentList.get(indexStudent).getFlags().contains(fake)) {
                    for (Flag f : studentList.get(indexStudent).getFlags()) {
                        if (f.getReason().equals("red flag")) {
                            System.out.printf("Student %s red flag %s\n",
                                    studentList.get(indexStudent).getUserName(),
                                    f.getProblem());
                        }
                    }
                }
            }
                    
        } // studentList exists
        
        if (ifDebug) {
            System.out.println("printResults End");
        }
    } // end PrintResults
    
    public static void flagCheater(ArrayList<Student> studentList, String cheaterName, Problem p, String reason) {
        int seriousness = 0;
        // Increase student's cheating index
        int index = studentList.indexOf(new Student(cheaterName, null, null));
        if (reason.equalsIgnoreCase("Tries")) {
            seriousness = 1;
        } else if (reason.equalsIgnoreCase("Times")) {
            seriousness = 3;
        } else if (reason.equalsIgnoreCase("Code")) {
            seriousness = 5;
        } else if (reason.equalsIgnoreCase("red flag")) {
            seriousness = 10;
        } else
            System.out.printf("ERROR: unknown cheating flag\n", reason);
        
        if (index != -1) {
            Student s = studentList.get(index);
            s.setCheatingIndex(s.getCheatingIndex()+seriousness);
            Flag f = new Flag(p, reason);
            // Red flags may go off multiple times in a problem - only flag it once
            if (!reason.equals("red flag") || !s.getFlags().contains(f))
                s.getFlags().add(f);
        } 
    } 
    
    public static <T extends Comparable<T>> Map<T, ArrayList<String>> findCommonFlags(Map<String, T> input) {
        List<Map.Entry<String, T>> listNameToFlag = new ArrayList<> (input.entrySet());
        listNameToFlag.sort(Map.Entry.comparingByValue());
        
        Map<T, ArrayList<String>> mapFlagToNames = new HashMap<>();
        
        // loop through each hash backwards so we can remove easily
        for (int index = listNameToFlag.size()-1; index > 0; index--) {
            T item = listNameToFlag.get(index).getValue();
            // if not unique
            if (item.equals(listNameToFlag.get(index-1).getValue())) {
                // create Map entry for the values from the original map
                ArrayList<String> names = new ArrayList<String>();
                mapFlagToNames.put(listNameToFlag.get(index).getValue(), names);
                // add first name to list
                names.add(listNameToFlag.get(index).getKey());
                // remove any duplicates earlier in the list
                while (index > 0 && item.equals(listNameToFlag.get(index-1).getValue())) {
                    names.add(listNameToFlag.get(index-1).getKey());
                    listNameToFlag.remove(index-1);
                    index--;
                }
                // remove first offender
                listNameToFlag.remove(index);
            } // not unique
        } // loop through hashes
        return mapFlagToNames;
    }

    public static void printStudentCheatingReport(Student s) {
        if (s.cheatingIndex == 0)
            return;
        System.out.printf("**%s index %d\n", s.getUserName(), s.getCheatingIndex());
        // Build Map of <reason, list<problem numbers> from list<flags>
        Map<String, ArrayList<String>> mapFlagProb = new HashMap<String, ArrayList<String>>();
        for (Flag flag : s.getFlags()) {
            // build arrayList if first of flag type
            if (!mapFlagProb.containsKey(flag.getReason()))
                mapFlagProb.put(flag.getReason(), new ArrayList<String>());
            mapFlagProb.get(flag.getReason()).add(flag.getProblem().toString()); 
        }
        // Print out each flag type : list of problems
        for (String reason : mapFlagProb.keySet()) {
            System.out.print(reason + ": ");
            for (String problemNum : mapFlagProb.get(reason))
                System.out.printf(problemNum);
            System.out.println();  // new line for each reason
        }
    } 
} 



