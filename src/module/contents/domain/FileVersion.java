/*
 * @(#)FileVersion.java
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

import myorg.applicationTier.Authenticate.UserView;

import org.joda.time.DateTime;

/**
 * 
 * @author Paulo Abrantes
 * 
 */
public class FileVersion extends FileVersion_Base implements Comparable<FileVersion> {

    public FileVersion(VersionedFile file, String displayName, String filename, byte[] content, int revision) {
	super();
	setCurrentVersionFile(file);
	setFile(file);
	init(displayName, filename, content);
	setRevision(revision);
	setUploader(UserView.getCurrentUser());
	setDate(new DateTime());
    }

    public FileVersion(VersionedFile file, int revision) {
	setFile(file);
	setCurrentVersionFile(file);
	setRevision(revision);
	setUploader(UserView.getCurrentUser());
	setDate(new DateTime());
    }

    @Override
    public int compareTo(FileVersion fileVersion) {
	return Integer.valueOf(getRevision()).compareTo(fileVersion.getRevision());
    }

}
