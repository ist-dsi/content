<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<h2><bean:write name="page" property="title"/></h2>

<!-- BLOCK_HAS_CONTEXT -->

<bean:define id="sections" name="page" property="orderedSections" toScope="request"/>
<jsp:include page="traditionalSectionMenuView.jsp"/>

<bean:define id="sections" name="page" property="orderedSections" toScope="request"/>
<jsp:include page="traditionalSectionContentView.jsp"/>

<!-- END_BLOCK_HAS_CONTEXT -->
