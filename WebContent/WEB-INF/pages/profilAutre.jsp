<%@include file="/WEB-INF/pages/Include/header.jsp" %>
<body>
	

		<label class="textAlign">Pseudo:</label>${requestScope.toSee.getPseudo()}<br>

		<label class="textAlign">Rue:  </label>${requestScope.toSee.getRue()}<br>
		<label class="textAlign" >CP:	</label>${requestScope.toSee.getCodePostal()}<br>
		<label class="textAlign">Ville:</label>${requestScope.toSee.getVille()}<br>

		<label class="textAlign">Telephone:</label>	${requestScope.toSee.getTelephone()}<br>
		

	
		<%@include file="/WEB-INF/pages/Include/back.jsp" %>
	
</body>
</html>