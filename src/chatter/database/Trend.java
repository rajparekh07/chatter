package chatter.database;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Trend extends Model {
    public static String TABLE_NAME = "trends";

    public int id;

    public int chirp_id;

    public String data;

    public long timestamp;

    public Trend(int chirp_id, String data) {
        this.chirp_id = chirp_id;
        this.data = data;
        this.timestamp = chatter.utils.Timestamp.now();
    }

    public Trend(int id, int chirp_id, String data, long timestamp) {
        this.id = id;
        this.chirp_id = chirp_id;
        this.data = data;
        this.timestamp = timestamp;
    }

    public Trend(ResultSet rs) throws SQLException {
        this(rs.getInt("id"), rs.getInt("chirp_id"), rs.getString("data"), rs.getLong("created_at"));
    }

    @Override
    public int save() throws Exception {
        String q = "INSERT INTO " + TABLE_NAME + " (chirp_id, data, created_at) VALUES ('" +
                this.chirp_id + "','" +
                this.data + "','" +
                this.timestamp + "')";
        return Database.init().query(q).fireUpdateWithID();
    }

    public static List<Chirp> findChirps (int trend_id) throws Exception {
        String q = "SELECT * FROM chirps WHERE id IN ( SELECT chirp_id FROM trends t WHERE t.id = " + trend_id + ")";
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
