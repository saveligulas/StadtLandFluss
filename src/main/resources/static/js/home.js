import { getCookie } from './cookies.js';
const jwtToken = getCookie('Authorization');
const username = getCookie('Username');

// Function to fetch the game list and update the table
function refreshGameList() {
  let tokenWithoutPrefix = jwtToken;
  tokenWithoutPrefix = tokenWithoutPrefix.replace("Bearer ", "");
  fetch('/home/list', {
    method: 'GET',
    headers: { 
      'Authorization': jwtToken,
      'Username': username
    }
    })
    .then(response => response.json())
    .then(games => {
      const table = document.getElementById('game-list');
      // Clear the current table contents
      while (table.rows.length > 1) {
        table.deleteRow(1);
      }

      // Add the new game list to the table
      games.forEach(game => {
        const row = table.insertRow(-1);
        row.style.cursor = 'pointer';
        row.addEventListener('click', function() {
          joinGame(game.id);
        });
        const idCell = row.insertCell(0);
        const hostCell = row.insertCell(1);
        const usersCell = row.insertCell(2);
        idCell.innerText = game.id;
        hostCell.innerText = game.hostUsername;
        usersCell.innerText = game.playerCount + "/" + game.maxPlayers;
      });
    });
}

function joinGame(gameId) {
  fetch(`/lobby/${gameId}/connect`, {
    method: 'POST',
    headers: {
      'Authorization': jwtToken,
      'Username': username
    }
  })
  .then(response => {
    if(response.ok) {
      return response.json();
    } else {
      throw new Error('Error joining game');
    }
  })
  .then(data => {
    console.log(data);
    setTimeout(function() {
      window.location.href = `http://192.168.1.27:8081/lobby/${gameId}`;
  }, 1000);
  })
  .catch(error => {
    console.error(error);
    alert('Failed to join game');
  })
}

function checkIfPlayerIsInGame() {
  
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
      'Username': username,
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