package module.contents.presentationTier.component;

import module.contents.domain.Page;
import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.ist.vaadinframework.ui.EmbeddedComponentContainer;
import vaadin.annotation.EmbeddedComponent;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@EmbeddedComponent(path = { "SimplePageView-(.*)" })
public class SimplePageView extends BaseComponent implements EmbeddedComponentContainer {

    private transient Page page;

    @Override
    public void setArguments(final String... arguments) {
	for (final String arg : arguments) {
	    final int i = arg.indexOf('-');
	    final String id = arg.substring(i + 1);
	    page = AbstractDomainObject.fromExternalId(id);
	}
    }

    @Override
    public void attach() {
	super.attach();

	final AbstractLayout layout = createSplitPanelLayout(SplitPanel.ORIENTATION_VERTICAL,
		true, "500px", "100%", 55, SplitPanel.UNITS_PIXELS);
	setCompositionRoot(layout);

	renderPageTitleArea(layout);

	final SplitPanel horizontalSplitPanel = createSplitPanelLayout(layout, SplitPanel.ORIENTATION_HORIZONTAL,
		false, "100%", "100%", 25, SplitPanel.UNITS_PERCENTAGE);
	renderPageMenuArea(horizontalSplitPanel);
	renderPageContent(horizontalSplitPanel);
    }

    private void renderPageTitleArea(final AbstractComponentContainer container) {
	addTag(container, "h2", page.getTitle().getContent());
    }

    private void renderPageMenuArea(final AbstractComponentContainer container) {
	final VerticalLayout verticalLayout = createVerticalLayout(container);
	final AbstractComponentContainer menuArea = new PageMenuComponent(page);
	verticalLayout.addComponent(menuArea);
    }

    private void renderPageContent(final AbstractComponentContainer container) {
	final SimplePageBodyComponent pageBodyComponent = new SimplePageBodyComponent(page);
	container.addComponent(pageBodyComponent);
    }

}
