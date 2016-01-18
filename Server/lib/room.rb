class room
	attr_accessor :no_of_players, :room_id, :players, :wolves, :doc, :cop
	def initialize(reception){
		@players = reception
		reception = reception.shuffle
		@wolves = reception[0,2]
		@doc = reception[2,1]
		@cop = reception[3,1]
		@no_of_players  = reception.length 
	}