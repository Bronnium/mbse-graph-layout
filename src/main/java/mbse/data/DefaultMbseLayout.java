package mbse.data;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.view.mxGraph;

public class DefaultMbseLayout extends mxCompactTreeLayout implements MbseLayout {

	public DefaultMbseLayout(mxGraph arg0) {
		super(arg0);
	}

	public void setHorizontalSpacing(int spacing) {
		setLevelDistance(spacing);
		
	}

	public void setVerticalSpacing(int spacing) {
		setNodeDistance(spacing);
		
	}


}
