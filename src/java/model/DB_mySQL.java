package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Heavily based off Jim's DB_Generic class (almost an exact copy). Rewrote for studying purposes used
 * for connecting to a database using SQL
 *
 * @author Tim
 * @version 1.0
 */
public class DB_mySQL implements DBAccessor {

    private Connection conn;
    private static final String URL_NULL_OR_EMPTY = "URL is null or empty";

    /**
     * Opens a connection to the database
     *
     * @param driverClassName - full name of the driver class
     * @param url - the connection URL
     * @param username - database username needed for connecting
     * @param password - database password needed for connecting
     * @throws IllegalArgumentException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public void openConnection(String driverClassName, String url, String username, String password)
            throws IllegalArgumentException, ClassNotFoundException, SQLException {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException(URL_NULL_OR_EMPTY);
        }
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        Class.forName(driverClassName);
        conn = DriverManager.getConnection(url, username, password);
    }

    /**
     * closes the database connection.
     *
     * @throws SQLException if the connection fails to close
     */
    @Override
    public void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     * used for custom SQL queries
     *
     * @param sqlString - an SQL query string
     * @param closeConnection - closes the connection if true
     * @return - a list of the records found
     * @throws SQLException
     * @throws Exception
     */
    @Override
    public List findRecords(String sqlString, boolean closeConnection)
            throws SQLException, Exception {

        Statement stmt = null;
        ResultSet rs;
        ResultSetMetaData metaData = null;
        final List records = new ArrayList();
        Map record;

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlString);
            metaData = rs.getMetaData();
            final int fields = metaData.getColumnCount();

