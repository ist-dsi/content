/*
 * @(#)AddSectionButton.java
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

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.BaseTheme;

/**
 * 
 * @author Luis Cruz
 * 
 */
public class AddSectionButton extends BaseComponent {

    private final ContentEditorSaveListner contentEditorSaveListner;

    public AddSectionButton(final ContentEditorSaveListner contentEditorSaveListner) {
	this.contentEditorSaveListner = contentEditorSaveListner;
    }

    @Override
    public void attach() {
	super.attach();

	final Button button = new Button("+ " + getMessage("label.add.section"));
	button.setStyleName(BaseTheme.BUTTON_LINK);
	button.addListener(new Button.ClickListener() {
	    @Override
	    public void buttonClick(ClickEvent event) {
		final ContentEditorWindow editorWindow = new ContentEditorWindow(getMessage("label.add.section.window.title"), "label.save");
		editorWindow.setContentEditorSaveListner(contentEditorSaveListner);
		getWindow().addWindow(editorWindow);
		editorWindow.center();
	    }
	});
	setCompositionRoot(button);
    }

}
