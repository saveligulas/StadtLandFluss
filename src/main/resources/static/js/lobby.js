import { getCookie } from './cookies.js';
import { extractGameIdFromUrlAtEnd } from './cookies.js';
import { checkIfGameHasExpired } from './game.js';
console.log("loaded");
const disconnectButton = document.querySelector('#disconnect');
const jwtToken = getCookie('Authorization');
const username = getCookie('Username');
const gameId = extractGameIdFromUrlAtEnd(window.location.href);
const startButton = document.querySelector('#startGame');

var ws = new WebSocket(`ws://192.168.1.27:8081/ws/${gameId}?username=` + encodeURIComponent(username));

ws.onopen = function(event) {
  console.log('WebSocket connection opened');
};

ws.onmessage = function(event) {
  console.log('Received message: ', event.data);
  if(event.data == "RELOAD_PAGE") {
    initializePage();
  }
};

ws.onerror = function(event) {
  console.log('Websocket error:', event);
};

window.addEventListener('DOMContentLoaded', initializePage);

function initializePage() {
  checkIfGameHasExpired();
  checkIfGameHasStarted();
  const checkHostAddress = `${window.location.href}/host/check`;
  console.log(checkHostAddress);
  fetch(checkHostAddress , {
    method: 'GET',
      headers: {
        'Authorization': jwtToken,
        'Username': username,
        'HostUsername': username
      }
  })
  .then(response => response.json())
  .then(data => {
    const showAddCategory = data;
    console.log(showAddCategory);
    // Check if the category input field and remove buttons should be displayed
    if (showAddCategory) {
      // Check if the container already exists
      const containerExists = document.getElementById('addCategoryContainer');
      if (!containerExists) {
        // Create the input field and the "Add" button
        const newCategoryNameInput = document.createElement('input');
        newCategoryNameInput.type = 'text';
        newCategoryNameInput.id = 'newCategoryName';
        newCategoryNameInput.placeholder = 'Enter category name';
        const addCategoryButton = document.createElement('button');
        addCategoryButton.type = 'button';
        addCategoryButton.id = 'addCategoryButton';
        addCategoryButton.textContent = 'Add';

        addCategoryButton.addEventListener('click', function(event) {
          const categoryName = document.getElementById('newCategoryName').value;
          sendAddCategoryRequest(categoryName);
          initializePage();
        });
    
        // Create the container and add the input field and the "Add" button to it
        const addCategoryContainer = document.createElement('div');
        addCategoryContainer.id = 'addCategoryContainer';
        addCategoryContainer.appendChild(newCategoryNameInput);
        addCategoryContainer.appendChild(addCategoryButton);
    
        // Insert the container before the table
        const categoriesTable = document.getElementById('categoriesTable');
        categoriesTable.parentNode.insertBefore(addCategoryContainer, categoriesTable);
      }
    }
    loadInfo(showAddCategory);
  })
}

window.addEventListener('DOMContentLoaded', initializePage);

function loadInfo(showRemoveButtons) {
  const infoAddress = `${window.location.href}/info`;
  fetch(infoAddress, {
    method: 'GET',
    headers: {
      'Authorization': jwtToken,
      'Username': username,
      'Content-Type': 'application/json'
    }
  })
    .then(response => response.json())
    .then(info => {
      console.log(info);
      const players = info.players;
      const rounds = info.rounds;
      const categoryNames = info.categoryNames;
      const id = info.id;

      const gameIdElement = document.querySelector('#gameId');
      gameIdElement.textContent = `Game ID: ${id}`;

      const roundsElement = document.querySelector('#rounds');
      roundsElement.textContent = `Number of Rounds: ${rounds}`;

      fillPlayersTable(players);
      fillCategoriesTable(categoryNames, showRemoveButtons);
    })
    .catch(error => {
      console.error('Error loading players:', error);
    });
}

