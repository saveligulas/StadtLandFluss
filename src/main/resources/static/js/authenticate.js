const emailHolder = document.querySelector('#email');
const passwordHolder = document.querySelector('#password');

const submitButton = document.querySelector('#cta');
const messageField = document.getElementById("messageLabel");

const prepareParams = params => ({
    email: params.email,
    password: params.password
});

const onSubmit = () => {
    const email = emailHolder.value;
    const password = passwordHolder.value;

    const loginData = prepareParams({email, password});
    
    fetch('http://192.168.1.27:8081/auth/post/authenticate', {
    method: 'POST',
    body: JSON.stringify(loginData),
    headers: { 'Content-Type': 'application/json' },
    })
    .then(response => response.json())
    .then(data => {
        const token = "Bearer "+data.token;
        document.cookie = `${'Authorization'}=${token}; path=/`;
        submitButton.disabled = true;
        messageField.textContent = "Login successful";
        setTimeout(function() {
            window.location.href = "http://192.168.1.27:8081/game/lobby";
        }, 3500);
    })
    .catch(error => {
        messageField.textContent = "Username or Password incorrect.";
    });
    };

const addSubmitEventListener = () => {
    submitButton.addEventListener('click', onSubmit);
};

addSubmitEventListener();