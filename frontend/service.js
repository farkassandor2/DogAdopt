const url = "http://localhost:8080/api/registration";

const email = document.getElementById('email');
const password = document.getElementById('password');
const country = document.getElementById('country');

document.getElementById('registration-form').addEventListener('submit', function (event) {
    event.preventDefault();

    if (!email.value || !password.value) {
        alert('Please fill both fields.')
    }
    const userData = {
        'email': email.value,
        'password': password.value,
        'country': country.value.toUpperCase()
    };
    registerUser(userData).then(() => console.log('reg complete'));
    email.value = '';
    password.value = '';
    country.value = '';
});

const registerUser = async (userData) => {
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
           const result = await response.json();
           console.log('Registration successful:', result);
           alert('Registration successful!');
        } else {
            console.error('Registration failed:', errorMessage);
            alert(`Registration failed: ${errorMessage || 'Please try again.'}`);
        }
    } catch (error) {
        console.error('Error during registration:', error);
        alert('Registration failed. Please try again.');
    }

}