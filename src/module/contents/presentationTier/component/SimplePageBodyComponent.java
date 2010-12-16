package module.contents.presentationTier.component;

import module.contents.domain.Page;
import module.contents.domain.Section;

import com.vaadin.ui.AbstractComponentContainer;

public class SimplePageBodyComponent extends BaseComponent {

    private final transient Page page;

    public SimplePageBodyComponent(final Page page) {
	this.page = page;
    }

    @Override
    public void attach() {
	super.attach();

	final AbstractComponentContainer container = createVerticalLayout();
	setCompositionRoot(container);

	for (final Section subSection : page.getOrderedSections()) {
	    addSection(container, subSection);
	}
    }

    public void addSection(final AbstractComponentContainer container, final Section section) {
	final SimpleSectionComponent sectionComponent = new SimpleSectionComponent(section);
	container.addComponent(sectionComponent);	
    }

}
