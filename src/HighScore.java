import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
    
public class HighScore
{
    static String url = "jdbc:mysql://sidju.noip.me:3306/java";
    static String username = "java";
    static String password = "testeste";
    
    private int currentScore;
    private String name;
    
    HighScore()
    {
	currentScore = 0;

	System.out.println("Loading driver...");

	try {
	    Class.forName("com.mysql.jdbc.Driver");
	    System.out.println("Driver loaded!");
	} catch (ClassNotFoundException e) {
	    throw new IllegalStateException("Cannot find the driver in the classpath!", e);
	}
	
	System.out.println("Connecting database...");
	try (Connection connection = DriverManager.getConnection(url, username, password)) {
	    System.out.println("Database connected!");
	} catch (SQLException e) {
	    throw new IllegalStateException("Cannot connect the database!", e);
	}
	
    }

    //Called when game is over
    public void syncScore()
    {
	
    }

    //Used to get current highscores
    public List getScoreTable()
    {
	List scoreTable = new ArrayList<Score>();
	return scoreTable;
    }

    //reset score before next game
    public void reset()
    {
	currentScore = 0;
    }
    
    //called after every move or after the game, to set the score
    public void addToScore(int points)
    {
	currentScore = currentScore + points;
    }

    //to get current score, if not stored elsewhere
    public int getCurrentScore()
    {
	return currentScore;
    }

    //used to set the name for the score table
    public void setName(String newName)
    {
	name = newName;
    }

    //to get the name, if it is to be shown while playing
    public String getName()
    {
	return name;
    }
}
