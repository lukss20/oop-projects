import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class WebWorkerFrame extends JFrame{
    private DefaultTableModel model;
    private JTable table;
    private JPanel panel;
    private JButton singleThreadFetchB,concurrentFetchB,stopB;
    private JTextField threadNumInputField;
    private JLabel running,completed,elapsed;
    private JProgressBar progressBar;
    private long startTime, endTime;
    private int runningNum,completedNum;
    private ArrayList<Thread> runningThreads;
    public Worker worker;
    public Semaphore semaphore;

    public WebWorkerFrame() throws IOException {
        super("WebLoader");
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        model = new DefaultTableModel(new String[] { "url", "status"}, 0);
        BufferedReader buffread = new BufferedReader(new FileReader("links.txt"));
        String line = buffread.readLine();
        while (line != null) {
            Object[] data = { line, "" };
            model.addRow(data);
            line = buffread.readLine();
        }
        buffread.close();
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(600,300));
        panel.add(scrollpane);
        singleThreadFetchB = new JButton("Single Thread Fetch");
        concurrentFetchB = new JButton("Concurrent Fetch");
        stopB = new JButton("Stop");
        stopB.setEnabled(false);
        stopB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopB.setEnabled(false);
                singleThreadFetchB.setEnabled(true);
                concurrentFetchB.setEnabled(true);
                endTime = System.currentTimeMillis();
                worker.interrupt();
                for(int i =0;i< runningThreads.size();i++){
                    runningThreads.get(i).interrupt();
                }
            }
        });
        singleThreadFetchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareForRun();
                worker = new Worker(1);
                worker.start();
            }
        });

        concurrentFetchB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepareForRun();
                worker = new Worker(Integer.parseInt(threadNumInputField.getText()));
                worker.start();
            }
        });
        threadNumInputField = new JTextField();
        threadNumInputField.setMaximumSize(new Dimension(50, 20));
        running = new JLabel("Running: ");
        completed = new JLabel("Completed: ");
        elapsed = new JLabel("Elapsed: ");
        progressBar = new JProgressBar(0,model.getRowCount());

        panel.add(singleThreadFetchB);
        panel.add(concurrentFetchB);
        panel.add(threadNumInputField);
        panel.add(running);
        panel.add(completed);
        panel.add(elapsed);
        panel.add(progressBar);
        panel.add(stopB);
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    private void prepareForRun(){
        elapsed.setText("Elapsed: ");
        for (int i = 0; i < model.getRowCount(); i++)
            model.setValueAt("", i, 1);
        runningNum =0;
        completedNum=0;
        stopB.setEnabled(true);
        singleThreadFetchB.setEnabled(false);
        concurrentFetchB.setEnabled(false);
        startTime = System.currentTimeMillis();
    }
    public synchronized void redraw(String message, int row,int runningchange,int completedchange) {
        runningNum += runningchange;
        completedNum += completedchange;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                running.setText("Running: " + runningNum);
                completed.setText("Completed: " + completedNum);
                model.setValueAt(message, row, 1);
                progressBar.setValue(completedNum);
            }
        });
    }
    public class Worker extends Thread{
        public Worker(int threadNum) {
            semaphore = new Semaphore(threadNum);
        }
        @Override
        public void run() {
            runningThreads = new ArrayList<>();
            int totalRows = model.getRowCount();
            for (int i = 0; i < totalRows; i++) {
                String url = (String) model.getValueAt(i, 0);
                WebWorker todo = new WebWorker(url, WebWorkerFrame.this, i);
                todo.start();
                runningThreads.add(todo);
            }

            for (int i =0;i<runningThreads.size();i++ ) {
                try {
                    runningThreads.get(i).join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            endTime = System.currentTimeMillis();
            SwingUtilities.invokeLater(() -> {
                long totalTime = endTime - startTime;
                elapsed.setText("Elapsed: " + totalTime + "ms");
            });
            stopB.setEnabled(false);
            singleThreadFetchB.setEnabled(true);
            concurrentFetchB.setEnabled(true);

        }

    }
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                new WebWorkerFrame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
