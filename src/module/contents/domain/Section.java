/*
 * @(#)Section.java
 *
 * Copyright 2009 Instituto Superior Tecnico
 * Founding Authors: João Figueiredo, Luis Cruz, Paulo Abrantes, Susana Fernandes
 * 
 *      https://fenix-ashes.ist.utl.pt/
 * 
 *   This file is part of the Contents Module for the MyOrg web application.
 *
 *   The Contents Module is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published
 *   by the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.*
 *
 *   The Contents Module is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with the Contents Module. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package module.contents.domain;

import java.util.Comparator;

import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.Language;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

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

    @Override
    public void setContainer(final Container container) {
	if (container != null && container != getContainer()) {
	    final int order = container.getSectionsCount() + 1;
	    setSectionOrder(Integer.valueOf(order));
	}
	super.setContainer(container);
    }

    @Service
    public void delete() {
	removeContainer();
	super.delete();
    }

    public void setContent(final String content) {
	final MultiLanguageString contents = new MultiLanguageString();
	final Language userLanguage = Language.getUserLanguage();
	if (getContents() != null) {
	    for (final Language language : getContents().getAllLanguages()) {
		if (language != userLanguage) {
		    contents.setContent(language, getContents().getContent(language));
		}
	    }
	}
	contents.setContent(content);
	setContents(contents);
    }

}
