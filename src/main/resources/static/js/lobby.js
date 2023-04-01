import { getCookie } from './cookies.js';
console.log("loaded");
const disconnectButton = document.querySelector('#disconnect');
const jwtToken = getCookie('Authorization');
const username = getCookie('Username');

window.addEventListener('DOMContentLoaded', initializePage);

function initializePage() {
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

    // Add event listener to the "Add" button
    const addCategoryButton = document.getElementById('addCategoryButton');
    if (addCategoryButton) {
      addCategoryButton.addEventListener('click', () => {
        const newCategoryName = document.getElementById('newCategoryName').value;
        const address = `${window.location.href}/host/settings`;
        fetch(address, {
          method: 'POST',
          headers: {
            'Authorization': jwtToken,
            'Username': username,
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ name: newCategoryName })
        })
        .then(() => {
          // Reload the page after adding the category
          location.reload();
        })
        .catch(error => {
          console.error('Error adding category:', error);
        });
      });
    }

    // Add event listener to the "Remove" buttons
    const removeCategoryButtons = document.querySelectorAll('.remove-category-button');
    removeCategoryButtons.forEach(button => {
      button.addEventListener('click', () => {
        const categoryId = button.dataset.categoryId;
        fetch(`/categories/remove/${categoryId}`, {
          method: 'POST'
        })
        .then(() => {
          // Reload the page after removing the category
          location.reload();
        })
        .catch(error => {
          console.error('Error removing category:', error);
        });
      });
    });
  })
  .catch(error => {
    console.error('Error fetching showAddCategory flag:', error);
  });
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
      actionsCell.appendChild(removeButton);
      row.appendChild(actionsCell);
    }

    categoriesTableBody.appendChild(row);
  });
}



const disconnect = () => {
  fetch('http://192.168.1.27:8081/game/lobby/disconnect', {
      method: 'POST',
      headers: {
          'Authorization': jwtToken,
          'HostUsername': username,
          'GameID': gameId
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

const addEventListener = () => {
  disconnectButton.addEventListener('click', disconnect);
};
  
addEventListener();
  // Call the fetchData() function every 10 seconds
setInterval(initializePage, 10000); 
