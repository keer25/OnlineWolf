var conn = new WebSocket('ws://localhost:3000/');
// The action fired when you're successfully connected to a Websocket
conn.onopen = function(e) {
    console.log("Connection established!");
    document.querySelector('#messages').innerHTML += '&lt;li&gt;' + "any response?" + this.origin + '&lt;/li&gt;';
};
// This function catches all the incoming messages
conn.onmessage = function(e) {
    console.log('New message: ' + e.data);
    document.querySelector('#messages').innerHTML += '&lt;li&gt;' + e.data + '&lt;/li&gt;';
};
// This one catches all the errors
conn.onerror = function(e) {
    console.log('Error: ' + e.data);
};
// And finally this function catches the successful disconnection
conn.onclose = function(e) {
    console.log('Disconnected'); 
};
// A little event to send the current message 
document.querySelector('button#fire').onclick = function() {
    conn.send(document.querySelector('#send').value);
    document.querySelector('#send').value = '';
};};