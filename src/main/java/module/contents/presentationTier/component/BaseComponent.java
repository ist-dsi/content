/*
 * @(#)BaseComponent.java
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

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.ist.bennu.core.domain.RoleType;
import pt.ist.bennu.core.domain.User;
import pt.ist.bennu.core.util.BundleUtil;
import pt.ist.fenixframework.DomainObject;
import pt.ist.fenixframework.FenixFramework;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.SplitPanel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
/**
 * 
 * @author Luis Cruz
 * 
 */
public abstract class BaseComponent extends CustomComponent {

    protected <T extends DomainObject> T getDomainObject(final String externalId) {
        return FenixFramework.<T> getDomainObject(externalId);
    }

    protected String getBundle() {
        return "resources.ContentResources";
    }

    protected String getMessage(final String key, String... args) {
        return BundleUtil.getFormattedStringFromResourceBundle(getBundle(), key, args);
    }

    protected boolean hasRole(final RoleType roleType) {
        final User user = UserView.getCurrentUser();
        return user.hasRoleType(roleType);
    }

    protected VerticalLayout createVerticalLayout(final AbstractComponentContainer container) {
        final VerticalLayout layout = createVerticalLayout();
        container.addComponent(layout);
        return layout;
    }

    protected VerticalLayout createVerticalLayout() {
        final VerticalLayout layout = new VerticalLayout();
//	layout.setSizeFull();
//	layout.setSpacing(true);
//	layout.setMargin(true);
        layout.setMargin(false, false, true, true);
        return layout;
    }

    protected SplitPanel createSplitPanelLayout(final AbstractComponentContainer container, final int orientation,
            final boolean locked, final String height, final String width, final int splitPositionPos,
            final int splitPositionUnits) {
        final SplitPanel splitPanel =
                createSplitPanelLayout(orientation, locked, height, width, splitPositionPos, splitPositionUnits);
        container.addComponent(splitPanel);
        return splitPanel;
    }

    protected SplitPanel createSplitPanelLayout(final int orientation, final boolean locked, final String height,
            final String width, final int splitPositionPos, final int splitPositionUnits) {
        final SplitPanel splitPanel = new SplitPanel();
        splitPanel.setLocked(locked);
        splitPanel.setHeight(height);
        splitPanel.setWidth(width);
        splitPanel.setSplitPosition(splitPositionPos, splitPositionUnits);
        splitPanel.setOrientation(orientation);
        return splitPanel;
    }

    protected Label addTag(final AbstractComponentContainer container, final String tag, final String content,
            final String... params) {
        final Label label = new Label(htmlTag(tag, content, params), Label.CONTENT_XHTML);
        container.addComponent(label);
        return label;
    }

    protected String htmlTag(final String tag, final String content, final String... params) {
        final StringBuilder stringBuilder = new StringBuilder();
        htmlTag(stringBuilder, tag, content, params);
        return stringBuilder.toString();
    }

    protected void htmlTag(final StringBuilder stringBuilder, final String tag, final String content, final String... params) {
        stringBuilder.append('<').append(tag).append(' ');
        if (params != null) {
            for (int i = 0; i < params.length; i++, i++) {
                final String paramName = params[i];
                stringBuilder.append(paramName).append("=\"");
                final String paramValue = params.length > i + 1 ? params[i + 1] : null;
                if (paramValue != null) {
                    stringBuilder.append(paramValue);
                }
                stringBuilder.append("\" ");
            }
        }
        if (content == null) {
            stringBuilder.append("/>");
        } else {
            stringBuilder.append('>');
            stringBuilder.append(content);
            stringBuilder.append("</").append(tag).append('>');
        }
    }

}
