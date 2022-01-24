package mbse.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MbseActionListener implements ActionListener {

    public enum Action {
        ZOOM_IN,
        ZOOM_OUT,
        NONE
    }

    public MbseActionListener(Action action) {
    }

    public void actionPerformed(ActionEvent e) {
    }
}
