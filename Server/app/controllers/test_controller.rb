class TestController < ApplicationController
	def new
		EM.run { 
  			EM::WebSocket.run(:host => "127.0.0.1", :port => 8080) do |ws|
  				logger.info "Waiting for handshake"
    			ws.onopen { |handshake|
      				logger.info "WebSocket connection open"

      # Access properties on the EM::WebSocket::Handshake object, e.g.
      # path, query_string, origin, headers

      # Publish message to the client
      				ws.send "Hello Client, you connected "
      				ws.send "Ignore this: \n\n #{handshake.inspect} \n\n"
    			}

    			ws.onclose { puts "Connection closed" }

    			ws.onmessage { |msg|
      				logger.info "Recieved message: #{msg}"
      				ws.send "Pong: #{msg} \n"
    			}
  			end
                }
	end
end
