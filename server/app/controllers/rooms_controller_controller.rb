class RoomsControllerController < WebsocketRails::BaseController
	def initialize_session
		# initializes a data store of the number of rooms currently active
		controller_store[:active_rooms] = 0
	end	
end
