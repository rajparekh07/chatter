package chatter.database;

import chatter.utils.Escaper;
import chatter.utils.Hasher;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class User extends Model{

    protected static String TABLE_NAME = "users";

    public int id;

    public String name;

    public String email;

    public String password;

    public long timestamp;

    public User() {

    }

    public User(int id, String name, String email, String password, long timestamp) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.timestamp = timestamp;
    }

    public User(String name, String email,  String password) {

        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(int id, String name, String email, String password) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(ResultSet rs) throws Exception{
//        rs.first();
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.email = rs.getString("email");
        this.password = rs.getString("password");
        this.timestamp = rs.getLong("created_at");
    }
    public static User findUser(int id) {
        User user = null;
        try {
            ResultSet rs = find(id);
            rs.first();
//            user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
            user = new User(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean attemptLogin(String username, String password) throws Exception{
        try {
            User user = where("name", username);
            String userPassword = user.password;
            return Hasher.sha1(password).equals(userPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int save() throws Exception {

        if(this.name.length() < 4 || this.email.length() < 5 || this.password.length() < 6) {
            throw new Exception("Please enter proper details.");
        }

        String query = "INSERT INTO "+TABLE_NAME+ " (name, email, password, created_at) VALUES " +
                "('" + this.name + "','" +
                this.email + "','" +
                Hasher.sha1(this.password) + "','" +
                this.timestamp +
                "')";
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
        ResultSet rs = Database.init().query(query).fireSelect();
        rs.first();
        return new User(rs);
    }

    public List followers () {
        List<User> followers = new ArrayList<>();

        String query = " SELECT * FROM " + TABLE_NAME + " u WHERE u.id IN " + "(" +
                "SELECT from_user_id FROM followers WHERE to_user_id = " + this.id +
                ")";

        try {
            ResultSet rs = Database.init().query(query).fireSelect();
            rs.first();

            while (!rs.isAfterLast()) {
                followers.add(new User(rs));
                rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return followers;
    }

    public List<User> following () {
        List<User> following = new ArrayList<>();

        String query = " SELECT * FROM " + TABLE_NAME + " u WHERE u.id IN " + "(" +
                "SELECT to_user_id FROM followers WHERE from_user_id = " + this.id +
                ")";

        ResultSet rs = null;
        try {
            rs = Database.init().query(query).fireSelect();
            rs.first();
            while (!rs.isAfterLast()) {
                following.add(new User(rs));
                rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return following;
    }

    public static User getLoggedInUser (HttpSession session) {
        return (User) session.getAttribute("user");
    }


}
