<div id="askReview" class="container-fluid">
	<form action="/reviewWebApp/submit" method="Get">
		<div class="form-group">
			<label>Submit Review:</label> <br> <input type="number" min="0"
				max="5" id="rating" name="rating" placeholder="rating" required> <br>
			<textarea class="form-control" rows="5" id="review"
				name="reviewStatement" required></textarea>
		</div>
		<c:choose>
			<c:when test="${isNewReviewer == false }">
				<button type="submit" id="reviewSubmission" class="btn btn-lg">Override</button>
			</c:when>
			<c:otherwise>
				<button type="submit" id="reviewSubmission" class="btn btn-lg">Submit</button>
			</c:otherwise>
		</c:choose>
		<input type="text" id="isNewReviewer" name="isNewReviewer"
			class="hidden" value="${isNewReviewer }" /> <input type="text"
			id="movieName" name="movieName" class="hidden"
			value="${movie.movieName }">
	</form>
</div>

