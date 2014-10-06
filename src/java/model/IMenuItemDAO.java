package model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Tim
 */
public interface IMenuItemDAO {
    public abstract List<MenuItem> getMenuItems() throws DataAccessException;
    
    public abstract void addMenuItem(MenuItem item) throws DataAccessException;
    
    public abstract void setDb(String accessor) throws ClassCreationException;
}
