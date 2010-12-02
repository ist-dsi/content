package module.contents.presentationTier.vaadin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import module.contents.domain.Page;
import module.contents.presentationTier.vaadin.ContentEditorLayout.ContentEditorCloseListner;
import module.contents.presentationTier.vaadin.ContentEditorLayout.ContentEditorSaveListner;
import myorg.domain.RoleType;
import pt.ist.fenixframework.pstm.AbstractDomainObject;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public class SectionedPageViewer extends ContentComponent {

    private transient Page page;

    private final List<Section> sections = new ArrayList<Section>();

    private final Label menu = new Label("", Label.CONTENT_XHTML);

    private AbstractLayout notificationLayout = new VerticalLayout();

    private boolean canEditPage() {
	return hasRole(RoleType.MANAGER);
    }

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
	sections.clear();
	sections.addAll(Section.getPageSections(page));

	final VerticalLayout layout = createRootVerticalLayout();

	if (canEditPage()) {
	    layout.addComponent(notificationLayout);
	    notificationLayout.addComponent(new Label("<!-- Place holder -->", Label.CONTENT_XHTML));
	}

	addTag(layout, "h2", page.getTitle().getContent());

	layout.addComponent(menu);
	renderMenu();
	if (canEditPage()) {
	    addAddSectionButton(layout);
	}

	addSections(layout, sections);

        super.attach();
    }

    private void renderMenu() {
	final StringBuilder stringBuilder = new StringBuilder();
	addMenu(stringBuilder, sections);
	menu.setValue(stringBuilder.toString());
	menu.requestRepaint();
    }

    private void addMenu(final StringBuilder stringBuilder, final Collection<Section> sections) {
	if (!sections.isEmpty()) {
	    stringBuilder.append("<ol>");
	    for (final Section section : sections) {
		addMenu(stringBuilder, section);
	    }
	    stringBuilder.append("</ol>");
	}
    }

    private void addMenu(final StringBuilder stringBuilder, final Section section) {
	stringBuilder.append("<li>");
	htmlTag(stringBuilder, "a", section.getTitle(), "href", "#" + section.getReference());
	addMenu(stringBuilder, section.getSections());
	stringBuilder.append("</li>");
    }

    private void addSection(final AbstractLayout layout, final Section section) {
	final VerticalLayout sectionLayout = createVerticalLayout();
	layout.addComponent(sectionLayout);
	redrawSection(sectionLayout, section);
	addSections(layout, section.getSections());
    }

    private void redrawSection(final AbstractLayout sectionLayout, final Section section) {
	addTag(sectionLayout, "a", null, "name", section.getReference());
	addTag(sectionLayout, "h" + (2 + section.levelFromTop()), section.getNumberedTitle());
	if (canEditPage()) {
	    addEditSectionButton(sectionLayout, section);
	}
	addTag(sectionLayout, "div", section.getContent());
    }

    private void addSections(final AbstractLayout layout, final Collection<Section> sections) {
	for (final Section subSection : sections) {
	    addSection(layout, subSection);
	}
    }

    private void notifyChanges() {
	final HorizontalLayout horizontalLayout = new HorizontalLayout();

	final Button saveButton = new Button(getMessage("label.save"));
	saveButton.addListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		Section.saveContainer(page, sections);
		resertSaveChangesLayout();
	    }
	});
	horizontalLayout.addComponent(saveButton);

	final SectionedPageViewer sectionedPageViewer = this;
	final Button cancelButton = new Button(getMessage("label.cancel"));
	cancelButton.addListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		resertSaveChangesLayout();
		sectionedPageViewer.detach();
		sectionedPageViewer.attach();
	    }
	});
	horizontalLayout.addComponent(cancelButton);

	final VerticalLayout verticalLayout = new VerticalLayout();
	verticalLayout.addComponent(horizontalLayout);

	final AbstractLayout layout = (AbstractLayout) getCompositionRoot();
	layout.replaceComponent(notificationLayout, verticalLayout);
	layout.requestRepaint();
	notificationLayout = verticalLayout;

	final Window window = getWindow();
	final Notification notification = new Notification(
		getMessage("label.unsabed.changes.title"),
		getMessage("label.unsabed.changes.question"),
		Notification.TYPE_TRAY_NOTIFICATION);
	notification.setDelayMsec(5000);
	notification.setPosition(Notification.POSITION_BOTTOM_RIGHT);
	window.showNotification(notification);
    }

    private void resertSaveChangesLayout() {
	final VerticalLayout verticalLayout = new VerticalLayout();
	verticalLayout.addComponent(new Label("<!-- Place holder -->", Label.CONTENT_XHTML));
	final AbstractLayout layout = (AbstractLayout) getCompositionRoot();
	layout.replaceComponent(notificationLayout, verticalLayout);
	notificationLayout = verticalLayout;
    }

    private void addAddSectionButton(final VerticalLayout layout) {
	final Button button = new Button("+ " + getMessage("label.add.section"));
	button.setStyleName(BaseTheme.BUTTON_LINK);
	layout.addComponent(button);
	button.addListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		final ContentEditorWindow editorWindow = new ContentEditorWindow(getMessage("label.add.section.window.title"));
		editorWindow.setContentEditorSaveListner(new ContentEditorSaveListner() {
		    @Override
		    public void save(final String title, final String content) {
	        	final Section section = new Section(sections.size() + 1, title, content);
	        	sections.add(section);
	        	addSection((AbstractLayout) getCompositionRoot(), section);
	        	renderMenu();

	        	notifyChanges();
		    }
		});
		getWindow().addWindow(editorWindow);
		editorWindow.center();
	    }
	});
    }

    private void addEditSectionButton(final AbstractLayout sectionLayout, final Section section) {
	final Button button = new Button(getMessage("label.edit"));
	button.setStyleName(BaseTheme.BUTTON_LINK);
	sectionLayout.addComponent(button);
	button.addListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		final ContentEditorLayout contentEditorLayout = new ContentEditorLayout(section.getTitle(), section.getContent());

		final AbstractLayout layout = (AbstractLayout) getCompositionRoot();
		layout.replaceComponent(sectionLayout, contentEditorLayout);

		contentEditorLayout.setContentEditorSaveListner(new ContentEditorSaveListner() {
		    @Override
		    public void save(String title, String content) {
			section.setTitle(title);
			section.setContent(content);
			final VerticalLayout sectionLayout = createVerticalLayout();
			redrawSection(sectionLayout, section);
			layout.replaceComponent(contentEditorLayout, sectionLayout);
			renderMenu();
			notifyChanges();
		    }
		});

		contentEditorLayout.setContentEditorCloseListner(new ContentEditorCloseListner() {
		    @Override
		    public void close() {
			layout.replaceComponent(contentEditorLayout, sectionLayout);
		    }
		});
	    }
	});
    }

}
