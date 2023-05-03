const ws = new WebSocket('ws://192.168.1.27:8081/ws/502',);

ws.onmessage = function(event) {
    console.log('Received message:', event.data);
};

ws.onopen = function(event) {
    console.log('WebSocket connection opened');
};

ws.onerror = function(event) {
    console.error('WebSocket error:', event);
};

ws.onclose = function(event) {
    console.log('WebSocket connection closed');
};