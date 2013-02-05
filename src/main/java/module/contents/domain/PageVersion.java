/*
 * @(#)PageVersion.java
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
import java.util.List;

import org.joda.time.DateTime;

import pt.ist.bennu.core.applicationTier.Authenticate.UserView;
import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

/**
 * 
 * @author Paulo Abrantes
 * 
 */
public class PageVersion extends PageVersion_Base {

    public PageVersion() {
        super();
    }

    public PageVersion(VersionedPage versionedPage, int number, MultiLanguageString content) {
        setDate(new DateTime());
        setPage(versionedPage);
        setRevision(number);
        setContent(content);
        setCreator(UserView.getCurrentUser());
        versionedPage.setCurrentVersion(this);
    }

    public PageVersion(VersionedPage versionedPage, int number) {
        setDate(new DateTime());
        setPage(versionedPage);
        setRevision(number);
        setCreator(UserView.getCurrentUser());
        versionedPage.setCurrentVersion(this);
    }

    public boolean isCurrentVersion() {
        return getCurrentVersionPage() != null;
    }

    public List<FileVersion> getFiles() {
        List<FileVersion> fileVersions = new ArrayList<FileVersion>();

        for (VersionedFile file : getPage().getFiles()) {
            FileVersion fileVersion = getFile(file);
            if (fileVersion != null) {
                fileVersions.add(fileVersion);
            }
        }

        return fileVersions;
    }

    public FileVersion getFile(VersionedFile file) {
        return file.getRevisionForDate(hasNextVersion() ? getNextVersion().getDate() : new DateTime());
    }

    public boolean hasNextVersion() {
        return !isCurrentVersion();
    }

    public PageVersion getNextVersion() {
        return getPage().getVersion(getRevision() + 1);
    }

    public PageVersion getPreviousVersion() {
        return getPage().getVersion(getRevision() - 1);
    }
}
