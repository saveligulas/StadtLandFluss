import { getCookie } from './cookies.js';
import { extractGameIdFromUrlAtEnd } from './cookies.js';
console.log("loaded");
const jwtToken = getCookie('Authorization');
const username = getCookie('Username');
const gameId = extractGameIdFromUrlAtEnd(window.location.href); 

function checkIfGameHasExpired() { //TODO implement
    fetch(`http://192.168.1.27:8081/game/${gameId}/expired`, {
        method: 'GET',
        headers: {
            'Authorization': jwtToken,
            'Username': username
        }
    })
    .then(response => response.json) 
    .then(data => {
        console.log(data);
        alert("Game has expired");
        window.location.href = "http://192.168.1.27:8081/home";
    })
}