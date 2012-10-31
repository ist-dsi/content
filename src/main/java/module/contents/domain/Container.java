/*
 * @(#)Container.java
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
package module.contents.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;

/**
 * 
 * @author Pedro Santos
 * @author Luis Cruz
 * 
 */
public class Container extends Container_Base {

    public Container() {
	super();
	setTitle(BundleUtil.getMultilanguageString("resources.ContentResources", "label.Page.title.defualt"));
    }

    public SortedSet<Section> getOrderedSections() {
	final SortedSet<Section> sections = new TreeSet<Section>(Section.COMPARATOR_BY_ORDER);
	sections.addAll(getSectionsSet());
	return sections;
    }

    public void delete() {
	for (final Section section : getSectionsSet()) {
	    section.delete();
	}
	deleteDomainObject();
    }

    public void setTitle(final String title) {
	setTitle(getTitle().withDefault(title));
    }

    @Service
    public Section addSection() {
	return new Section(this);
    }

    @Service
    public Section addSection(final String title, final String content) {
	return new Section(this, title, content);
    }

    public boolean isPage() {
	return false;
    }

}
