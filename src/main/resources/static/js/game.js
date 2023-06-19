import { getCookie } from './cookies.js';
import { extractGameIdFromUrlAtEnd } from './cookies.js';
console.log("loaded");
const jwtToken = getCookie('Authorization');
const username = getCookie('Username');
const gameId = extractGameIdFromUrlAtEnd(window.location.href); 

function checkIfGameHasExpired() {
    fetch(`http://192.168.1.27:8081/game/${gameId}/`)
}