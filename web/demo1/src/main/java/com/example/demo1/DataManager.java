package com.example.demo1;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class DataManager {
    public class item{
        public String id,name,image;
        public BigDecimal price;
    }
    public DataManager() throws SQLException {
        fillList();
    }
    public ArrayList<item> items = new ArrayList<item>();
    public void fillList() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shoppingdb", "root", "Luka2004#");
        PreparedStatement ps = con.prepareStatement("SELECT * FROM products;");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            item temp = new item();
            temp.id = (String)rs.getObject(1);
            temp.name = (String)rs.getObject(2);
            temp.image = (String)rs.getObject(3);
            temp.price = (BigDecimal)rs.getObject(4);
            items.add(temp);
        }
    }
    public ArrayList<item> getItems(HashSet<String> id){
        if(id == null){ return items;};
        ArrayList<item> temp = new ArrayList<item>();
        for(String i : id){
            for(item t : items){
                if(t.id.equals(i)){
                    temp.add(t);
                    break;
                }
            }
        }
        return temp;
    };
}
