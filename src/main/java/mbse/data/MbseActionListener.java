package mbse.data;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MbseActionListener implements ActionListener {

    public enum Action {
        ZOOM_IN,
        ZOOM_OUT,
        NONE
    }

    private Action action = Action.NONE;

    public MbseActionListener(Action action) {
        this.action = action;
    }

    public void actionPerformed(ActionEvent e) {
    }
}
