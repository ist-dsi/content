package module.contents.presentationTier.component;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ProgressIndicator;

public class ProgressFactory {

    public static AbstractComponentContainer createCenteredProgressIndicator() {
	final HorizontalLayout layout = new HorizontalLayout();
	layout.setSizeFull();

	final ProgressIndicator progressIndicator = new ProgressIndicator();
	progressIndicator.setIndeterminate(true);
	progressIndicator.setPollingInterval(3000);
	progressIndicator.setEnabled(true);

	layout.addComponent(progressIndicator);
	layout.setComponentAlignment(progressIndicator, Alignment.MIDDLE_CENTER);

	return layout;
    }

}
