<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>

<h2>Editar</h2>

<bean:define id="pageId" name="page" property="externalId"/>

<fr:edit id="pageBean" name="pageBean" schema="module.contents.domain.VersionedPageBean.edit"
		action="<%= "/pageVersioning.do?method=editPage&pageId=" + pageId %>">
	<fr:layout name="tabular">
	</fr:layout>
	<fr:destination name="cancel" path="/pageVersioning.do?method=viewPage"/>
</fr:edit>
