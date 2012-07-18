<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>

<h2><bean:message key="label.content.page.new.title" bundle="MYORG_RESOURCES" /></h2>
<fr:edit id="pageBean" name="pageBean" schema="module.contents.domain.VersionedPageBean"
		action="/pageVersioning.do?method=createNewPage">
	<fr:layout name="tabular">
	</fr:layout>
</fr:edit>
