import { getCookie } from './cookies.js';
console.log("loaded");
const disconnectButton = document.querySelector('#disconnect');
const jwtToken = getCookie('Authorization');
const username = getCookie('Username');
const gameId = getCookie('GameID');

window.addEventListener('DOMContentLoaded', initializePage);

function initializePage() {
  fetch('/api/show-add-category')
    .then(function(response) {
      return response.json();
    })
    .then(function(data) {
      if (data === true) {
        document.querySelector('#categoriesTable th:last-child').style.display = 'table-cell';
        document.querySelectorAll('.remove-category-button').forEach(function(button) {
          button.style.display = 'inline-block';
        });
        document.querySelector('#categoriesTable tr:last-child').style.display = 'table-row';
        document.querySelector('#addCategoryButton').addEventListener('click', function() {
          var name = document.querySelector('#newCategoryName').value;
          var weight = document.querySelector('#newCategoryWeight').value;
          if (name && weight) {
            // TODO: Implement add category functionality
          }
        });
        document.querySelectorAll('.remove-category-button').forEach(function(button) {
          button.addEventListener('click', function() {
            var categoryId = button.getAttribute('data-category-id');
            // TODO: Implement remove category functionality
          });
        });
      }
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
