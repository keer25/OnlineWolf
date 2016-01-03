class SessionsController < ApplicationController
	def new

	end
  def create
  	user = User.find_by(email: params[:session][:email])
  	if user && user.authenticate(params[:session][:password])
  		logger.info "Valid User"
  		log_in user
  		render nothing: true, status: 201
  	else
  		logger.info "Invalid Details..."
  		render nothing: true, status: 250
  	end
  end

  def destroy

  end
end
