package zqc.wetalk.client;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import zqc.wetalk.client.ui.WeTalkClientFrame;

public class WeTalkClient {

    /**
     * @param args
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                JFrame f = new WeTalkClientFrame();
                f.setVisible(true);
            }
        });
    }

}
