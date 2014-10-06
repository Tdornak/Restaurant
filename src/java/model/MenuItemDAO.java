package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tim
 */
public class MenuItemDAO implements IMenuItemDAO {

    private DBAccessor db;
    
    //connection criteria for local connection
    private static final String DB_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "admin";
    
    //exception strings
    private static final String GET_ALL_MENUITEMS = "Select * from menu_item";
    private static final String DB_NULL_EXCEPTION = "Database is null";
    private static final String GET_ALL_RECORDS_EXCEPTION = "invalid SQL";
    private static final String URL_NULL = "URL is null or empty";

    public MenuItemDAO() {

    }

    public MenuItemDAO(DBAccessor db) {
        this.setDb(db);
    }
    
    public MenuItemDAO(String database) throws NullPointerException, ClassCreationException {  
        try {
            Class clazz = Class.forName(database);
            db = (DBAccessor)clazz.newInstance();
        } catch (ClassNotFoundException ex) {
            throw new ClassCreationException(ex.getMessage());
        } catch (InstantiationException ex) {
            throw new ClassCreationException(ex.getMessage());
        } catch (IllegalAccessException ex) {
            throw new ClassCreationException(ex.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
        this.setDb(db);
    }

    /**
     *
     * Retrieves all MenuItem records and packages them in a list
     *
     * @return - List of all MenuItem objects
     * @throws model.DataAccessException - if database is not successfully accessed
     */
    @Override
    public List<MenuItem> getMenuItems() throws DataAccessException {
        this.openLocalDbConnection();
        List<Map> rawData = new ArrayList<>();
        List<MenuItem> records = new ArrayList<>();

        try {
            rawData = db.findRecords(GET_ALL_MENUITEMS, true);
        } catch (SQLException ex) {
            throw new DataAccessException(GET_ALL_RECORDS_EXCEPTION);
        } catch (Exception ex) {
            Logger.getLogger(MenuItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        MenuItem item;

        for (Map map : rawData) {
            item = new MenuItem();
            String name = map.get("menu_item_name").toString();
            String cost = map.get("menu_item_cost").toString();
            item.setName(name);
            item.setCost(new Double(cost));

            records.add(item);
        }
        return records;
    }

    /**
     * adds a MenuItem object to the database as a new or updated record
     *
     * @param item - a MenuItem object
     * @throws SQLException
     */
    @Override
    public void addMenuItem(MenuItem item) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //getter
    public final DBAccessor getDb() {
        return db;
    }

    //setter
    public final void setDb(DBAccessor db) {
        if (db == null) {
            throw new NullPointerException(DB_NULL_EXCEPTION);
        }
        this.db = db;
    }
    
    @Override
    public final void setDb(String accessor) throws ClassCreationException {
        if (accessor == null || accessor.isEmpty()) {
            throw new NullPointerException(DB_NULL_EXCEPTION);
        }
        try {
            Class clazz = Class.forName(accessor);
            db = (DBAccessor)clazz.newInstance();
        } catch (ClassNotFoundException ex) {
            throw new ClassCreationException(ex.getMessage());
        } catch (InstantiationException ex) {
            throw new ClassCreationException(ex.getMessage());
        } catch (IllegalAccessException ex) {
            throw new ClassCreationException(ex.getMessage());
        }
        
    }

    private void openLocalDbConnection() {
        try {
            db.openConnection(DB_DRIVER_CLASS_NAME, DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(URL_NULL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuItemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //equals and hash
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.db);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MenuItemDAO other = (MenuItemDAO) obj;
        if (!Objects.equals(this.db, other.db)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MenuItemDAO{" + "db=" + db + '}';
    }

    public static void main(String[] args) throws DataAccessException, 
            NullPointerException, ClassCreationException {
        //SQLDB db = new SQLDB();
        MenuItemDAO menu = new MenuItemDAO("model.SQLDB");
        List<MenuItem> records = menu.getMenuItems();
        for (MenuItem item : records) {
            System.out.println(item);
        }
    }
}
