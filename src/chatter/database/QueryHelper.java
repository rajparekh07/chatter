package chatter.database;

public class QueryHelper {

    public static String createTable(String tableName, String fields) {

        return "CREATE TABLE IF NOT EXISTS " + tableName + " " + fields;
    }

    public static String implode(String delimiter, String... array) {

       StringBuilder sb = new StringBuilder();
        for(int i=0; i<array.length; i++) {
            sb.append(array[i]);
            sb.append((i!=array.length-1)?delimiter:"");
        }
        return sb.toString();
    }


}
