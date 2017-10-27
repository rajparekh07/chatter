package chatter.database;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Mention extends Model {
    public static String TABLE_NAME = "mentions";

    public int id;

    public int chirp_id;

    public String data;

    public int user_id;

    public long timestamp;

    public Mention(int chirp_id, int user_id) {
        this.chirp_id = chirp_id;
        this.user_id = user_id;
        this.timestamp = chatter.utils.Timestamp.now();
    }

    public Mention(int id, int chirp_id, int user_id, long timestamp) {
        this.id = id;
        this.chirp_id = chirp_id;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    public Mention(ResultSet rs) throws SQLException {
        this(rs.getInt("id"), rs.getInt("chirp_id"), rs.getInt("user_id"), rs.getLong("created_at"));
    }

    @Override
    public int save() throws Exception {
        String q = "INSERT INTO " + TABLE_NAME + " (chirp_id, user_id, created_at) VALUES ('" +
                this.chirp_id + "','" +
                this.user_id + "','" +
                this.timestamp + "')";
        return Database.init().query(q).fireUpdateWithID();
    }

    public static List<Chirp> findChirps (int user_id) throws Exception {
        String q = "SELECT * FROM chirps WHERE id IN ( SELECT chirp_id FROM mentions t WHERE t.id = " + user_id + ")";
        ResultSet rs = Database.init().query(q).fireSelect();
        rs.first();
        List<Chirp> list = new ArrayList<>();
        while (!rs.isAfterLast()) {
            list.add(new Chirp(rs));
        }

        return list;
    }

    @Override
    public String getTableName() throws Exception {
        return TABLE_NAME;
    }
}
