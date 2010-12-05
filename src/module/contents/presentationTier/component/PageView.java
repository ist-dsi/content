package module.contents.presentationTier.component;

import java.io.Serializable;

import module.contents.domain.Page;
import module.contents.domain.Section;
import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.ist.vaadinframework.ui.EmbeddedComponentContainer;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class PageView extends BaseComponent implements EmbeddedComponentContainer {

    public interface MenuReRenderListner extends Serializable {
	public void reRender();
    }

    private transient Page page;

    private final AbstractComponentContainer notificationArea = new HorizontalLayout();

    private AbstractComponentContainer menuArea = null;

    private PageBodyComponent pageBodyComponent = null;

    private final MenuReRenderListner menuReRenderListner = new MenuReRenderListner() {
	@Override
	public void reRender() {
	    rerenderMenuArea();
	}
    };

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
	final AbstractLayout layout = createSplitPanelLayout(SplitPanel.ORIENTATION_VERTICAL,
		true, "500px", "100%", 55, SplitPanel.UNITS_PIXELS);
	setCompositionRoot(layout);

	renderPageTitleArea(layout);

	final SplitPanel horizontalSplitPanel = createSplitPanelLayout(layout, SplitPanel.ORIENTATION_HORIZONTAL,
		false, "100%", "100%", 25, SplitPanel.UNITS_PERCENTAGE);
	renderPageMenuArea(horizontalSplitPanel);
	renderPageContent(horizontalSplitPanel);

	super.attach();
    }

    private void renderPageTitleArea(final AbstractComponentContainer container) {
	final HorizontalLayout horizontalLayout = new HorizontalLayout();
	horizontalLayout.setSizeFull();
	final Label title = addTag(horizontalLayout, "h2", page.getTitle().getContent());
	horizontalLayout.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
	if (page.canEdit()) {
	    horizontalLayout.addComponent(notificationArea);
	    horizontalLayout.setComponentAlignment(notificationArea, Alignment.MIDDLE_RIGHT);
	}
	container.addComponent(horizontalLayout);
    }

    private void renderPageMenuArea(final AbstractComponentContainer container) {
	final VerticalLayout verticalLayout = createVerticalLayout(container);

	menuArea = new PageMenuComponent(page);
	verticalLayout.addComponent(menuArea);

	if (page.canEdit()) {
	    final AddSectionButton createSectionComponent = new AddSectionButton(new ContentEditorSaveListner() {
	        @Override
	        public void save(final String title, final String content) {
	            final Section section = page.addSection(title, content);
	            pageBodyComponent.addSection(section);
	            rerenderMenuArea();
	        }
	    });
	    verticalLayout.addComponent(createSectionComponent);
	    verticalLayout.setComponentAlignment(createSectionComponent, Alignment.BOTTOM_LEFT);
	}
    }

    private void rerenderMenuArea() {
	final AbstractComponentContainer container = (AbstractComponentContainer) menuArea.getParent();
        final PageMenuComponent pageMenuComponent = new PageMenuComponent(page);
        container.replaceComponent(menuArea, pageMenuComponent);
        menuArea = pageMenuComponent;	
    }

    private void renderPageContent(final AbstractComponentContainer container) {
	pageBodyComponent = new PageBodyComponent(page, menuReRenderListner);
	container.addComponent(pageBodyComponent);
    }

}
