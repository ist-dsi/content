/*
 * @(#)PageMenuComponent.java
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

import java.util.Collection;

import module.contents.domain.Page;
import module.contents.domain.Section;

import com.vaadin.ui.Label;

/**
 * 
 * @author Luis Cruz
 * 
 */
public class PageMenuComponent extends BaseComponent {

	private final transient Page page;

	public PageMenuComponent(final Page page) {
		this.page = page;
	}

	@Override
	public void attach() {
		super.attach();
		setCompositionRoot(ProgressFactory.createCenteredProgressIndicator());
		new Worker().start();
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
		htmlTag(stringBuilder, "a", section.getTitle().getContent(), "href", "#section" + section.getNumber());
		addMenu(stringBuilder, section.getOrderedSections());
		stringBuilder.append("</li>");
	}

	public class Worker extends UserTransactionalThread {

		@Override
		public void doIt() {
			final StringBuilder stringBuilder = new StringBuilder();
			addMenu(stringBuilder, page.getOrderedSections());
			stringBuilder.append("<br/>");

			final Label content = new Label(stringBuilder.toString(), Label.CONTENT_XHTML);

//            synchronized (getApplication()) {
			setCompositionRoot(content);
//            }

			requestRepaint();
		}
	}

}
