package chatter.database;

import chatter.utils.Escaper;

import java.sql.ResultSet;

/**
 * Created by Administrator on 05-Apr-17.
 */
public class DeviceService extends Model {

    protected static String TABLE_NAME = "device_service";

    public int id;

    public float cost;

    public int device_id;

    public int service_id;

    public DeviceService() {

    }

    public DeviceService(float cost, int device_id, int service_id) {

        this.cost = cost;
        this.device_id = device_id;
        this.service_id = service_id;
    }

    public DeviceService(int id, float cost, int device_id, int service_id) {

        this.id = id;

        this.cost = cost;
        this.device_id = device_id;
        this.service_id = service_id;
    }

    public static DeviceService findDeviceService(int id) {
        DeviceService user = null;
        try {
            ResultSet rs = find(id);
            rs.first();
            user = new DeviceService(rs.getInt("id"), rs.getFloat("cost"),rs.getInt("device_id") , rs.getInt("service_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int save() throws Exception {

        String query = "INSERT INTO "+TABLE_NAME+ "(cost, device_id, service_id) VALUES " +
                "('" + this.cost + "','" +
                this.device_id + "','" +
                this.service_id + "')";
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
