package module.contents.presentationTier.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import module.contents.domain.Page;
import module.contents.domain.Section;
import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.ist.vaadinframework.ui.EmbeddedComponentContainer;
import pt.ist.vaadinframework.annotation.EmbeddedComponent;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@EmbeddedComponent(path = { "PageView-(.*)" })
public class PageView extends BaseComponent implements EmbeddedComponentContainer {

    private class PageIndex extends VerticalLayout  {

	private class SectionLink extends Button implements ClickListener {

	    private final String sectionOID;

	    private SectionLink(final Section section) {
		super(section.getNumberedTitle());
		setStyleName(Reindeer.BUTTON_LINK);
		sectionOID = section.getExternalId();
		final ClickListener clickListener = this;
		addListener(clickListener);
	    }

	    @Override
	    public void buttonClick(final ClickEvent event) {
		scrollIntoSection(sectionOID);
	    }

	}

	private PageIndex() {
	    setMargin(false, false, true, true);
	}

	@Override
	public void attach() {
	    super.attach();

	    final Set<Section> sections = page.getOrderedSections();
	    addSections(sections);
	}

	private void addSections(final Set<Section> sections) {
	    for (final Section section : sections) {
		addSection(section);
	    }
	}

	private void addSection(final Section section) {
	    final SectionLink sectionLink = new SectionLink(section);
	    addComponent(sectionLink);
	    final Set<Section> subSections = section.getOrderedSections();
	    addSections(subSections);
	}

    }

    private class PageContent extends VerticalLayout {

	private class SectionPanel extends Panel {

	    private SectionPanel(final Section section) {
		super(section.getNumberedTitle());
		setStyleName(Reindeer.PANEL_LIGHT);
		final String content = section.getContents().getContent();
		addComponent(new Label(content, Label.CONTENT_RAW));
	    }

	    @Override
	    public void attach() {
	        super.attach();
	    }

	}

	public PageContent() {
	    setMargin(true, false, false, false);
	}

	@Override
	public void attach() {
	    super.attach();

	    final Set<Section> sections = page.getOrderedSections();
	    addSections(sections);
	}

	private void addSections(final Set<Section> sections) {
	    for (final Section section : sections) {
		addSection(section);
	    }
	}

	private void addSection(final Section section) {
	    final SectionPanel sectionPanel = new SectionPanel(section);
	    addComponent(sectionPanel);
	    sectionComponentMap.put(section.getExternalId(), sectionPanel);
	    final Set<Section> subSections = section.getOrderedSections();
	    addSections(subSections);
	}

    }

    public interface MenuReRenderListner extends Serializable {
	public void reRender();
    }

    private transient Page page;
    private Map<String, Component> sectionComponentMap = new HashMap<String, Component>();

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
	super.attach();

	final VerticalLayout layout = new VerticalLayout();
	setCompositionRoot(layout);
	layout.setSpacing(true);

	final Label title = new Label("<h2>" + page.getTitle().getContent() + "</h2>", Label.CONTENT_XHTML);
	title.setSizeFull();
	layout.addComponent(title);

	final PageIndex pageIndex = new PageIndex();
	layout.addComponent(pageIndex);

	final PageContent pageContent = new PageContent();
	layout.addComponent(pageContent);
/*
	renderPageMenuArea(horizontalSplitPanel);
	renderPageContent(horizontalSplitPanel);
*/
    }

    private void scrollIntoSection(final String sectionOID) {
	final Component component = sectionComponentMap.get(sectionOID);
	getWindow().scrollIntoView(component);
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
