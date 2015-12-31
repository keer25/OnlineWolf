module Server
	class SocketServer
   		KEEPALIVE_TIME = 15 # in seconds
    	def initialize(app)
			@app = app
			@clients = []
			Faye::WebSocket.load_adapter('thin')
		end	

		def call(env)
			if Faye::WebSocket.websocket?(env)
				ws = Faye::WebSocket.new(env)
				
				ws.on :open do |event|
					Rails.logger.info "Websocket Open, #{ws.object_id}"
					@clients << ws	
				end
				
				ws.on :message do |event|
					Rails.logger.info "Message : #{event.data} "
					@clients.each {|client| client.send(event.data)}
				end	
				
				ws.on :close do |event|
					Rails.logger.info "Closing the connection"
					@clients.delete(ws)
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