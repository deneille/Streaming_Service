import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// REMEMBER TO CONNECT TO MCGILL VPN !!!
public class Main {
	static Statement statement;

	public static void main(String[] args) throws SQLException {
		// Register the driver
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
		} catch (Exception e) {
			System.out.println("Class not found");
		}
		// Set up connection
		String usernamestring = "cs421g46";
		String passwordstring = "LuDaBa46";
		String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
		Connection con = DriverManager.getConnection(url, usernamestring, passwordstring);
		statement = con.createStatement();

		System.out.println("---------WELCOME---------");
		System.out.println();

		// Loop forever, running option that user selects
		while (true) {
			int option = getOption();
			switch (option) {
			case 1:
				getAvailableMedia();
				break;
			case 2:
				getNumEpisodes();
				break;
			case 3:
				createNewPayment();
				break;
			case 4:
				createNextSeason();
				break;
			case 5:
				increaseRockRatings();
				break;
			case 6: // Quit
				// Close the statement and connection
				statement.close();
				con.close();
				System.out.println("---------GOODBYE---------");
				System.exit(0);
			}
		}
	}

	static int getOption() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = -1;

		while (option < 1 || option > 6) {
			System.out.println("Our application offers the following features:");
			System.out.println("	1. List available media for user");
			System.out.println("	2. Get number of episodes in a season for a TV show");
			System.out.println("	3. Create new payment");
			System.out.println("	4. Create next season");
			System.out.println("	5. Increase ratings of all media with The Rock in it by 1");
			System.out.println("	6. Quit");
			System.out.println();
			System.out.print("Enter an option number: ");

			try {
				String s = br.readLine();
				System.out.println();
				option = Integer.parseInt(s);
				if (option < 1 || option > 6) {
					throw new Exception();
				}
			} catch (Exception e) {
				System.out.println("Invalid Input. Try again.");
			}
		}
		return option;
	}

	static void getAvailableMedia() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int accid;
		String username;

		System.out.println("(Try using account ID = 1 and username = Basta)");

		// Get account ID
		System.out.print("Enter an account ID number: ");
		try {
			String s = br.readLine();
			accid = Integer.parseInt(s);
		} catch (Exception e) {
			System.out.println();
			System.out.println("Invalid input. Returning to main menu.");
			return;
		}

		// Get username
		System.out.print("Enter a username: ");
		try {
			username = br.readLine();
			System.out.println();
		} catch (Exception e) {
			System.out.println();
			System.out.println("Invalid input. Returning to main menu.");
			return;
		}

		try {
			// Create query
			String querySQL = "SELECT title, releaseYear FROM accountuser au, available_in avail, media med\r\n"
					+ "WHERE au.accid=" + accid + " AND au.username=\'" + username
					+ "\' AND avail.regname=au.regname AND med.mid=avail.mid;";

			// Execute query
			java.sql.ResultSet rs = statement.executeQuery(querySQL);

			if (rs.next()) {
				System.out.println("Available Media:");
				String name = rs.getString(1);
				int releaseYear = rs.getInt(2);
				System.out.println("	" + name + " (" + releaseYear + ")");

				while (rs.next()) {
					name = rs.getString(1);
					releaseYear = rs.getInt(2);
					System.out.println("	" + name + " (" + releaseYear + ")");
				}
			} else {
				System.out.println("No available media.");
			}
		} catch (SQLException e) {
			String sqlMessage = e.getMessage();
			System.out.println(sqlMessage);
		}
		System.out.println();
	}

	static void getNumEpisodes() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String title;
		int seasonNum;

		System.out.println("(Try using TV show = Pokemon and season number = 1)");

		// Get TV show title
		System.out.print("Enter a TV show title: ");
		try {
			title = br.readLine();
		} catch (Exception e) {
			System.out.println();
			System.out.println("Invalid input. Returning to main menu.");
			System.out.println();
			return;
		}

		// Get season number
		System.out.print("Enter a season number: ");
		try {
			String s = br.readLine();
			System.out.println();
			seasonNum = Integer.parseInt(s);
		} catch (Exception e) {
			System.out.println("Invalid input. Returning to main menu.");
			System.out.println();
			return;
		}

		try {
			// Create query
			String querySQL = "SELECT COUNT(*) \r\n" + "FROM episode e, media m\r\n"
					+ "WHERE e.mid=m.mid AND m.title=\'" + title + "\' AND e.seasonnum=" + seasonNum + ";";

			// Execute query
			java.sql.ResultSet rs = statement.executeQuery(querySQL);

			while (rs.next()) {
				int numEpisodes = rs.getInt(1);
				System.out.println("Number of episodes in season " + seasonNum + ": " + numEpisodes);
			}
		} catch (SQLException e) {
			String sqlMessage = e.getMessage();
			System.out.println(sqlMessage);
		}
		System.out.println();
	}

	static void createNewPayment() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int accid;
		String date;
		Double amount;

		System.out.println("(Try using account ID = 1 and date format YYYY-MM-DD)");

		// Get account ID
		System.out.print("Enter an account ID number: ");
		try {
			String s = br.readLine();
			accid = Integer.parseInt(s);
		} catch (Exception e) {
			System.out.println();
			System.out.println("Invalid input. Returning to main menu.");
			System.out.println();
			return;
		}
		
		// Get Date
		System.out.print("Enter a date: ");
		try {
			date = br.readLine();
		} catch (Exception e) {
			System.out.println();
			System.out.println("Invalid input. Returning to main menu.");
			System.out.println();
			return;
		}

		// Get amount
		System.out.print("Enter an amount: ");
		try {
			String s = br.readLine();
			System.out.println();
			amount = Double.parseDouble(s);
		} catch (Exception e) {
			System.out.println("Invalid input. Returning to main menu.");
			System.out.println();
			return;
		}

		try {
			// Create insert string
			String insertSQL = "INSERT INTO payment VALUES (DEFAULT, \'" + date + "\', " + amount + ", " + accid + ");";

			// Execute insert
			statement.executeUpdate(insertSQL);
			
			// If no error
			System.out.println("Successfully added payment for account with ID #" + accid + " on " + date + " for $" + amount + ".");
			System.out.println();
		} catch (SQLException e) {
			String sqlMessage = e.getMessage();
			System.out.println(sqlMessage);
			System.out.println();
		}
	}
	
	static void createNextSeason() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String title;
		String epTitle;
		int numSeasons;
		int mid;

		System.out.println("(Try using TV show = Pokemon)");

		// Get TV show title
		System.out.print("Enter a TV show title: ");
		try {
			title = br.readLine();
		} catch (Exception e) {
			System.out.println();
			System.out.println("Invalid input. Returning to main menu.");
			System.out.println();
			return;
		}
		
		// Get epTitle
		System.out.print("Your new season needs at least one episode. Enter an episode title: ");
		try {
			epTitle = br.readLine();
			System.out.println();
		} catch (Exception e) {
			System.out.println();
			System.out.println("Invalid input. Returning to main menu.");
			System.out.println();
			return;
		}
		
		try {
			// SEPARATE QUERIES SINCE SINGLE QUERY WON'T WORK IN CASE TV SHOW HAS 0 SEASONS
			
			// Create query for mid
			String querySQL = "SELECT mid \r\n" + 
					"FROM media \r\n" + 
					"WHERE title=\'" + title + "\';";

			// Execute query
			java.sql.ResultSet rs = statement.executeQuery(querySQL);
				
			// Get mid
			if (rs.next()) {
				mid = rs.getInt(1);
			}
			else {
				System.out.println("ERROR: Show does not exist.");
				System.out.println();
				return;
			}

			// Create query for numSeasons
			querySQL = "SELECT COUNT(*) \r\n" + 
					"FROM season s, media m \r\n" + 
					"WHERE s.mid=m.mid AND m.title=\'" + title + "\';";

			// Execute query
			rs = statement.executeQuery(querySQL);
			
			// Get numSeasons
			rs.next();
			numSeasons = rs.getInt(1);

			// Create insert string for season
			String insertSQL = "INSERT INTO season VALUES (" + (numSeasons+1) + ", " + mid + ");";
			// Execute insert
			statement.executeUpdate(insertSQL);
			
			// Create insert string for episode
			insertSQL = "INSERT INTO episode VALUES (1, " + (numSeasons+1) + ", " + mid + ", \'" + epTitle + "\');";
			// Execute insert
			statement.executeUpdate(insertSQL);
		
			// If no error
			System.out.println("Successfully added season " + (numSeasons+1) + " to " + title + " with first episode \"" + epTitle + "\".");
			System.out.println();
			
		} catch (SQLException e) {
			String sqlMessage = e.getMessage();
			System.out.println(sqlMessage);
			System.out.println();
		}
	}
	
	static void increaseRockRatings() {
		try {
			// Create update string
			String updateSQL = "UPDATE rating\r\n" + 
					"SET value = value + 1\r\n" + 
					"WHERE value <= 4 AND mid IN \r\n" + 
					"(\r\n" + 
					"	SELECT mid\r\n" + 
					"	FROM describes d, tag t \r\n" + 
					"	WHERE d.tid=t.tid AND t.tagname='The Rock'\r\n" + 
					");";

			// Execute insert
			statement.executeUpdate(updateSQL);
			
			// If no error
			System.out.println("Successfully increased ratings of all media with The Rock in it by 1.");
			System.out.println();
		} catch (SQLException e) {
			String sqlMessage = e.getMessage();
			System.out.println(sqlMessage);
			System.out.println();
		}
	}
}