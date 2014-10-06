package model;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 *
 * This class is the high level service class used to talk to the MenuItem
 * strategy objects
 * 
 * @author Tim
 * @version 1.0
 */

public class MenuItemService {
    
    private IMenuItemDAO dao;
    private static final String DATA_NULL_EXCEPTION = "Data Service Strategy is null";
    
    public MenuItemService(String orderDao) 
            throws NullPointerException, ClassCreationException {
        try {
            Class clazz = Class.forName(orderDao);
            dao = (IMenuItemDAO)clazz.newInstance();
        } catch (ClassNotFoundException ex) {
            throw new ClassCreationException(ex.getMessage());
        } catch (InstantiationException ex) {
            throw new ClassCreationException(ex.getMessage());
        } catch (IllegalAccessException ex) {
            throw new ClassCreationException(ex.getMessage());
        }
        
        this.setDao(dao);
    }
    
    public MenuItemService(String orderDao, String db) 
            throws NullPointerException, ClassCreationException {
        try {
            Class clazz = Class.forName(orderDao);
            dao = (IMenuItemDAO)clazz.newInstance();
        } catch (ClassNotFoundException ex) {
            throw new ClassCreationException(ex.getMessage());
        } catch (InstantiationException ex) {
            throw new ClassCreationException(ex.getMessage());
        } catch (IllegalAccessException ex) {
            throw new ClassCreationException(ex.getMessage());
        }
        this.setDao(dao);
        dao.setDb(db);
    }
    
    public MenuItemService(IMenuItemDAO orderDao) {
        this.setDao(orderDao);
    }

    public List<MenuItem> getMenuList() throws DataAccessException {
        return dao.getMenuItems();
    }
    
    public void processOrder(String selectedItem) {
        //for future
    }
    
    //getters
    public IMenuItemDAO getDataStrategy() {
        return dao;
    }

    //setters
    public final void setDao(IMenuItemDAO dao) {
        if (dao == null) {
            throw new IllegalArgumentException(DATA_NULL_EXCEPTION);
        }
        this.dao = dao;
    }
    
    //equals, hash
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.dao);
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
        final MenuItemService other = (MenuItemService) obj;
        if (!Objects.equals(this.dao, other.dao)) {
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) throws NullPointerException, 
            DataAccessException, ClassCreationException {
        MenuItemService service = new MenuItemService("model.MenuItemDAO", "model.SQLDB");
        List list = service.getMenuList();
        MenuItem menuItem;
        for (Object item : list) {
            menuItem = (MenuItem)item;
            System.out.println(menuItem);       
        }
    }
}
