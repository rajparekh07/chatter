package chatter.database;

import chatter.utils.Escaper;

import java.sql.ResultSet;

public class Service extends Model{
    protected static String TABLE_NAME = "services";

    public int id;

    public String name;


    public Service() {

    }

    public Service(String name) {

        this.name = name;

    }

    public Service(int id, String name) {

        this.id = id;
        this.name = name;


    }

    public static Service findService(int id) {
        Service user = null;
        try {
            ResultSet rs = find(id);
            rs.first();
            user = new Service(rs.getInt("id"), rs.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int save() throws Exception {

        String query = "INSERT INTO "+TABLE_NAME+ "(name) VALUES " +
                "('" + this.name + "')";
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

        columnName = Escaper.escapeString(columnName);

        val = Escaper.escapeString(val);

        String query = "SELECT * FROM " + TABLE_NAME  + " WHERE `" + columnName + "` = '" + val + "'";
        return Database.init().query(query).fireSelect();
    }

}
