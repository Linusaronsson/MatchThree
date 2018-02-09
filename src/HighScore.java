import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;
    
public class HighScore
{
    static String url = "jdbc:mysql://sidju.noip.me:3306/java";
    static String username = "java";
    static String password = "testeste";
    static String getScoresQuery = "SELECT * FROM scores ORDER BY scores.score DESC;";
    static String syncQueryStart = "INSERT INTO scores VALUES(";
    static String syncQueryEnd = ");";
    
    private Connection connection;
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

    }

    //Called when game is over
    public void syncScore()
    {
	Statement statement;

	// connect database
	try {
	    connection = DriverManager.getConnection(url, username, password);
	    System.out.println("Database connected!");
	} catch (SQLException e) {
	    throw new IllegalStateException("Cannot connect the database!", e);
	}
	
	// query database
	try {statement = connection.createStatement();
	    statement.executeUpdate(syncQueryStart + currentScore + ", " + "\"" + name + "\"" + syncQueryEnd);
	    System.out.println("Database responded!");
	} catch (SQLException e) {
	    throw new IllegalStateException("Database unresponsive!", e);
	}

	// disconnect database
	if (connection != null) {
	    try {
		connection.close();
		connection = null;
		System.out.println("Database disconnected!");
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    //Used to get current highscores
    public List<Score> getScoreTable()
    {
	Statement statement;
	ResultSet selection;
	Score tmp;

	List<Score> scoreTable = new ArrayList<Score>();

	// connect database
	try {
	    connection = DriverManager.getConnection(url, username, password);
	    System.out.println("Database connected!");
	} catch (SQLException e) {
	    throw new IllegalStateException("Cannot connect the database!", e);
	}
	
	// query database
	try {statement = connection.createStatement();
	    selection = statement.executeQuery(getScoresQuery);
	    System.out.println("Database responded!");
	} catch (SQLException e) {
	    throw new IllegalStateException("Database unresponsive!", e);
	}

	// read response
	try {
	    selection.first();
	    while ( !selection.isAfterLast() )
		{
		    tmp = new Score();
		    tmp.score = selection.getInt("score");
		    tmp.name = selection.getString("name");
		    scoreTable.add(tmp);
		    selection.next();
		}
	} catch (SQLException e) {
	    throw new IllegalStateException("ResultSet non-functional!", e);
	}
	

	// disconnect database
	if (connection != null) {
	    try {
		connection.close();
		connection = null;
		System.out.println("Database disconnected!");
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}		
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
