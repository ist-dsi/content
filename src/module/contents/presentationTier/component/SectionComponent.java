package module.contents.presentationTier.component;

import module.contents.domain.Section;
import module.contents.presentationTier.component.EditSectionButton.EditSectionSaveListner;
import module.contents.presentationTier.component.PageView.MenuReRenderListner;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class SectionComponent extends BaseComponent {

    private final transient Section section;
    private final MenuReRenderListner menuReRenderListner;

    public SectionComponent(final Section section, final MenuReRenderListner menuReRenderListner) {
	this.section = section;
	this.menuReRenderListner = menuReRenderListner;
    }

    @Override
    public void attach() {
	super.attach();

	final VerticalLayout layout = new VerticalLayout();
	setCompositionRoot(layout);

	final Component sectionBody = createSectionBody(layout);
	layout.addComponent(sectionBody);

	for (final Section subSection : section.getOrderedSections()) {
	    addSectionComponent(layout, subSection);
	}
    }

    private Component createSectionBody(final AbstractComponentContainer container) {
	final VerticalLayout sectionLayout = new VerticalLayout();
	addTag(sectionLayout, "a", null, "name", "section" + section.getNumber());
	addTag(sectionLayout, "h" + (2 + section.levelFromTop()), section.getNumberedTitle());
	if (section.getPage().canEdit()) {
	    final HorizontalLayout horizontalLayout = new HorizontalLayout();
	    horizontalLayout.setSpacing(true);
	    horizontalLayout.setMargin(true);

	    addEditSectionButton(horizontalLayout, sectionLayout, container, section);
	    addAddSectionButton(horizontalLayout, container, section);
	    sectionLayout.addComponent(horizontalLayout);
	}
	addTag(sectionLayout, "div", section.getContents().getContent());
	return sectionLayout;
    }

    private SectionComponent addSectionComponent(final AbstractComponentContainer container, final Section section) {
	final SectionComponent sectionComponent = new SectionComponent(section, menuReRenderListner);
	container.addComponent(sectionComponent);
	return sectionComponent;
    }

    private void addEditSectionButton(final AbstractComponentContainer container, final AbstractComponentContainer sectionContainer,
	    final AbstractComponentContainer newSectionContainer, final Section section) {
	final EditSectionButton editSectionButton = new EditSectionButton(section, sectionContainer, new EditSectionSaveListner() {
	    @Override
	    public void save(final String title, final String content, final AbstractComponentContainer editorContainer) {
		section.edit(title, content);
		newSectionContainer.replaceComponent(editorContainer, createSectionBody(newSectionContainer));
		menuReRenderListner.reRender();
	    }
	}, false);
	container.addComponent(editSectionButton);

	final EditSectionButton editHtmlSectionButton = new EditSectionButton(section, sectionContainer, new EditSectionSaveListner() {
	    @Override
	    public void save(final String title, final String content, final AbstractComponentContainer editorContainer) {
		section.edit(title, content);
		newSectionContainer.replaceComponent(editorContainer, createSectionBody(newSectionContainer));
		menuReRenderListner.reRender();
	    }
	}, true);
	container.addComponent(editHtmlSectionButton);
    }

    private void addAddSectionButton(final AbstractComponentContainer container, final AbstractComponentContainer sectionContainer,
	    final Section section) {
	final AddSectionButton createSectionComponent = new AddSectionButton(new ContentEditorSaveListner() {
	    @Override
	    public void save(final String title, final String content) {
		final Section newSection = section.addSection(title, content);
		addSectionComponent(sectionContainer, newSection);
		menuReRenderListner.reRender();
	    }
	});
	container.addComponent(createSectionComponent);
    }

}
