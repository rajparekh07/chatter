package servicely.database;

import servicely.utils.Escaper;

import java.sql.ResultSet;

public abstract class Model {

    protected static String TABLE_NAME = "devices";

    abstract public int save() throws Exception;

    abstract public String getTableName() throws Exception;


}
