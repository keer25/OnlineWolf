require 'test_helper'

class SessionsControllerTest < ActionController::TestCase
	def setup()
		@hash =  { :name => "Keerthana" , :email => "keerukeerthana8@gmail.com", :password => "foobars" , 
  		:password_confirmation => "foobars"}
  	User.new(@hash).save
  end	
  
  test "Proper Login" do
  	user = User.find_by(email: "keerukeerthana8@gmail.com")
  	assert user && user.authenticate("foobar") == true
  	post :create, session: { email: "keerukeerthana8@gmail.com", password: "foobars"}
  	assert_response 201	
  end

  test "Invalid Login" do
  	user = User.find_by(email: "keerukeerthana8@gmail.com")
  	assert user && user.authenticate("foobarsalad") == true
  	post :create, session: { email: "keerukeerthana8@gmail.com", password: "foobarsalad"}
  	assert_response 250	
  end

end
