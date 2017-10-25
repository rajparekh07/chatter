package servicely.database;

import servicely.utils.Escaper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Device extends Model{

    protected static String TABLE_NAME = "devices";

    public int id;

    public String name;

    public String type;

    public Device() {

    }

    public Device(ResultSet rs) throws SQLException {
        rs.first();
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.type = rs.getString("type");
    }

    public Device(String name, String type) {

        this.name = name;
        this.type = type;
    }

    public Device(int id, String name, String type) {

        this.id = id;
        this.name = name;
        this.type = type;

    }

    public static Device findDevice(int id) {
        Device user = null;
        try {
            user = new Device(find(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int save() throws Exception {

        String query = "INSERT INTO "+TABLE_NAME+ "(name, type) VALUES " +
                "('" + this.name + "','" +
                this.type + "')";
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

    public static ResultSet where(String columnName, String val) throws Exception {

        return where(columnName, "=", val);
    }

    public static ResultSet where(String columnName,String operator ,String val) throws Exception {

        columnName = Escaper.escapeString(columnName);

        val = Escaper.escapeString(val);

        String query = "SELECT * FROM " + TABLE_NAME  + " WHERE `" + columnName + "` "+ operator +" '" + val + "'";
        return Database.init().query(query).fireSelect();
    }



}
