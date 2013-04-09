/*
 * @(#)VersionedFile.java
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

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.DateTime;

import pt.ist.fenixframework.Atomic;

/**
 * 
 * @author Paulo Abrantes
 * 
 */
public class VersionedFile extends VersionedFile_Base {

    public VersionedFile() {
        new FileVersion(this, 0);
    }

    @Atomic
    public void addVersion() {
        new FileVersion(this, getCurrentVersion().getRevision() + 1);
    }

    public VersionedFile(String displayName, String filename, byte[] content) {
        super();
        setPresentationName(displayName);
        new FileVersion(this, displayName, filename, content, 0);
    }

    public FileVersion getRevisionForDate(DateTime date) {
        Set<FileVersion> revisions = new TreeSet<FileVersion>(new Comparator<FileVersion>() {
            @Override
            public int compare(FileVersion arg0, FileVersion arg1) {
                return -1 * arg0.compareTo(arg1);
            }
        });

        revisions.addAll(getFileVersions());
        for (FileVersion revision : revisions) {
            if (revision.getDate().isBefore(date)) {
                return revision;
            }
        }

        return null;
    }

    public int getCurrentRevision() {
        return getCurrentVersion().getRevision();
    }
}
