package servicely;


import servicely.database.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Startup {

    public static String start () {

        try{
            migrate();
            seed();
            System.out.println("Init Successful 1 ");

        } catch (Exception e) {
            return e.getMessage();
        }
        return "true";
    }

    private static boolean migrate() throws Exception {
        String query;

        List<String> queries = new ArrayList<>();
        System.out.println("Init Successful 2");

        query = QueryHelper.createTable("users", "(" +
                "id int UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) UNIQUE NOT NULL," +
                "mobile_number VARCHAR(10) NOT NULL," +
                "password VARCHAR(50) NOT NULL" +
                ")");

         queries.add(query);

        query = QueryHelper.createTable("devices", "(" +
                "id int UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "type VARCHAR(100) NOT NULL" +
                ")");
        queries.add(query);

        query = QueryHelper.createTable("services", "(" +
                "id int UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL" +
                ")");
        queries.add(query);

        query = QueryHelper.createTable("contracts", "(" +
                "id int UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "device_id int unsigned, " +
                    "FOREIGN KEY (device_id) REFERENCES devices (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                "user_id int unsigned, " +
                    "FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");
        queries.add(query);

        query = QueryHelper.createTable("device_service", "(" +
                "id int UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "cost FLOAT NOT NULL," +
                "device_id int unsigned, " +
                    "FOREIGN KEY (device_id) REFERENCES devices (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                "service_id int unsigned, " +
                    "FOREIGN KEY (service_id) REFERENCES services (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");
        queries.add(query);

        query = QueryHelper.createTable("address", "(" +
                "id int UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "address VARCHAR(255) NOT NULL," +
                "user_id int unsigned, " +
                    "FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");
        queries.add(query);

        query = QueryHelper.createTable("orders", "(" +
                "id int UNSIGNED AUTO_INCREMENT PRIMARY KEY," +
                "device_service_id int unsigned, " +
                    "FOREIGN KEY (device_service_id) REFERENCES device_service (id) ON DELETE CASCADE ON UPDATE CASCADE," +
                "user_id int unsigned, " +
                    "FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")");
        queries.add(query);

        for(String q : queries) {

            Database.init().query(q).fireUpdate();
        }

        return true;
    }

    public static boolean seed() throws Exception{

        deviceSeeder();

        serviceSeeder();

        deviceServiceSeeder();

        System.out.println("Init Successful 3");

        return true;
    }

    private static void deviceServiceSeeder() throws Exception {
        ResultSet devices = Device.all();
        devices.beforeFirst();
        while( devices.next() ) {
            ResultSet services = Service.all();

            services.beforeFirst();
            while( services.next() ) {

                DeviceService ds = new DeviceService(500, devices.getInt("id"), services.getInt("id"));
                ds.save();
            }
        }
    }

    private static void serviceSeeder() {
        Service service = null;

        List<Service> services = new ArrayList<>();

        service = new Service("Battery Replace");
        System.out.println("Service Created");
        services.add(service);
        service = new Service("Screen Replace");
        services.add(service);
        service = new Service("Body Replace");
        services.add(service);
        service = new Service("Speaker Replace");
        services.add(service);
        service = new Service("Accessory Replace");
        services.add(service);
        service = new Service("Software Update");
        services.add(service);

        services.forEach((d) -> {
            try {
                d.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void deviceSeeder() {
        Device device = null;
        List<Device> devices = new ArrayList<>();

        device = new Device("Google Pixel", "Mobile");
        devices.add(device);
        device = new Device("Google Pixel XL", "Mobile");
                devices.add(device);

        device = new Device("Google Nexus 6", "Mobile");
                devices.add(device);

        device = new Device("Google Nexus 6p", "Mobile");
                devices.add(device);

        device = new Device("Google Nexus 5x", "Mobile");
                devices.add(device);

        device = new Device("Google Pixel C", "Mobile");
                devices.add(device);

        device = new Device("Google Pixel 2", "Mobile");
                devices.add(device);

        device = new Device("Google Pixel 2017", "Mobile");
                devices.add(device);

        device = new Device("HP Pavilion", "Laptop");
                devices.add(device);

        device = new Device("HP Pavilion G6", "Laptop");
                devices.add(device);

        device = new Device("HP Pavilion G7", "Laptop");
                devices.add(device);

        device = new Device("HP Pavilion G10", "Laptop");
                devices.add(device);

        device = new Device("HP Pavilion Spectre", "Laptop");
        devices.add(device);

        devices.forEach((d) -> {
            try {
                d.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void userSeeder() {

        List<String> queries = new ArrayList<>();

        String query;

        query = "";

    }


}
