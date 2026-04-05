import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class Metropolis extends AbstractTableModel {
    private int rownum ;
    private int colnum = 3;
    private Connection connection;
    private ResultSet resultSet;
    //initializes connection in constructor
    public Metropolis(Connection conn)  {
        connection = conn;
    }
    // adds row to a database with given parameters and displays that on table
    public void add(String metropolis, String continent, String population) throws SQLException {
        if(metropolis.equals("")||continent.equals("")||population.equals(""))
            return;
        PreparedStatement statement = connection.prepareStatement("INSERT INTO metropolises VALUES(?,?,?);");
        statement.setString(1,metropolis);
        statement.setString(2,continent);
        statement.setString(3,population);
        statement.executeUpdate();
        fireTableDataChanged();
        onlylast(metropolis,continent,population);
    }
    //constructs first part of query with given parameters
    private void firstPart(String metropolis,String matchingtype,String[] temp){
        if(!metropolis.equals("")) {
            if (matchingtype.equals("") || matchingtype.equals("exact match")) {
                temp[0] = " metropolis = '" + metropolis + "'";
            } else {
                temp[0] = " metropolis LIKE '%" + metropolis + "%'";
            }
            temp[3] += " WHERE " + temp[0];
        }
    }
    //constructs second part of query with given parameters
    private void secondPart(String continent,String matchingtype,String[] temp ){
        if(!continent.equals("")) {
            if (matchingtype.equals("") || matchingtype.equals("exact match")) {
                temp[1] = " continent = '" + continent + "'";
            } else {
                temp[1]  = " continent LIKE '%" + continent + "%'";
            }
            if(!temp[0] .equals("")){
                temp[3]  += " AND " + temp[1] ;
            }else{
                temp[3]  += " WHERE " + temp[1] ;
            }
        }
    }
    //constructs third part of query with given parameters
    private void thirdPart(String population,String whichsize,String[] temp){
        if(!population.equals("")) {
            if(whichsize.equals("")) {
                temp[2] = " population = " + population;
            }else if(whichsize.equals("population smaller than")){
                temp[2] = " population < " + population;
            }else{
                temp[2] = " population > " + population;
            }
            if(temp[1].equals("") && temp[0].equals("")){
                temp[3]+= " WHERE " + temp[2];
            }else{
                temp[3] += " AND " + temp[2];
            }
        }
    }
    // searches a database based on given parameters and displays it on table
    public void search(String metropolis, String continent, String population,String whichsize,String matchingtype) throws SQLException {
        String[] temp = {"","","","SELECT * FROM metropolises"};
        firstPart(metropolis,matchingtype,temp);
        secondPart(continent,matchingtype,temp);
        thirdPart(population,whichsize,temp);
        temp[3] += ";";
        PreparedStatement statement = connection.prepareStatement(temp[3],ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        resultSet = statement.executeQuery();
        fireTableDataChanged();


    }
    //displas the last added row on the table
    public void onlylast(String metropolis, String continent, String population) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM metropolises WHERE metropolis = ? AND continent = ? AND population = ?",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        statement.setString(1, metropolis);
        statement.setString(2, continent);
        statement.setString(3, population);
        resultSet = statement.executeQuery();
        fireTableDataChanged();
    }
    // abstracttablemoedl overriden functions
    @Override
    public int getRowCount() {

            if(resultSet!=null ) {
                try {
                    resultSet.last();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    rownum = resultSet.getRow();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        return rownum;
    }

    @Override
    public int getColumnCount() {
        return colnum;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

            if (resultSet != null) {
                try {
                    resultSet.absolute(rowIndex + 1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    return resultSet.getObject(columnIndex + 1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

        return null;
    }
}
