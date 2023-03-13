import { getCookie } from './cookies.js';
console.log("loaded");
const disconnectButton = document.querySelector('#disconnect');
const jwtToken = getCookie('Authorization');

function fetchData() {
    fetch('http://192.168.1.27:8081/game/lobby/auth/users', {
        method: 'GET',
        headers: {
            'Authorization': jwtToken,
            'Content-Type': 'application/json'
        }
    })
      .then(response => response.json())
      .then(data => {
        console.log(data);
        updateTable(data);
      })
      .catch(error => console.error(error));
}
  
function updateTable(data) {
    // Get the table body element
    const tableBody = document.querySelector('#table-body');
  
    // Clear the existing table rows
    tableBody.innerHTML = '';
     
    // Loop through the array of objects and create table rows and cells
    data.forEach(str => {
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.textContent = str;
        row.appendChild(cell);
        tableBody.appendChild(row);
      });
}

const disconnect = () => {

  let tokenWithoutPrefix = jwtToken;
  tokenWithoutPrefix = tokenWithoutPrefix.replace("Bearer ", "");

  fetch('http://192.168.1.27:8081/game/lobby/disconnect', {
      method: 'POST',
      headers: {
          'Authorization': jwtToken,
          'Content-Type': 'application/json'
      }
  })
  .then(response => response.json)
  .then(data => {
    console.log(data);
    fetchData();
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
