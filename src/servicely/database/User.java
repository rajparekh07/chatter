package servicely.database;

import servicely.utils.Escaper;

import java.sql.ResultSet;


public class User extends Model{

    protected static String TABLE_NAME = "users";

    public int id;

    public String name;

    public String email;

    public String mobileNumber;

    public String password;

    public User() {

    }

    public User(String name, String email, String mobileNumber, String password) {

        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public User(int id, String name, String email, String mobileNumber, String password) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.password = password;
    }

    public User(ResultSet rs) throws Exception{
        rs.first();
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.email = rs.getString("email");
        this.mobileNumber = rs.getString("mobile_number");
        this.password = rs.getString("password");
    }
    public static User findUser(int id) {
        User user = null;
        try {
            ResultSet rs = find(id);
            rs.first();
            user = new User(rs.getInt("id"), rs.getString("name"),rs.getString("email") , rs.getString("mobile_number"), rs.getString("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean attemptLogin(String email, String password) throws Exception{
        try {
            User user = where("email", email);
            String userPassword = user.password;
            return password.equals(userPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int save() throws Exception {

        String query = "INSERT INTO "+TABLE_NAME+ " (name, email, mobile_number, password) VALUES " +
                "('" + this.name + "','" +
                this.email + "','" +
                this.mobileNumber + "','" +
                this.password + "')";
        System.out.println(query);
        return Database.init().query(query).fireUpdate();
    }

    @Override
    public String getTableName() throws Exception {
        return TABLE_NAME;
    }


    public static ResultSet find(int id) throws Exception{
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE `id` = "+id;
        return Database.init().query(query).fireSelect();
    }

    public static ResultSet all() throws Exception {
        String query = "SELECT * FROM " + TABLE_NAME;
        return Database.init().query(query).fireSelect();
    }

    public static User where(String columnName, String val) throws Exception {

        columnName = Escaper.escapeString(columnName);

        val = Escaper.escapeString(val);

        String query = "SELECT * FROM " + TABLE_NAME  + " WHERE `" + columnName + "` = '" + val + "'";
        return new User(Database.init().query(query).fireSelect());
    }


}
