require 'test_helper'

class UsersControllerTest < ActionController::TestCase

	def setup
	@hash =  { :name => "Keerthana" , :email => "keerukeerthana8@gmail.com", :password => "foobars" , 
  		:password_confirmation => "foobars"}
  	end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "Saving users" do
  	@user = User.new(@hash)
  	assert_difference 'User.count',1 do
  		@user.save 
  	end
  end

  test "User Exists" do
  	@user = User.new(@hash)
  	assert_difference 'User.count',1 do 
  		@user.save
  		@user.save
  	end 
  end

  test "password too short" do
  	@hash[:password] = @hash[:password_confirmation] = "123"
  	@user = User.new(@hash)
  	assert_difference 'User.count',0 do   			
  		@user.save 
  	end
  end

  test "email not valid" do 
  	@hash[:email] = "hello"
  	assert_difference 'User.count', 0 do 
  		User.new(@hash).save
  	end
  end

end
