const firstNameHolder = document.querySelector('#firstName');
const lastNameHolder = document.querySelector('#lastName');
const emailHolder = document.querySelector('#email');
const passwordHolder = document.querySelector('#password');

const submitButton = document.querySelector('#cta');

const messageField = document.getElementById('messageLabel');
const emailField = document.getElementById('email');

const prepareParams = params => ({
    firstName: params.firstName,
    lastName: params.lastName,
    email: params.email,
    password: params.password
});

const onSubmit = () => {
    const firstName = firstNameHolder.value;
    const lastName = lastNameHolder.value;
    const email = emailHolder.value;
    const password = passwordHolder.value;

    messageField.textContent = "";

    if(lastName.length<1 || firstName.length<1){
        messageField.textContent = "Your name cant be empty.";
        return;
    }
    if(password.length<8 || !/\d/.test(password) || !/[A-Z]/.test(password)) {
        messageField.textContent = "Your password must be atleast 8 characters long, contain atleast 1 digit and 1 capitalized letter.";
        return;
    }
    
    var emailValid;

    fetch('http://192.168.1.27:8081/validator/email', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email
        })
    })
    .then(response => {
        if(!response.ok) {  
            throw new Error(`API error: Email is not valid`);
        } else {
            console.log(response);
            emailValid = true;
            messageField.textContent = "All Fields entered correctly";
        }
    })
    .catch(error => {
        console.error(error);
        messageField.textContent = "Email is not valid";
        emailValid = false;
        console.log("catched error")
    })

    if(emailValid == false) {
        console.log("returning");
        return;
    }

    console.log("pre-prepareParams");

    const data = prepareParams({firstName, lastName, email, password});

    fetch('http://192.168.1.27:8081/auth/post/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if(!response.ok) {
            return response.json().then(data => {
                throw new Error(`API error: ${data.message}`);
            });
        } else {
            console.log(response);
            messageField.textContent = "User registered succesfully";
            window.location.href = "http://192.168.1.27:8081/";
        }
        
    })
    .catch(error => {
        console.error(error);
        messageField.textContent = "Email is already taken!";
        emailField.value = '';
    });

    console.log("post-prepareParams")
};

const addSubmitEventListener = () => {
    submitButton.addEventListener('click', onSubmit);
};

addSubmitEventListener();