package chatter.database;
import java.sql.*;

public class Database {
    private Connection connection;

    private PreparedStatement statement;


    public static Connection getMySqlConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/chatter", "root", "");
    }

    public Database() throws Exception {
        this.connect();
    }

    public static Database init() throws Exception {
        Database db = new Database();
        return db;
    }

    public ResultSet fireSelect() throws SQLException {
        ResultSet resultSet = null;
        resultSet = this.statement.executeQuery();
        return resultSet;
    }

    private void cleanUp() throws SQLException {

        if (this.statement != null) {
            this.statement.close();
        }
        if (this.connection != null) {
            this.connection.close();
        }
    }

    public int fireUpdate() throws SQLException {
        int result = 0;

        result = this.statement.executeUpdate();

        this.cleanUp();

        return result;
    }

    public int fireUpdateWithID() throws SQLException {
        int result = 0;

        result = this.statement.executeUpdate();

        if (result == 0) {
            return 0;
        }

        ResultSet resultSet = this.statement.getGeneratedKeys();
        resultSet.next();
        result = resultSet.getInt(1);

        this.cleanUp();

        return result;
    }

    public Database query(String query) throws Exception {
        if( connection==null ) {
            this.connect();
        }
        System.out.println(query);
        this.createStatement(query);

        return this;
    }

    private Database createStatement(String query) {

        try {
            this.statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    private Database connect() throws Exception{
        connection = Database.getMySqlConnection();
        return this;
    }

}
