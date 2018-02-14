package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** ... */
public class HighScore
{
	/** ... */
	static final String GET_SCORES_QUERY =
		"SELECT * FROM scores ORDER BY scores.score DESC;";
	
	/** ... */
	static final String PASSWORD = "testeste";
	
	/** ... */
	static final String SYNC_QUERY_END = ");";
	
	/** ... */
	static final String SYNC_QUERY_START = "INSERT INTO scores VALUES(";
	
	/** ... */
	static final String URL = "jdbc:mysql://sidju.noip.me:3306/java";
	
	/** ... */
	static final String USERNAME = "java";
	
	/** ... */
	private Connection connection = null;
	
	/** ... */
	private int currentScore = 0;
	
	/** ... */
	private String name = null;
	
	/**
	 * ...
	 */
	public HighScore() {
		currentScore = 0;
		
		System.out.println("Loading driver...");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
				"Cannot find the driver in the classpath!",
				e
			);
		}
	}
	
	// Called when game is over.
	/**
	 * ...
	 */
	public void syncScore() {
		Statement statement;
		
		// connect database
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Database connected!");
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		// query database
		try {
			statement = connection.createStatement();
			statement.executeUpdate(
				SYNC_QUERY_START
				+ currentScore
				+
				", "
				+ "\""
				+ name
				+ "\""
				+ SYNC_QUERY_END
			);
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
	
	/**
	 * Get current list of high scores.
	 *
	 * @return ...
	 */
	public String[] getScoreTable() {
		Statement statement;
		ResultSet selection;
		
		List<String> scoreTable = new ArrayList<String>();
		
		// connect database
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Database connected!");
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		// query database
		try {
			statement = connection.createStatement();
			selection = statement.executeQuery(GET_SCORES_QUERY);
			System.out.println("Database responded!");
		} catch (SQLException e) {
			throw new IllegalStateException("Database unresponsive!", e);
		}
		
		// read response
		try {
			selection.first();
			while (!selection.isAfterLast()) {
				scoreTable.add("" + selection.getInt("score") + selection.getString("name"));
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
		return scoreTable.toArray(new String[scoreTable.size()]);
	}
	
	// Reset score before next game.
	/**
	 * ...
	 */
	public void reset() {
		currentScore = 0;
	}
	
	// Called after every move or after the game, to set the score.
	/**
	 * ...
	 *
	 * @param points ...
	 */
	public void addToScore(final int points) {
		currentScore = currentScore + points;
	}
	
	// To get current score, if not stored elsewhere.
	/**
	 * ...
	 *
	 * @return ...
	 */
	public int getCurrentScore() {
		return currentScore;
	}
	
	// Used to set the name for the score table.
	/**
	 * ...
	 *
	 * @param newName ...
	 */
	public void setName(final String newName) {
		name = newName;
	}
	
	// To get the name, if it is to be shown while playing.
	/**
	 * ...
	 *
	 * @return ...
	 */
	public String getName() {
		return name;
	}
}
