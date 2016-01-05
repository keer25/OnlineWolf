class SessionsController < ApplicationController
  def create
  	user = User.find_by(email: params[:session][:email])
  	if user && user.authenticate(params[:session][:password])
  		 logger.info "Valid User"
  		render nothing: true, status: 201
  		#render plain: "Logged in"
  	else
  		logger.info "Invalid Details..."
  		render nothing: true, status: 250
  		#render plain "Invalid Details"
  	end
  end

end
