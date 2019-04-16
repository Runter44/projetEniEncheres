<%@include file="/WEB-INF/pages/Include/header.jsp" %>
<body class="">
	<c:if test="${!empty param.modif}">
	<%@include file="/WEB-INF/pages/Profil/inModif.jsp" %>
	</c:if>
	<c:if test="${empty param.modif}">
	<%@include file="/WEB-INF/pages/Profil/inAffichage.jsp" %>
	</c:if>
</body>
</html>