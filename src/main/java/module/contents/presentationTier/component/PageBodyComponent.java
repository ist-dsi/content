/*
 * @(#)PageBodyComponent.java
 *
 * Copyright 2010 Instituto Superior Tecnico
 * Founding Authors: Luis Cruz, Paulo Abrantes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Content Module.
 *
 *   The Content Module is free software: you can
 *   redistribute it and/or modify it under the terms of the GNU Lesser General
 *   Public License as published by the Free Software Foundation, either version 
 *   3 of the License, or (at your option) any later version.
 *
 *   The Content Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Content Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package module.contents.presentationTier.component;

import module.contents.domain.Page;
import module.contents.domain.Section;
import module.contents.presentationTier.component.PageView.MenuReRenderListner;

import com.vaadin.ui.AbstractComponentContainer;

/**
 * 
 * @author Luis Cruz
 * 
 */
public class PageBodyComponent extends BaseComponent {

    private final transient Page page;
    private final MenuReRenderListner menuReRenderListner;

    public PageBodyComponent(final Page page, final MenuReRenderListner menuReRenderListner) {
        this.page = page;
        this.menuReRenderListner = menuReRenderListner;
    }

    @Override
    public void attach() {
        super.attach();
        setCompositionRoot(ProgressFactory.createCenteredProgressIndicator());
        new Worker().start();
    }

    public void addSection(final Section section) {
        addSection((AbstractComponentContainer) getCompositionRoot(), section);
    }

    public void addSection(final AbstractComponentContainer container, final Section section) {
        final SectionComponent sectionComponent = new SectionComponent(section, menuReRenderListner);
        container.addComponent(sectionComponent);
    }

    public class Worker extends UserTransactionalThread {

        @Override
        public void doIt() {
            final AbstractComponentContainer container = createVerticalLayout();

            for (final Section subSection : page.getOrderedSections()) {
                addSection(container, subSection);
            }

//	    synchronized (getApplication()) {
            setCompositionRoot(container);
//	    }

            requestRepaint();
        }
    }

}
