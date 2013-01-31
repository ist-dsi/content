/*
 * @(#)Section.java
 *
 * Copyright 2009 Instituto Superior Tecnico
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
package module.contents.domain;

import java.util.Comparator;

import pt.ist.fenixWebFramework.services.Service;

/**
 * 
 * @author Pedro Santos
 * @author Paulo Abrantes
 * @author Luis Cruz
 * 
 */
public class Section extends Section_Base {

	public static final Comparator<Section> COMPARATOR_BY_ORDER = new Comparator<Section>() {

		@Override
		public int compare(final Section section1, final Section section2) {
			final int c = section1.getSectionOrder().compareTo(section2.getSectionOrder());
			return c == 0 ? section1.hashCode() - section2.hashCode() : c;
		}

	};

	public Section(final Container container) {
		super();
		setContainer(container);
	}

	public Section(final Container container, final String title, final String content) {
		this(container);
		setTitle(title);
		setContent(content);
	}

	@Override
	public void setContainer(final Container container) {
		if (container != null && container != getContainer()) {
			final int order = container.getSectionsCount() + 1;
			setSectionOrder(Integer.valueOf(order));
		}
		super.setContainer(container);
	}

	@Override
	@Service
	public void delete() {
		removeContainer();
		super.delete();
	}

	public void setContent(final String content) {
		setContents(getContents().withDefault(content));
	}

	public String getNumber() {
		return isParentAPage() ? getSectionOrder().toString() : ((Section) getContainer()).getNumber() + "." + getSectionOrder();
	}

	private boolean isParentAPage() {
		return hasContainer() && getContainer().isPage();
	}

	public int levelFromTop() {
		return isParentAPage() ? 0 : ((Section) getContainer()).levelFromTop() + 1;
	}

	public String getNumberedTitle() {
		return getNumber() + ". " + getTitle().getContent();
	}

	public Page getPage() {
		final Container parent = getContainer();
		return parent.isPage() ? (Page) parent : ((Section) parent).getPage();
	}

	@Service
	public void edit(final String title, final String content) {
		setTitle(title);
		setContent(content);
	}

}
