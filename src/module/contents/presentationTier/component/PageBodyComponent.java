package module.contents.presentationTier.component;

import module.contents.domain.Page;
import module.contents.domain.Section;
import module.contents.presentationTier.component.PageView.MenuReRenderListner;

import com.vaadin.ui.AbstractComponentContainer;

public class PageBodyComponent extends BaseComponent {

    private final transient Page page;
    private final MenuReRenderListner menuReRenderListner;

    public PageBodyComponent(final Page page, final MenuReRenderListner menuReRenderListner) {
	this.page = page;
	this.menuReRenderListner = menuReRenderListner;
    }

    @Override
    public void attach() {
	final AbstractComponentContainer container = createVerticalLayout();
	setCompositionRoot(container);

	for (final Section subSection : page.getOrderedSections()) {
	    addSection(container, subSection);
	}

        super.attach();
    }

    public void addSection(final Section section) {
	addSection((AbstractComponentContainer) getCompositionRoot(), section);
    }

    public void addSection(final AbstractComponentContainer container, final Section section) {
	final SectionComponent sectionComponent = new SectionComponent(section, menuReRenderListner);
	container.addComponent(sectionComponent);	
    }

}
