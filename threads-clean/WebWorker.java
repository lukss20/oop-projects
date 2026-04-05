import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import javax.swing.*;

public class WebWorker extends Thread {
    private String urlString;
    private WebWorkerFrame webWorkerFrame;
    private int row;

    public WebWorker(String urlString, WebWorkerFrame webWorkerFrame, int row) {
        this.urlString = urlString;
        this.webWorkerFrame = webWorkerFrame;
        this.row = row;
    }
    @Override
    public void run() {
        try {
            webWorkerFrame.semaphore.acquire();
            webWorkerFrame.redraw("running",row,1,0);
            download();
            webWorkerFrame.semaphore.release();
        } catch (InterruptedException e) {
            webWorkerFrame.redraw("interrupted",row,0,0);
            throw new RuntimeException(e);
        }
    }
    //This is the core web/download i/o code...
    public void download() {
        InputStream input = null;
        StringBuilder contents = null;
        try {
            long startTime = System.currentTimeMillis();
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            // Set connect() to throw an IOException
            // if connection does not succeed in this many msecs.
            connection.setConnectTimeout(5000);

            connection.connect();
            input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            char[] array = new char[1000];
            int len;
            contents = new StringBuilder(1000);
            while ((len = reader.read(array, 0, array.length)) > 0) {
                contents.append(array, 0, len);
                Thread.sleep(100);
            }
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            Date now = new Date();
            SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
            String message="";
            message += date.format(now) + " " + elapsedTime + "ms " + contents.length() + "bytes";
            webWorkerFrame.redraw(message,row,-1,1);
            // Successful download if we get here

        }
        // Otherwise control jumps to a catch...
        catch (MalformedURLException ignored) {
            webWorkerFrame.redraw("err",row,-1,1);
        } catch (InterruptedException exception) {
            webWorkerFrame.redraw("interrupted",row,-1,1);
            // YOUR CODE HERE
            // deal with interruption
        } catch (IOException ignored) {
            webWorkerFrame.redraw("err",row,-1,1);
        }
        // "finally" clause, to close the input stream
        // in any case
        finally {
            try {
                if (input != null) input.close();
            } catch (IOException ignored) {
            }
        }


    }
}