            while (rs.next()) {
                record = new HashMap();
                for (int counter = 1; counter <= fields; counter++) {
                    try {
                        record.put(metaData.getColumnName(counter), rs.getObject(counter));
                    } catch (NullPointerException n) {

                    }
                }
                records.add(record);
            }
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                stmt.close();
                if (closeConnection) {
                    conn.close();
                }
            } catch (SQLException se) {
                throw se;
            }
        }

        return records;
    }

    /**
     * looks for a record based on primary key entered
     *
     * @param table - the name of the table
     * @param primaryKeyField the name of the primary key field
     * @param keyValue the value of the primary key
     * @param closeConnection - closes database connection if true
     * @return - a single record if found
     * @throws SQLException
     * @throws Exception
     */
    public Map getRecordByID(String table, String primaryKeyField, Object keyValue, boolean closeConnection)
            throws SQLException, Exception {

        Statement stmt = null;
        ResultSet rs;
        ResultSetMetaData metaData = null;
        final Map record = new HashMap();

        try {
            stmt = conn.createStatement();
            String sql2;

            if (keyValue instanceof String) {
                sql2 = "= '" + keyValue + "'";
            } else {
                sql2 = "=" + keyValue;
            }

            String sql = "Select * From " + table + " Where " + primaryKeyField + sql2;
            rs = stmt.executeQuery(sql);
            metaData = rs.getMetaData();
            final int fields = metaData.getColumnCount();

            if (rs.next()) {
                for (int counter = 1; counter <= fields; counter++) {
                    record.put(metaData.getColumnName(counter), rs.getObject(counter));
                }
            }

        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                stmt.close();
                if (closeConnection) {
                    conn.close();
                }
            } catch (SQLException se) {
                throw se;
            }
        }
        return record;
    }

    /**
     *
     * @param tableName - the name of the table
     * @param colDescriptors -
     * @param colValues
     * @param closeConnection
     * @return
     * @throws SQLException
     * @throws Exception
     */
    @Override
    public boolean insertRecord(String tableName, List colDescriptors, List colValues, boolean closeConnection)
            throws SQLException, Exception {

        PreparedStatement pstmt = null;
        int recordsUpdated = 0;
        
        try {
            pstmt = buildInsertStatement(conn, tableName, colDescriptors);
            
            final Iterator counter = colValues.iterator();
            int index = 1;
            while (counter.hasNext()) {
                final Object obj = counter.next();
                if (obj instanceof String) {
                    pstmt.setString(index++, (String) obj);
                } else if (obj instanceof Integer) {
                    pstmt.setInt(index++, ((Integer) obj));
                } else if (obj instanceof Long) {
                    pstmt.setLong(index++, ((Long) obj));
                } else if (obj instanceof Double) {
                    pstmt.setDouble(index++, ((Double) obj));
                } else if (obj instanceof java.sql.Date) {
                    pstmt.setDate(index++, (java.sql.Date) obj);
                } else if (obj instanceof Boolean) {
                    pstmt.setBoolean(index++, ((Boolean) obj));
                } else {
                    if (obj != null) {
                        pstmt.setObject(index++, obj);
                    }
                }
            }
            recordsUpdated = pstmt.executeUpdate();
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                pstmt.close();
                if (closeConnection) {
                    conn.close();
                }
            } catch (SQLException se) {
                throw se;
            }
        }
        return recordsUpdated == 1;
        //return false;
    }

    /**
     * updates a record
     *
     * @param tableName - name of the table
     * @param colDescriptors - list of column descriptors for the fields that
     * can be updated
     * @param colValues - values for the fields to be updated
     * @param whereField - field name for the search criteria
     * @param whereValue - value for the search criteria
     * @param closeConnection - will close the database connection if true
     * @return - the number of fields updated
     * @throws SQLException
     * @throws Exception
     */
    @Override
    public int updateRecords(String tableName, List colDescriptors, List colValues,
            String whereField, Object whereValue, boolean closeConnection)
            throws SQLException, Exception {
        // do not quite understand this
        PreparedStatement pstmt = null;
        int recordsUpdated = 0;

        try {
            pstmt = buildUpdateStatement(conn, tableName, colDescriptors, whereField);

            final Iterator counter = colValues.iterator();
            int index = 1;
            boolean doWhereValueFlag = false;
            Object obj = null;

            while (counter.hasNext() || doWhereValueFlag) {
                if (!doWhereValueFlag) {
                    obj = counter.next();
                }
                if (obj instanceof String) {
                    pstmt.setString(index++, (String) obj);
                } else if (obj instanceof Integer) {
                    pstmt.setInt(index++, ((Integer) obj));
                } else if (obj instanceof Long) {
                    pstmt.setLong(index++, ((Long) obj));
                } else if (obj instanceof Double) {
                    pstmt.setDouble(index++, ((Double) obj));
                } else if (obj instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(index++, ((java.sql.Timestamp) obj));
                } else if (obj instanceof java.sql.Date) {
                    pstmt.setDate(index++, (java.sql.Date) obj);
                } else if (obj instanceof Boolean) {
                    pstmt.setBoolean(index++, ((Boolean) obj));
                } else {
                    if (obj != null) {
                        pstmt.setObject(index++, obj);
                    }
                }
                
                if(doWhereValueFlag) {
                    break;
                }
                if(!counter.hasNext()) {
                    doWhereValueFlag = true;
                    obj = whereValue;
                }
            }
            
            recordsUpdated = pstmt.executeUpdate();

        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                pstmt.close();
                if (closeConnection) {
                    conn.close();
                }
            } catch (SQLException se) {
                throw se;
            }
        }

        return recordsUpdated;
    }

    /**
     * Deletes record(s) from a table based on a single matching field value
     * 
     * @param tableName - name of table
     * @param whereField - name of the field for search
     * @param whereValue - the value for search criteria
     * @param closeConnection - closes the connection if true
     * @return - the number of records deleted
     * @throws SQLException
     * @throws Exception 
     */
    @Override
    public int deleteRecords(String tableName, String whereField, Object whereValue, boolean closeConnection)
            throws SQLException, Exception {
        
        PreparedStatement pstmt = null;
        int recordsDeleted = 0;
        
        try {
            pstmt = buildDeleteStatement(conn, tableName, whereField);
            
            if(whereField != null) {
                if (whereValue instanceof String) {
                    pstmt.setString(1, (String) whereValue);
                } else if (whereValue instanceof Integer) {
                    pstmt.setInt(1, ((Integer) whereValue));
                } else if (whereValue instanceof Long) {
                    pstmt.setLong(1, ((Long) whereValue));
                } else if (whereValue instanceof Double) {
                    pstmt.setDouble(1, ((Double) whereValue));
                } else if (whereValue instanceof java.sql.Date) {
                    pstmt.setDate(1, (java.sql.Date) whereValue);
                } else if (whereValue instanceof Boolean) {
                    pstmt.setBoolean(1, ((Boolean) whereValue));
                } else {
                    if (whereValue != null) {
                        pstmt.setObject(1, whereValue);
                    }
                }
            }
            
            recordsDeleted = pstmt.executeUpdate();
            
        } catch (SQLException se) {
            throw se;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                pstmt.close();
                if (closeConnection) {
                    conn.close();
                }
            } catch (SQLException se) {
                throw se;
            }
        }

        return recordsDeleted;
    }

    /**
     * builds a java.sql.PreparedStatement for SQL insert
     * 
     * @param conn_loc - a valid connection
     * @param tableName - the name of the table
     * @param colDescriptors - List of column descriptors for the fields to be inserted to
     * @return java.sql.PreparedStatement
     * @throws SQLException 
     */
    private PreparedStatement buildInsertStatement(Connection conn_loc, String tableName, List colDescriptors)
            throws SQLException {

        StringBuilder sql = new StringBuilder("Insert Into ");
        sql.append(tableName).append(" (");
        final Iterator counter = colDescriptors.iterator();
        while(counter.hasNext()) {
            sql.append((String)counter.next()).append(", ");
        }
        sql = new StringBuilder(sql.toString().substring(0, sql.toString().lastIndexOf(", ")) + ") Values (");
        for (Object colDescriptor : colDescriptors) {
            sql.append("? ");
        }
        final String finalSQL = sql.toString().substring(0, sql.toString().lastIndexOf(", ")) + ")";
        return conn_loc.prepareStatement(finalSQL);
    }

    /**
     * Builds a java.sql.PreparedStatement for an sql update statement.
     * 
     * @param conn_loc - a valid connection
     * @param tableName - the table name
     * @param colDescriptors - list of column descriptors
     * @param whereField - the field name for the search criteria
     * @return
     * @throws SQLException 
     */
    private PreparedStatement buildUpdateStatement(Connection conn_loc, String tableName, List colDescriptors, String whereField)
            throws SQLException {

        StringBuilder sql = new StringBuilder("Update ");
        sql.append(tableName).append(" Set ");
        final Iterator counter = colDescriptors.iterator();
        while(counter.hasNext()) {
            sql.append((String)counter.next()).append(" = ?, ");
        }
        sql = new StringBuilder(sql.toString().substring(0, sql.toString().lastIndexOf(", ")));
        sql.append(" Where ").append(whereField).append(" = ?");
        final String finalSQL = sql.toString();
        return conn_loc.prepareStatement(finalSQL);
    }

    /**
     * Builds a java.sql.PreparedStatement for sql delete statement
     * 
     * @param conn_loc - a valid connection
     * @param tableName - the name of the table
     * @param whereField - field name for the search criteria
     * @return - java.sql.PreparedStatement
     * @throws SQLException 
     */
    private PreparedStatement buildDeleteStatement(Connection conn_loc, String tableName, String whereField)
            throws SQLException {
        
        final StringBuilder sql = new StringBuilder("Delete From ");
        sql.append(tableName);
        
        if(whereField != null) {
            sql.append(" Where ");
            sql.append(whereField).append(" = ?");
        }
        final String finalSQL = sql.toString();
        return conn_loc.prepareStatement(finalSQL);
    }

    //equals and hash
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.conn);
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
        final DB_mySQL other = (DB_mySQL) obj;
        if (!Objects.equals(this.conn, other.conn)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SQLDB{" + "conn=" + conn + '}';
    }
    
    public static void main(String[] args) throws Exception {
        DB_mySQL db = new DB_mySQL();
        db.openConnection("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/restaurant", "root", "admin");
        
        List records = db.findRecords("Select menu_item_name from menu_item", true);
        System.out.println(records);
    }

}
