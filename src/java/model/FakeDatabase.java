package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fake database used to test the web page
 *
 * @author Tim
 * @version 1.0
 */
public class FakeDatabase implements DBAccessor {

    private final List<MenuItem> menuItems;
    
    
    public FakeDatabase() {
        menuItems = new ArrayList<>();
        //create MenuItems
        MenuItem item1 = new MenuItem("Burger", 2);
        MenuItem item2 = new MenuItem("Fries", 3);
        MenuItem item3 = new MenuItem("Mountian Dew", 1.5);
        // add menu items
        menuItems.add(item1);
        menuItems.add(item2);
        menuItems.add(item3);
    }

    @Override
    public void openConnection(String driverClassName, String url, String username, String password) throws IllegalArgumentException, ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void closeConnection() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findRecords(String sqlString, boolean closeConnection) throws SQLException, Exception {
        return menuItems;
    }

    @Override
    public boolean insertRecord(String tableName, List colDescriptors, List colValues, boolean closeConnection) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateRecords(String tableName, List colDescriptors, List colValues, String whereField, Object whereValue, boolean closeConnection) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteRecords(String tableName, String whereField, Object whereValue, boolean closeConnection) throws SQLException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
}
