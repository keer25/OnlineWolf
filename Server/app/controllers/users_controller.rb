class UsersController < ApplicationController
  def new
  	
  end

  def create
      # TODO : Remove other validations after implementing the client side in user.rb
      @user = User.new(user_params)
      logger.info(@user.errors.full_messages)
      if @user.save
        render nothing: true, status: 201
      elsif  @user.errors.full_messages.include?"Email has already been taken" 
        render nothing: true, status: 226       
      else
        render nothing: true, status: 250 
      end   
  end

  def show

  end

  def user_params
  	params.require(:user).permit(:name,:email,:password,:password_confirmation)
  end


end
