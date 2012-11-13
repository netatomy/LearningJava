package zqc.wetalk.server;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import zqc.wetalk.server.ui.WeTalkServerFrame;

public class WeTalkServer {

    /**
     * @param args
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                JFrame f = new WeTalkServerFrame();
                //f.pack();
                f.setVisible(true);
            }
        });

    }

}
