package mbse.data;

public interface MbseGraphControllerInterface {

    /**
     * 
     * @param style
     */
    public void changeStyle(boolean style);

    public void sameOriginControl(boolean sameOrigin);

    public void collapseAction();

    public void expandAction();

    public void exportImage();

    public void layoutOnSelectedGroup();

    public void changedSpacing(int spacing, boolean horizontal);
}
