package chatter.database;

import chatter.utils.Escaper;
import chatter.utils.Timestamp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Chirp extends Model {

    protected static String TABLE_NAME = "chirps";

    public int id;

    public String data;

    public long timestamp;

    public int user_id;

    public Chirp() { }

    public Chirp(int id, String data, long timestamp, int user_id) {
        this.id = id;
        this.data = data;
        this.timestamp = timestamp;
        this.user_id = user_id;
    }

    public Chirp(String data, int user_id) {
        this.data = data;
        this.user_id = user_id;
        this.timestamp = Timestamp.now();
    }

    public Chirp(ResultSet rs) throws Exception{
        this (
                rs.getInt("id"),
                rs.getString("data"),
                rs.getLong("created_at"),
                rs.getInt("user_id")
                );
    }

    @Override
    public int save() throws Exception {
        String query = "INSERT INTO " + TABLE_NAME + "(data, user_id, created_at) VALUES ('" +
                this.data + "','" +
                this.user_id + "','" +
                this.timestamp +
                "')";
       return Database.init().query(query).fireUpdateWithID();
    }

    @Override
    public String getTableName() throws Exception {
        return TABLE_NAME;
    }

    public static ResultSet find(int id) throws Exception{
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE `id` = "+id;
        return Database.init().query(query).fireSelect();
    }

    public static Chirp findChirp(int id) throws Exception {
        return new Chirp(find(id));
    }

    public static ResultSet all() throws Exception {
        String query = "SELECT * FROM " + TABLE_NAME;
        return Database.init().query(query).fireSelect();
    }

    public static Chirp where(String columnName, String val) throws Exception {

        columnName = Escaper.escapeString(columnName);

        val = Escaper.escapeString(val);

        String query = "SELECT * FROM " + TABLE_NAME  + " WHERE `" + columnName + "` = '" + val + "'";
        return new Chirp(Database.init().query(query).fireSelect());
    }

    public static List<Chirp> getTimeline (int user_id, int count) throws Exception {
        List<Chirp> timeline = new ArrayList<>();
        List<User> following = User.findUser(user_id).following();
        String userListString = following.stream().map( user -> user.id + "").collect(Collectors.joining(","));
        String query = "SELECT * from " + TABLE_NAME + " where user_id IN (" + userListString + "," + user_id +") order by created_at desc LIMIT "+count;
        ResultSet resultSet = Database.init().query(query).fireSelect();
        resultSet.first();
        while(!resultSet.isAfterLast()) {
            timeline.add(new Chirp(resultSet));
            resultSet.next();
        }

        return timeline;
    }


}
