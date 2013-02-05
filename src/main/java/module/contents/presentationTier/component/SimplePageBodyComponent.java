/*
 * @(#)SimplePageBodyComponent.java
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

import com.vaadin.ui.AbstractComponentContainer;

/**
 * 
 * @author Luis Cruz
 * 
 */
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
