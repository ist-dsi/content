/*
 * @(#)ContentEditorWindow.java
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

import com.vaadin.ui.Window;

/**
 * 
 * @author Luis Cruz
 * 
 */
public class ContentEditorWindow extends Window {

    private final ContentEditorLayout layout;

    public ContentEditorWindow(final String widnowTitle, final String okButtonTitle) {
	super(widnowTitle, new ContentEditorLayout(okButtonTitle, false));
	layout = (ContentEditorLayout) getContent();
	final Window window = this;
	layout.setContentEditorCloseListner(new ContentEditorCloseListner() {
	    @Override
	    public void close() {
		window.getParent().removeWindow(window);
	    }
	});
    }

    public void setContentEditorSaveListner(final ContentEditorSaveListner contentEditorSaveListner) {
	final Window window = this;
	layout.setContentEditorSaveListner(new ContentEditorSaveListner() {
	    @Override
	    public void save(final String title, final String content) {
		contentEditorSaveListner.save(title, content);
		window.getParent().removeWindow(window);
	    }
	});
    }

}
