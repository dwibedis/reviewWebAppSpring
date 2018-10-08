<div class = "container-fluid">
	<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
		data-target="#askReview">Submit a review</button>

	<!-- Modal -->
	<div id="askReview" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Submit Review</h4>
				</div>
				<form action="/reviewWebApp/submit" method="Get">
					<div class="modal-body">
						<div class="form-group ">
							<input class="form-control" type="number" min="0" max="5"
								id="rating" name="rating" placeholder="rating" required>
						</div>
						<div class="form-group ">
							<textarea class="form-control" rows="5" id="review"
								name="reviewStatement" required></textarea>
						</div>
						<div class="form-group ">
							<input class="form-control" type="text" name="movieName"
								id="movieName" placeholder="Actual Movie name" required>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="reviewSubmission" class="btn btn-lg">Submit</button>
						<input type="text" id="isNewReviewer" name="isNewReviewer"
							class="hidden" value="${isNewReviewer }" /> <input type="text"
							id="akaMovieName" name="akaMovieName" class="hidden"
							value="${movie.movieName }">
					</div>
				</form>
			</div>

		</div>
	</div>
</div>
