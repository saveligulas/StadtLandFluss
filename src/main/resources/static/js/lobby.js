import { getCookie } from './cookies.js';
console.log("loaded");
const disconnectButton = document.querySelector('#disconnect');
const jwtToken = getCookie('Authorization');
const username = getCookie('Username');
const gameId = getCookie('GameID');

window.addEventListener('DOMContentLoaded', initializePage);

function initializePage() {
    // Fetch the flag that determines if the category input field and remove buttons should be displayed
  const checkHostAddress = `${window.location.href}/host/check`;
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

    // Create a new container element for the input field and the "Add" button
    const addCategoryContainer = document.createElement('div');

    // Check if the category input field and remove buttons should be displayed
    if (showAddCategory) {
      // Create the input field and the "Add" button
      const newCategoryNameInput = document.createElement('input');
      newCategoryNameInput.type = 'text';
      newCategoryNameInput.id = 'newCategoryName';
      newCategoryNameInput.placeholder = 'Enter category name';
      const addCategoryButton = document.createElement('button');
      addCategoryButton.type = 'button';
      addCategoryButton.id = 'addCategoryButton';
      addCategoryButton.textContent = 'Add';

      // Add the input field and the "Add" button to the container
      addCategoryContainer.appendChild(newCategoryNameInput);
      addCategoryContainer.appendChild(addCategoryButton);

      // Insert the container before the table
      const categoriesTable = document.getElementById('categoriesTable');
      categoriesTable.parentNode.insertBefore(addCategoryContainer, categoriesTable);
    }

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

function updateTables() {
  fetch('/api/get-players')
    .then(function(response) {
      return response.json();
    })
    .then(function(data) {
      var table = document.querySelector('#playersTable');
      table.innerHTML = '';
      var headerRow = document.createElement('tr');
      var nameHeader = document.createElement('th');
      nameHeader.textContent = 'Name';
      headerRow.appendChild(nameHeader);
      var scoreHeader = document.createElement('th');
      scoreHeader.textContent = 'Score';
      headerRow.appendChild(scoreHeader);
      table.appendChild(headerRow);
      data.forEach(function(player) {
        var row = document.createElement('tr');
        var nameCell = document.createElement('td');
        nameCell.textContent = player;
        row.appendChild(nameCell);
        var scoreCell = document.createElement('td');
        scoreCell.textContent = '0';
        row.appendChild(scoreCell);
        table.appendChild(row);
      });
    });
  fetch('/api/get-categories')
    .then(function(response) {
      return response.json();
    })
    .then(function(data) {
      var table = document.querySelector('#categoriesTable');
      table.innerHTML = '';
      var headerRow = document.createElement('tr');
      var nameHeader = document.createElement('th');
      nameHeader.textContent = 'Name';
      headerRow.appendChild(nameHeader);
      var weightHeader = document.createElement('th');
      weightHeader.textContent = 'Weight';
      headerRow.appendChild(weightHeader);
      var removeHeader = document.createElement('th');
      removeHeader.textContent = 'Remove';
      removeHeader.style.display = 'none';
      headerRow.appendChild(removeHeader);
      table.appendChild(headerRow);
      data.forEach(function(category) {
        var row = document.createElement('tr');
        var nameCell = document.createElement('td');
        nameCell.textContent = category;
        row.appendChild(nameCell);
        var weightCell = document.createElement('td');
        weightCell.textContent = '1';
        row.appendChild(weightCell);
        var removeCell = document.createElement('td');
        var removeButton = document.createElement('button');
        removeButton.setAttribute('type', 'button');
        removeButton.classList.add('remove-category-button');
        removeButton.setAttribute('data-category-id', category);
        removeButton.textContent = 'Remove';
        removeButton.style.display = 'none';
        removeCell.appendChild(removeButton);
        row.appendChild(removeCell);
        table.appendChild(row);
      });
    });
}

const disconnect = () => {

  let tokenWithoutPrefix = jwtToken;
  tokenWithoutPrefix = tokenWithoutPrefix.replace("Bearer ", "");

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
    fetchData();
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
  
  // Call the fetchData() function immediately when the page loads
fetchData();
  
  // Call the fetchData() function every 10 seconds
setInterval(fetchData, 10000); 
