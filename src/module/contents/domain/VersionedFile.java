package module.contents.domain;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.DateTime;

import pt.ist.fenixWebFramework.services.Service;

public class VersionedFile extends VersionedFile_Base {

    public VersionedFile() {
	new FileVersion(this, 0);
    }

    @Service
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
