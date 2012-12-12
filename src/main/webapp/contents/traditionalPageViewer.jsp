<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>

<h2><bean:write name="page" property="title"/></h2>

<%= pt.ist.fenixWebFramework.servlets.filters.contentRewrite.RequestRewriter.BLOCK_HAS_CONTEXT_PREFIX %>

<bean:define id="sections" name="page" property="orderedSections" toScope="request"/>
<jsp:include page="traditionalSectionMenuView.jsp"/>

<bean:define id="sections" name="page" property="orderedSections" toScope="request"/>
<jsp:include page="traditionalSectionContentView.jsp"/>

<%= pt.ist.fenixWebFramework.servlets.filters.contentRewrite.RequestRewriter.END_BLOCK_HAS_CONTEXT_PREFIX %>
