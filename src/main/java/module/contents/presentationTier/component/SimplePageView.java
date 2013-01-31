/*
 * @(#)SimplePageView.java
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

import java.util.Map;

import module.contents.domain.Page;
import pt.ist.vaadinframework.annotation.EmbeddedComponent;
import pt.ist.vaadinframework.ui.EmbeddedComponentContainer;

import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@EmbeddedComponent(path = { "SimplePageView" }, args = { "page" })
/**
 * 
 * @author Pedro Santos
 * @author Sérgio Silva
 * @author Luis Cruz
 * 
 */
public class SimplePageView extends BaseComponent implements EmbeddedComponentContainer {

	private transient Page page;

	@Override
	public boolean isAllowedToOpen(Map<String, String> arguments) {
		return true;
	}

	@Override
	public void setArguments(Map<String, String> arguments) {
		page = getDomainObject(arguments.get("page"));
	}

	@Override
	public void attach() {
		super.attach();

		final VerticalLayout layout = createVerticalLayout();
		setCompositionRoot(layout);

		renderPageTitleArea(layout);

		renderPageMenuArea(layout);
		renderPageContent(layout);
	}

	private void renderPageTitleArea(final AbstractComponentContainer container) {
		addTag(container, "h2", page.getTitle().getContent());
	}

	private void renderPageMenuArea(final AbstractComponentContainer container) {
		final AbstractComponentContainer menuArea = new PageMenuComponent(page);
		container.addComponent(menuArea);
	}

	private void renderPageContent(final AbstractComponentContainer container) {
		final SimplePageBodyComponent pageBodyComponent = new SimplePageBodyComponent(page);
		container.addComponent(pageBodyComponent);
	}

}
