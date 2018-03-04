package matchthree.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ...
 *
 * @author David Olofsson
 * @author Erik Selstam
 */
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
	 *
	 * @author David Olofsson
	 */
	public HighScore() {
		currentScore = 0;
		
		System.out.println("Loading driver...");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (final ClassNotFoundException exception) {
			throw new IllegalStateException(
				"Cannot find the driver in the classpath!",
				exception
			);
		}
	}
	
	/**
	 * ...
	 *
	 * @author David Olofsson
	 * @param name  Player name.
	 * @param score Player score.
	 */
	public void syncScore(final String name, final int score) {
		currentScore = score;
		this.name = name;
		syncScore();
	}
	
	/**
	 * ...
	 *
	 * @author David Olofsson
	 */
	public void syncScore() {
		Statement statement;
		
		// Connect to database //
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Database connected!");
		} catch (final SQLException exception) {
			throw new IllegalStateException(
				"Cannot connect the database!",
				exception
			);
		}
		
		// Query database //
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
		} catch (final SQLException exception) {
			throw new IllegalStateException(
				"Database unresponsive!",
				exception
			);
		}
		
		// Disconnect from database //
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				System.out.println("Database disconnected!");
			} catch (final SQLException exception) {
				exception.printStackTrace();
			}
		}
	}
	
	/**
	 * Get current list of high scores.
	 *
	 * @author David Olofsson
	 * @return ...
	 */
	public String[] getScoreTable() {
		Statement statement;
		ResultSet selection;
		
		List<String> scoreTable = new ArrayList<String>();
		
		// Connect to database //
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Database connected!");
		} catch (final SQLException exception) {
			throw new IllegalStateException(
				"Cannot connect the database!",
				exception
			);
		}
		
		// Query database //
		try {
			statement = connection.createStatement();
			selection = statement.executeQuery(GET_SCORES_QUERY);
			System.out.println("Database responded!");
		} catch (final SQLException exception) {
			throw new IllegalStateException(
				"Database unresponsive!",
				exception
			);
		}
		
		// read response
		try {
			selection.first();
			while (!selection.isAfterLast()) {
				scoreTable.add(
					"" + selection.getInt("score") + selection.getString("name")
				);
				selection.next();
			}
		} catch (final SQLException exception) {
			throw new IllegalStateException(
				"ResultSet non-functional!",
				exception
			);
		}
		
		// Disconnect from database //
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				System.out.println("Database disconnected!");
			} catch (final SQLException exception) {
				exception.printStackTrace();
			}
		}
		return scoreTable.toArray(new String[scoreTable.size()]);
	}
	
	/**
	 * ...
	 *
	 * @author David Olofsson
	 */
	public void reset() {
		currentScore = 0;
	}
	
	/**
	 * ...
	 *
	 * @author David Olofsson
	 * @param points ...
	 */
	public void addToScore(final int points) {
		currentScore = currentScore + points;
	}
	
	/**
	 * ...
	 *
	 * @author David Olofsson
	 * @return ...
	 */
	public int getCurrentScore() {
		return currentScore;
	}
	
	/**
	 * ...
	 *
	 * @author David Olofsson
	 * @param newName ...
	 */
	public void setName(final String newName) {
		name = newName;
	}
	
	/**
	 * ...
	 *
	 * @author David Olofsson
	 * @return ...
	 */
	public String getName() {
		return name;
	}
}
