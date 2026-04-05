import com.example.demo1.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataManagerTest {
   DataManager dataManager;
   @BeforeEach
    public void prepare() throws SQLException {
       dataManager = new DataManager();
   }
   @Test
   public void fillListTest(){
       assertEquals(dataManager.items.size(), 14);
       assertEquals(dataManager.items.get(0).name, "Classic Hoodie");
       assertEquals(dataManager.items.get(13).name, "Mini Football Helmet");
   }
   @Test
    public void getItemTest(){
       HashSet<String> expected = new HashSet<>();
       expected.add("HC");
       expected.add("AMinHm");
       ArrayList<DataManager.item> items = dataManager.getItems(expected);
       HashSet<String> actual = new HashSet<>();
       for(DataManager.item i : items)
           actual.add(i.id);
       assertEquals(expected, actual);
   }
}
