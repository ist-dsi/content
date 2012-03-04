/*
 * @(#)VersionedPageNode.java
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

import myorg.domain.VirtualHost;
import myorg.domain.contents.Node;
import myorg.domain.groups.AnyoneGroup;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * 
 * @author Nuno Diegues
 * @author Paulo Abrantes
 * 
 */
public class VersionedPageNode extends VersionedPageNode_Base {

    public VersionedPageNode() {
	super();
	setAccessibilityGroup(AnyoneGroup.getInstance());
    }

    public VersionedPageNode(final VirtualHost virtualHost, final Node parentNode, final VersionedPage page, final Integer order) {
	this();
	init(virtualHost, parentNode, order);
	setPage(page);
    }

    @Override
    public Object getElement() {
	return getPage();
    }

    @Override
    public void delete() {
	removePage();
	super.delete();
    }

    @Override
    public MultiLanguageString getLink() {
	final VersionedPage page = getPage();
	return page.getTitle();
    }

    @Override
    protected void appendUrlPrefix(final StringBuilder stringBuilder) {
	stringBuilder.append("/pageVersioning.do?method=viewPage");
    }

    @Override
    public boolean isAcceptsFunctionality() {
	return false;
    }

}
