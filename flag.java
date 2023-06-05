/**
 * Flag is a record flagging a potential cheating incident
 * 
 * Version 2.0.1 - 5/31/20 cleanup - added Flag class, catch null pointers
 * Version 2.0.2 - 6/5/20 print cleanup - added equals for red flag searches
 *
 */
public class Flag {
    // No username as it will be held by a particular user
    private Problem problem; // problem where cheating occurred (e.g. Ex 12:18)
    private String reason;  // description of type of cheating (e.g. "Tries")

    
    /**
     * Construct cheating Flag from the problem and reason
     * 
     * @param problem
     * @param reason
     */
    public Flag(Problem problem, String reason) {
        super();
        this.problem = problem;
        this.reason = reason;
    }
    
    public Problem getProblem() {
        return this.problem;
    }
    public void setProblem(Problem problem) {
        this.problem = problem;
    }
    public String getReason() {
        return this.reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return this.problem + this.reason; 
    }
    
    @Override
    /**
     * Flag.equals compares the reason only
     *     This is used to check if a "red flag" has already been filed so problem is not desired
     */
    public boolean equals(Object o) {
        if (o == this)
            return true;
        
        Flag f = (Flag)o;
        
        return (f.getReason() == this.getReason());
    }
}
