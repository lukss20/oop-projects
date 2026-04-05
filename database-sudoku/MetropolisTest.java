import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetropolisTest {
    private Connection connection;
    private Metropolis metro;

    @BeforeEach
    public void prepare() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mymetropolisdb", "root", "Luka2004#");
        metro = new Metropolis(connection);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM metropolises");
        }
    }
    @Test
    public void testAdd() throws SQLException {
        metro.add("Martvili", "Europe", "3200");
        assertEquals(1, metro.getRowCount());
        assertEquals(3,metro.getColumnCount());
        assertEquals("Martvili", metro.getValueAt(0, 0));
        assertEquals("Europe", metro.getValueAt(0, 1));
        assertEquals("3200", metro.getValueAt(0, 2).toString());
    }
    @Test
    public void testAddNotFullInfo() throws SQLException {
        metro.add("", "Europe", "3200");
        assertEquals(0, metro.getRowCount());
        metro.add("Martvili", "", "3200");
        assertEquals(0, metro.getRowCount());
        metro.add("Martvili", "Europe", "");
        assertEquals(0, metro.getRowCount());
    }
    @Test
    public void testSearchPopMore() throws SQLException {
        metro.add("Martvili", "Europe", "3200");
        metro.add("Xoni", "Asia", "2300");
        metro.add("Kutaisi", "America", "1300");
        metro.search("", "", "2000", "population larger than", "");
        assertEquals(2, metro.getRowCount());
        assertEquals("Martvili", metro.getValueAt(0, 0));
        assertEquals("Xoni", metro.getValueAt(1, 0));
    }

    @Test
    public void testSearchPopLess() throws SQLException {
        metro.add("Martvili", "Europe", "3200");
        metro.add("Xoni", "Asia", "2300");
        metro.add("Kutaisi", "America", "1300");
        metro.search("", "", "2500", "population smaller than", "");
        assertEquals(2, metro.getRowCount());
        assertEquals("Xoni", metro.getValueAt(0, 0));
    }
    @Test
    public void testSearchExact() throws SQLException {
        metro.add("Martvili", "Europe", "3200");
        metro.search("Martvili", "Europe", "3200", "", "exact match");

        assertEquals(1, metro.getRowCount());
        assertEquals("Martvili", metro.getValueAt(0, 0));
    }

    @Test
    public void testSearchPartial() throws SQLException {
        metro.add("Martvili", "Europe", "3200");
        metro.add("Martkofi", "Asia", "2300");

        metro.search("Mar", "", "", "", "partial match");
        assertEquals(2, metro.getRowCount());
        assertEquals("Martvili", metro.getValueAt(0, 0));
        metro.search("","si","","","partial match");
        assertEquals(1,metro.getRowCount());
        assertEquals("Martkofi", metro.getValueAt(0, 0));
    }
}
