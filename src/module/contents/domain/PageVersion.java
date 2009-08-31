package module.contents.domain;

import java.util.ArrayList;
import java.util.List;

import myorg.applicationTier.Authenticate.UserView;

import org.joda.time.DateTime;

import pt.utl.ist.fenix.tools.util.i18n.MultiLanguageString;

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
