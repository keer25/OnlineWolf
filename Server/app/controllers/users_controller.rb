class UsersController < ApplicationController
  def new
  	
  end

  def create
  	@user = User.new(user_params)
  	logger.info "User given #{@user}"
  end

  def show

  end

  def user_params
  	params.require(:user).permit(:name,:email,:password,:password_confirmation)
  end


end
