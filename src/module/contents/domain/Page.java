/*
 * @(#)Page.java
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

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import myorg.domain.MyOrg;
import myorg.util.BundleUtil;
import pt.ist.fenixWebFramework.services.Service;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

public class Page extends Page_Base {

    public Page() {
	super();
	setMyOrg(MyOrg.getInstance());
	setLink(BundleUtil.getMultilanguageString("resources.ContentResources", "label.Page.link.defualt"));
    }

    @Service
    public void reorderSections(final ArrayList<Section> sections) {
	for (final Section section : getSectionsSet()) {
	    if (!sections.contains(section)) {
		throw new Error("Sections changed!");
	    }
	}

	int i = 0;
	for (final Section section : sections) {
	    if (!hasSections(section)) {
		throw new Error("Sections changed!");
	    }
	    section.setSectionOrder(Integer.valueOf(i++));
	}
    }

    @Override
    public void delete() {
	removeMyOrg();
        super.delete();
    }

    @Service
    public static Page createNewPage() {
	return new Page();
    }

    @Service
    public void setTitle(final String content) {
	final MultiLanguageString title = getTitle();
	title.setContent(content);
	setTitle(title);
    }

}
