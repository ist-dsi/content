/*
 * @(#)SimpleSectionComponent.java
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

import module.contents.domain.Section;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author Luis Cruz
 * 
 */
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
