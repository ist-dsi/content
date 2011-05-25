package module.contents.presentationTier.component;

import module.contents.domain.Page;

import org.apache.commons.lang.StringUtils;

import pt.ist.vaadinframework.ui.EmbeddedComponentContainer;
import pt.ist.vaadinframework.annotation.EmbeddedComponent;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@EmbeddedComponent(path = { "SimplePageView-(.*)" })
public class SimplePageView extends BaseComponent implements EmbeddedComponentContainer {

    private transient Page page;

    @Override
    public void setArguments(final String... args) {
	page = getDomainObject(StringUtils.substringAfter(args[0], "-"));
    }

    @Override
    public void attach() {
	super.attach();

	final VerticalLayout layout = createVerticalLayout();
	setCompositionRoot(layout);

	renderPageTitleArea(layout);

	renderPageMenuArea(layout);
	renderPageContent(layout);
    }

    private void renderPageTitleArea(final AbstractComponentContainer container) {
	addTag(container, "h2", page.getTitle().getContent());
    }

    private void renderPageMenuArea(final AbstractComponentContainer container) {
	final AbstractComponentContainer menuArea = new PageMenuComponent(page);
	container.addComponent(menuArea);
    }

    private void renderPageContent(final AbstractComponentContainer container) {
	final SimplePageBodyComponent pageBodyComponent = new SimplePageBodyComponent(page);
	container.addComponent(pageBodyComponent);
    }

}
