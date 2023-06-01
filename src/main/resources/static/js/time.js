import { getCookie } from './cookies.js';
import extractGameIdFromUrlAtEnd from './cookies.js';
const username = getCookie('Username');
const gameId = extractGameIdFromUrlAtEnd(window.location.href);
console.log(username + " " + gameId);

var testWebSocket = new WebSocket(`ws://192.168.1.27:8081/ws/test/${gameId}?username=` + encodeURIComponent(username));

testWebSocket.onopen = function(event) {
    console.log('WebSocket connection opened');
};

testWebSocket.onmessage = function(event) {
    console.log('Received message: ', event.data);
}

testWebSocket.onerror = function(event) {
    console.error('WebSocket error:', event);
};

testWebSocket.onclose = function(event) {
    console.log('WebSocket connection closed');
};