require_relative 'room'
module Server
	class SocketServer 
   	KEEPALIVE_TIME = 15 # in seconds
    def initialize(app)
			@app = app
			@wait_num = 0
			@reception = []
			Faye::WebSocket.load_adapter('thin')
		end	

		def call(env)
			if Faye::WebSocket.websocket?(env)
				ws = Faye::WebSocket.new(env, nil, {ping: KEEPALIVE_TIME })	
				ws.on :open do |event|
					#Room request is associated with the onopen event
					Rails.logger.info "Websocket Open,  and #{ws.object_id}"
					@reception << ws	
					@wait_num += 1
					
					#Assuming events are queued
					if @wait_num >= 8
						#code to start game and send stuff and clear the current reception
						@wait_num = 0
						Room.new(@reception)
						@reception = []
					else
						#@reception.each do |client|
						#event = {}
						#event['event'] = "wait"
						#event['data'] = @wait_num.to_s
						#	client.send(event.to_json )
					  #end
					  sender( @reception, 'wait',@wait_num.to_s )
					end
				end
				
				ws.on :message do |event|
					Rails.logger.info "Message : #{event.data} "
					#@clients.each {|client| client.send(event.data)}
				end	
				
				ws.on :close do |event|
					Rails.logger.info "Closing the connection"
					# Handle players leaving room and the game
					@reception.delete(ws)
					@wait_num -= 1
					#Send an event for deleting 
					ws = nil
				end	
   				# Return async Rack response
   				ws.rack_response
   		else
   			@app.call(env)
   		end	
		end
	end
end
