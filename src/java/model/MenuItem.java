package model;

import java.util.Objects;

/** 
 *
 * @author tdornak
 * @version 1.0
 */
public class MenuItem {
    
    private String name;
    private double cost;
    private static final String NAME_NULL_EXCEPTION = "Name is invalid";
    private static final String COST_INVALID_EXCEPTION = "Cost is invalid";

    public MenuItem() {
        
    }
    
    public MenuItem(String name, double cost) {
        this.setName(name);
        this.setCost(cost);
    }

    //setters
    public final void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new NullPointerException(NAME_NULL_EXCEPTION);
        }
        this.name = name;
    }

    public final void setCost(double cost) {
        if (cost < 0 || cost > 100) {
            throw new IllegalArgumentException(COST_INVALID_EXCEPTION);
        }
        this.cost = cost;
    }

    //getters
    public final String getName() {
        return name;
    }

    public final double getCost() {
        return cost;
    }

    
    //equals and hash
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.cost) ^ (Double.doubleToLongBits(this.cost) >>> 32));
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
        final MenuItem other = (MenuItem) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (Double.doubleToLongBits(this.cost) != Double.doubleToLongBits(other.cost)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + ", " + cost;
    }

    
    
}