function fillPlayersTable(players) {
  const tableBody = document.querySelector('#playersTable tbody');
  tableBody.innerHTML = ''; // clear the table before filling it with new data

  for (let i = 0; i < players.length; i++) {
    const player = players[i];

    const row = document.createElement('tr');
    const playerName = document.createElement('td');
    playerName.textContent = player;

    row.appendChild(playerName);
    tableBody.appendChild(row);
  }
}

function fillCategoriesTable(categoryStrings, showRemoveButton) {
  const categoriesTableBody = document.querySelector('#categoriesTable tbody');
  categoriesTableBody.innerHTML = '';

  categoryStrings.forEach((categoryString) => {
    const row = document.createElement('tr');
    const categoryCell = document.createElement('td');
    categoryCell.textContent = categoryString;
    row.appendChild(categoryCell);

    if (showRemoveButton) {
      const actionsCell = document.createElement('td');
      const removeButton = document.createElement('button');
      removeButton.type = 'button';
      removeButton.classList.add('remove-category-button');
      removeButton.dataset.categoryName = categoryString;
      removeButton.textContent = 'Remove';
      removeButton.addEventListener('click', function(event) {
        const row = event.target.parentNode.parentNode;
    
        const categoryCell = row.querySelector('td:first-child');

        const category = categoryCell.textContent.trim();

        console.log(`Category: ${category}`);

        sendCategoryRemoveRequest(category);

        initializePage();
      });
      actionsCell.appendChild(removeButton);
      row.appendChild(actionsCell);
    }

    categoriesTableBody.appendChild(row);
  });
};

const sendAddCategoryRequest = (categoryNames) => {
  fetch(`http://192.168.1.27:8081/lobby/${gameId}/host/settings?categories=${categoryNames}`, {
    method: 'POST',
    headers: {
      'Authorization': jwtToken,
      'Username': username
    }
  })
};

const sendCategoryRemoveRequest = (categoryName) => {
  fetch(`http://192.168.1.27:8081/lobby/${gameId}/host/remove/category?category_name=${categoryName}`, {
    method: 'POST',
    headers: {
      'Authorization': jwtToken,
      'Username': username
    }
  })
};

const disconnect = () => {
  fetch(`http://192.168.1.27:8081/lobby/${gameId}/disconnect`, {
      method: 'POST',
      headers: {
          'Authorization': jwtToken,
          'Username': username
      }
  })
  .then(response => response.json)
  .then(data => {
    console.log(data);
    setTimeout(function() {
      window.location.href = "http://192.168.1.27:8081/home";
  }, 2000);
  })
  .catch(error => console.error(error));
};

startButton.addEventListener('click', startGame);

function startGame() {
  fetch(`http://192.168.1.27:8081/lobby/${gameId}/host/start`, {
    method: 'POST',
    headers: {
      'Authorization': jwtToken,
      'Username': username
    }
  })
  .then(response => {
    console.log(response);
    if(response.ok) {
      return response.text();
    } else {
      return response.text().then(errorMessage => {
        console.log(errorMessage);
        if (errorMessage.includes('players')) {
          alert('There are not enough Players to start the game!');
        } else if (errorMessage.includes('categories')) {
          alert('You can not have more than 15 Categories or less than 2!');
        } else if (errorMessage.includes('characters')) {
          alert('You need to select more Characters!');
        } else {
          setTimeout(function() {
            window.location.href = 'http://192.168.1.27:8081/game/' + gameId;
          }, 2000)
        }
    });
    }
  })
  
};

function checkIfGameHasStarted() {
  fetch(`http://192.168.1.27:8081/game/${gameId}/started`, {
        method: 'GET',
        headers: {
            'Authorization': jwtToken,
            'Username': username
        }
    })
    .then(response => response.json) 
    .then(data => {
        console.log(data);
        alert("Game has already started");
        window.location.href = "http://192.168.1.27:8081/home";
    })
};

const addEventListener = () => {
  disconnectButton.addEventListener('click', disconnect);
};
  
addEventListener();
  // Call the fetchData() function every 10 seconds
//setInterval(initializePage, 10000); 
