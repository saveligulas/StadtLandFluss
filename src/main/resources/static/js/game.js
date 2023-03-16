

function addRow(label) {
    // Get a reference to the table body
    const tableBody = document.querySelector('#my-table tbody');
    
    // Create a new table row
    const newRow = document.createElement('tr');
    
    // Create a table cell for the label
    const labelCell = document.createElement('td');
    labelCell.textContent = label;
    newRow.appendChild(labelCell);
    
    // Create a table cell for the input field
    const inputCell = document.createElement('td');
    const inputField = document.createElement('input');
    inputField.type = 'text';
    inputCell.appendChild(inputField);
    newRow.appendChild(inputCell);
    
    // Add the new row to the table body
    tableBody.appendChild(newRow);
}

fetch()