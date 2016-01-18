# Number of doc,cop and villagers
$dn = 1
$cn = 1
$wn = 2
$vn = 4

#Sends to each of the connection in the list the event
def sender(list,event,data)
	eventjson = {}
	eventjson['event'] = event
	eventjson['data'] = data
	list.each { |ws| ws.send(eventjson.to_json) }
end
class Room
	attr_accessor :room_id, :vil, :wolves, :doc, :cop
	def initialize(reception)
		# Assigning the players to be wolves, etc..
		reception = reception.shuffle
		@wolves = reception[0,$wn]
		@doc = reception[$wn,$dn]
		@cop = reception[$wn+$dn,$cn]
		@vil = reception[$wn+$dn+$cn,$vn]
		#@no_of_players  = reception.length

		#Sending assign task to client 
		sender(@wolves,'assign','wolf')

		sender(@doc,'assign','doctor')

    sender(@cop,'assign','cop')

    sender(@vil,'assign','villager')


	end
end