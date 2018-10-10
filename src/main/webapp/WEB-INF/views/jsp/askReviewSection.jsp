<div class="container-fluid">
	<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
		data-target="#askReview">Submit a review</button>

	<div id="askReview" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Login To Your Account To Submit A
						Review..</h4>
				</div>
				<form action="/reviewWebApp/login" method="Get">
					<div class="modal-body">
						<div class="form-group">
							<label for="userName"></label> <input class="form-control"
								type="text" id="userId" name="userId" placeholder = "user ID"required>
						</div>
						<div class="form-group">
							<label for="password"></label> <input class="form-control"
								type="password" id="password" name="password" placeholder = "password" required>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" id="reviewSubmission" class="btn btn-lg">Login</button>
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

<!--div class = "container-fluid">
	<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
		data-target="#askReview">Submit a review</button>

	<div id="askReview" class="modal fade" role="dialog">
		<div class="modal-dialog">

			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Submit Review</h4>
				</div>
				<form action="/reviewWebApp/submit" method="Get">
					<div class="modal-body">
						<div class="form-group ">
							<input class="form-control" type="number" min="1" max="10"
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
</div-->
