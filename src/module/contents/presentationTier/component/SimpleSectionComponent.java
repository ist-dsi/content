package module.contents.presentationTier.component;

import module.contents.domain.Section;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class SimpleSectionComponent extends BaseComponent {

    private final transient Section section;

    public SimpleSectionComponent(final Section section) {
	this.section = section;
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
	addTag(sectionLayout, "div", section.getContents().getContent());
	return sectionLayout;
    }

    private SimpleSectionComponent addSectionComponent(final AbstractComponentContainer container, final Section section) {
	final SimpleSectionComponent sectionComponent = new SimpleSectionComponent(section);
	container.addComponent(sectionComponent);
	return sectionComponent;
    }

}
