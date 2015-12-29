class TestController < ApplicationController
	def new
		@channel = EventMachine::Channel.new
		EM.run { 	
  			EM::WebSocket.run(:host => "192.168.1.102", :port => 8080) do |ws|
  				logger.info "Waiting for handshake"
    			ws.onopen { |handshake|
      				logger.info "WebSocket connection open"
      				sid = @channel.subscribe { |msg| ws.send "hello " + msg}

      # Access properties on the EM::WebSocket::Handshake object, e.g.
      # path, query_string, origin, headers

      # Publish message to the client
      				@channel.push "#{sid}"
      				#ws.send "Ignore this: \n\n #{handshake.inspect} \n\n"
    			

    			ws.onclose do  
    				logger.info "Connection closed"
    				@channel.unsubscribe(sid)
    				end 

    			ws.onmessage { |msg|
      				logger.info "Recieved message: #{msg}"
      				ws.send "Pong: #{msg} \n"
    			}
    		}
  			end
                }
	end
end
