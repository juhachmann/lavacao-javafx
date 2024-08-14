package ifsc.poo.lavacao.model.database;

public class DatabaseFactory {

    public static Database getConnection(String sgbd) {
        if(sgbd.equals("postgresql"))
            return new DatabasePostgreSQL();
        if(sgbd.equals("mysql"))
            return new DatabaseMySQL();
        return null;
    }

}
