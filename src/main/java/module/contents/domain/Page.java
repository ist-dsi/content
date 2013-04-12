/*
 * @(#)Page.java
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

import java.util.ArrayList;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.MyOrg;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author Pedro Santos
 * @author Luis Cruz
 * @author Paulo Abrantes
 * 
 */
public class Page extends Page_Base {

    public Page() {
        super();
        setMyOrg(MyOrg.getInstance());
        setLink(BundleUtil.getMultilanguageString("resources.ContentResources", "label.Page.link.defualt"));
    }

    @Atomic
    public void reorderSections(final ArrayList<Section> sections) {
        for (final Section section : getSectionsSet()) {
            if (!sections.contains(section)) {
                throw new Error("Sections changed!");
            }
        }

        int i = 0;
        for (final Section section : sections) {
            if (!getSectionsSet().contains(section)) {
                throw new Error("Sections changed!");
            }
            section.setSectionOrder(Integer.valueOf(i++));
        }
    }

    @Override
    public void delete() {
        setMyOrg(null);
        super.delete();
    }

    @Atomic
    public static Page createNewPage() {
        return new Page();
    }

    @Override
    @Atomic
    public void setTitle(final String content) {
        setTitle(getTitle().withDefault(content));
    }

    @Override
    public boolean isPage() {
        return true;
    }

    public boolean canEdit() {
        final User user = UserView.getCurrentUser();
        return user.hasRoleType(RoleType.MANAGER);
    }

}
