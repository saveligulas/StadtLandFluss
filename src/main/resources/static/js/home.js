import { getCookie } from './cookies.js';
const table = document.getElementById('game-list');
const jwtToken = getCookie('Authorization');
const username = getCookie('Username');

// Function to fetch the game list and update the table
function refreshGameList() {
  let tokenWithoutPrefix = jwtToken;
  tokenWithoutPrefix = tokenWithoutPrefix.replace("Bearer ", "");
  fetch('http://192.168.1.27:8081/home/list', {
    method: 'GET',
    headers: { 
      'Authorization': jwtToken,
      'Content-Type': 'application/json',
      'HostUsername': username
    }
    })
    .then(response => response.json())
    .then(games => {
      // Clear the current table contents
      while (table.firstChild) {
        table.removeChild(table.firstChild);
      }

      // Add the new game list to the table
      const headerRow = table.insertRow(-1);
      const idHeader = headerRow.insertCell(0);
      const hostHeader = headerRow.insertCell(1);
      const usersHeader = headerRow.insertCell(2);
      idHeader.innerText = 'ID';
      hostHeader.innerText = 'Host';
      usersHeader.innerText = 'Users';

      games.forEach(game => {
        const row = table.insertRow(-1);
        const idCell = row.insertCell(0);
        const hostCell = row.insertCell(1);
        const usersCell = row.insertCell(2);
        idCell.innerText = game.id;
        hostCell.innerText = game.hostUsername;
        usersCell.innerText = game.playerCount;
      });
    });
}

// Call the function initially to populate the table
refreshGameList();

// Add event listeners to the buttons
const refreshButton = document.getElementById('refresh');
refreshButton.addEventListener('click', refreshGameList);

const fetchButton = document.getElementById('host');
fetchButton.addEventListener('click', () => {
  console.log(username);
  fetch('http://192.168.1.27:8081/home/host', {
    method: 'POST',
    headers: { 
      'Authorization': jwtToken,
      'HostUsername': username
    }
    })
    .then(response => {
      if (response.ok) {
        alert('Game created successfully!');
        refreshGameList();
        return response.text(); // return the promise here
      } else {
        throw new Error('Failed to create game');
      }
    })
    .then(async text => { // use async/await to wait for the promise to resolve
      const gameId = parseInt(text);
      console.log(gameId);
      console.log(text);
    })
    .catch(error => {
      console.error(error);
      alert('Failed to create game');
    });
});