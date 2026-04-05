import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class metropolisFrame extends JFrame {
    private JLabel metrolabel,continentlabel,populationlabel;
    private JTextField metropoliss,continent,population;
    private JButton add,search;
    private JComboBox populationsize,match;
    private Box rightside;
    private JTable table;
    private Metropolis metro;
    private JPanel panel;
    //this is a constructor of a class and unites helper functions of creating a display
    public metropolisFrame() throws SQLException {
        super("Metropolis");
        setLayout((new BorderLayout(4,4)));
        drawNorthPart();
        drawEastPart();
        drawCentrePart();
        buttonsActionListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }
    // this function draws the center part of a window and adds components
    private void drawCentrePart() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mymetropolisdb", "root", "Luka2004#");
        metro = new Metropolis(connection);
        table = new JTable(metro);
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setPreferredSize(new Dimension(300,200));
        add(scrollTable, BorderLayout.CENTER);
    }
    // this function draws the right part of a window and adds components
    private void drawEastPart(){
        rightside = Box.createVerticalBox();
        add = new JButton("Add");
        add.setMaximumSize(new Dimension(100,25));
        search = new JButton("Search");
        search.setMaximumSize(new Dimension(100,25));
        String[] populationsizeoptions = {"","population smaller than","population larger than"};
        String[] matchoptions = {"","exact match","partial match"};
        populationsize = new JComboBox(populationsizeoptions);
        populationsize.setMaximumSize(new Dimension(150,25));
        match = new JComboBox(matchoptions);
        match.setMaximumSize(new Dimension(150,25));
        rightside.add(add);
        rightside.add(search);
        rightside.add(populationsize);
        rightside.add(match);
        add(rightside, BorderLayout.EAST);
    }
    // this function draws the upper part of a window and adds components
    private void drawNorthPart(){
        panel = new JPanel();
        metropoliss = new JTextField(15);
        continent = new JTextField(15);
        population = new JTextField(15);
        metrolabel = new JLabel("Metropolis ");
        continentlabel = new JLabel("Continent ");
        populationlabel = new JLabel("Population ");
        panel.add(metrolabel);
        panel.add(metropoliss);
        panel.add(continentlabel);
        panel.add(continent);
        panel.add(populationlabel);
        panel.add(population);
        add(panel,BorderLayout.NORTH);
    }
    //this function adds action listeners on add and search buttons
    private void buttonsActionListeners() {
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    metro.add(metropoliss.getText(),continent.getText(),population.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    metro.search(metropoliss.getText(),continent.getText(),population.getText(), populationsize.getSelectedItem().toString(),match.getSelectedItem().toString() );
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


    public static void main(String[]args) throws SQLException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception ignored) {
        }

        metropolisFrame frame = new metropolisFrame();

    }
}
