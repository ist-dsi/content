package module.contents.presentationTier.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import module.contents.domain.Page;
import module.contents.domain.Section;
import module.vaadin.ui.BennuTheme;
import pt.ist.fenixframework.pstm.AbstractDomainObject;
import pt.ist.vaadinframework.EmbeddedApplication;
import pt.ist.vaadinframework.annotation.EmbeddedComponent;
import pt.ist.vaadinframework.ui.EmbeddedComponentContainer;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout.MarginHandler;
import com.vaadin.ui.Layout.MarginInfo;
import com.vaadin.ui.Layout.SpacingHandler;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
@EmbeddedComponent(path = { "PageView" }, args = { "page", "section" })
public class PageView extends BaseComponent implements EmbeddedComponentContainer {

    private class PageIndex extends VerticalLayout {

	private class SectionLink extends Button implements ClickListener {

	    private final String sectionOID;

	    private SectionLink(final Section section) {
		super(section.getNumberedTitle());
		setStyleName(BaseTheme.BUTTON_LINK);
		sectionOID = section.getExternalId();
		final ClickListener clickListener = this;
		addListener(clickListener);
	    }

	    @Override
	    public void buttonClick(final ClickEvent event) {
		// scrollIntoSection(sectionOID);
		// getApplication().getMainWindow().open(new
		// ExternalResource("#PageView-" + page.getExternalId() + "-" +
		// sectionOID));
		EmbeddedApplication.open(getApplication(), PageView.class, page.getExternalId(), sectionOID);
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
		setStyleName(BennuTheme.PANEL_LIGHT);
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
    private transient Section scrolledSection;
    private final Map<String, Component> sectionComponentMap = new HashMap<String, Component>();

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
    public boolean isAllowedToOpen(Map<String, String> arguments) {
	return true;
    }

    @Override
    public void setArguments(Map<String, String> arguments) {
	page = AbstractDomainObject.fromExternalId(arguments.get("page"));
	scrolledSection = AbstractDomainObject.fromExternalId(arguments.get("section"));
    }

    @Override
    public void attach() {
	super.attach();

	final Panel layout = new Panel();
	final SpacingHandler content = (SpacingHandler) layout.getContent();
	content.setSpacing(true);
	final MarginHandler marginHandler = (MarginHandler) layout.getContent();
	marginHandler.setMargin(new MarginInfo(false));
	setCompositionRoot(layout);
	layout.setHeight(500, UNITS_PIXELS);
	layout.addStyleName(BennuTheme.PANEL_LIGHT);

	final Label title = new Label("<h2>" + page.getTitle().getContent() + "</h2>", Label.CONTENT_XHTML);
	title.setSizeFull();
	layout.addComponent(title);

	final PageIndex pageIndex = new PageIndex();
	layout.addComponent(pageIndex);

	final PageContent pageContent = new PageContent();
	layout.addComponent(pageContent);

	if (scrolledSection != null) {
	    scrollIntoSection(scrolledSection.getExternalId());
	}
	/*
	 * renderPageMenuArea(horizontalSplitPanel);
	 * renderPageContent(horizontalSplitPanel);
	 */
    }

    private void scrollIntoSection(final String sectionOID) {
	final Component component = sectionComponentMap.get(sectionOID);
	// getApplication().getMainWindow().scrollIntoView(component);
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
