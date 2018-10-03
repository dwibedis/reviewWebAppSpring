<%@page info="Created by Satyad during his training period"%>
<%@page import="com.review.model.Movie"%>
<%@page import="com.review.model.Review"%>
<%@page import="java.util.List"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="<c:url value="/resources/styles/Stylings.css" />"
	rel="stylesheet">
<title>MOVIE REVIEW</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="utf-8" />
</head>
<body>
	<%@include file="./headerSection.jsp"%>
	<c:choose>
		<c:when test="${not empty movie }">
			<div>
				<p class="center movieName container-fluid" id="nameOfMovie">
					Showing Results For : ${movie.movieName }</p>
			</div>
			<div id="result" class="container-fluid ">
				<br>
				<c:forEach items="${movie.reviews }" var="review">
					<div class="boxed">
						<h2>${review.sourceName }</h2>
						<c:forEach var = "i" begin = "1" end = "${review.ratingInStars }">
							<p class = "glyphicon glyphicon-star"></p>
						</c:forEach>
						<h3>${review.reviewStatement}</h3>
					</div>
					<br>
				</c:forEach>
			</div>
			<div>
				<%@include file="./askReviewSection.jsp"%>
			</div>
		</c:when>
		<c:otherwise>
			<p class="center movieName container-fluid">The largest Movie
				Review Collection of World</p>
		</c:otherwise>
	</c:choose>
</body>
<c:if test="${not empty alertStatus}">
	<c:choose>
		<c:when test="${alertStatus == true }">
			<script>
				alert("Your review has been overridden");
			</script>
		</c:when>
	</c:choose>
</c:if>
<!-- section for enabling search by enter key -->
<script>
	var input = document.getElementById("searchQuery");
	input.addEventListener("keyup", function(event) {
		event.preventDefault();
		if (event.keyCode === 13) {
			document.getElementById("searchAction").click();
		}
	});
</script>
</html>