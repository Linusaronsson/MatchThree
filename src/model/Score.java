package model;

/**
 * ...
 */
public class Score
{
    //protected to enable HighScore to work on the raw data
    
    /** ... */
    protected String name = null;
	
    /** ... */
    protected int score = 0;
    
    /**
     * ...
     *
     * @return ...
     */
    public String getName() {
	return name;
    }
	
    /**
     * ...
     *
     * @return ...
     */
    public int getScore() {
	return score;
    }
}
