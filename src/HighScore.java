public class HighScore
{
    private int currentScore;
    private String name;
    
    HighScore()
    {
	currentScore = 0;
    }

    //Called when game is over
    public void syncScore()
    {
	
    }

    //Used to get current highscores
    public List<int,String> getScoreTable();
    {
	List<int,String> scoreTable = 0;
	return scoreTable;
    }

    //reset score before next game
    public void reset()
    {
	currentScore = 0;
    }
    
    //called after every move or after the game, to set the score
    public void addToScore(int points);
    {
	currentScore = currentScore + points;
    }

    //to get current score, if not stored elsewhere
    public int getCurrentScore();
    {
	return currentScore;
    }

    //used to set the name for the score table
    public void setName(String newName)
    {
	name = newName;
    }

    //to get the name, if it is to be shown while playing
    public String getName();
    {
	return name;
    }
