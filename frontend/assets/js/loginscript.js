// GymNest Login/SignUp interactions
(function () {
    'use strict';

    const container = document.getElementById('authContainer');
    const toRegisterBtns = document.querySelectorAll('.register-btn');
    const toLoginBtns = document.querySelectorAll('.login-btn');
    const phoneInput = document.getElementById('signupPhone');
    const phoneError = document.getElementById('phone-error');
    const phoneFull = document.getElementById('signupPhoneFull');
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');

    // Toggle view
    toRegisterBtns.forEach(btn => btn.addEventListener('click', () => {
        container.classList.add('show-register');
        initPhone(); // init when switching to signup (ensures visible)
    }));
    toLoginBtns.forEach(btn => btn.addEventListener('click', () => {
        container.classList.remove('show-register');
    }));

    // Password show/hide
    document.querySelectorAll('.toggle-pass').forEach(btn => {
        btn.addEventListener('click', () => {
            const target = document.querySelector(btn.dataset.target);
            if (!target) return;
            const isPwd = target.getAttribute('type') === 'password';
            target.setAttribute('type', isPwd ? 'text' : 'password');
            const icon = btn.querySelector('i');
            if (icon) icon.className = isPwd ? 'bx bx-show' : 'bx bx-hide';
        });
    });

    // intl-tel-input init (lazy)
    let iti = null;
    function initPhone() {
        if (!phoneInput || iti) return;
        iti = window.intlTelInput(phoneInput, {
            initialCountry: 'auto',
            geoIpLookup: function (callback) {
                // fallback to LK if geo fails
                try {
                    fetch('https://ipapi.co/json').then(res => res.json()).then(data => callback(data.country_code || 'LK')).catch(() => callback('LK'));
                } catch (e) { callback('LK'); }
            },
            preferredCountries: ['lk', 'in', 'gb', 'us'],
            separateDialCode: true,
            utilsScript: 'https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/utils.js'
        });

        phoneInput.addEventListener('blur', validatePhone);
        phoneInput.addEventListener('input', () => {
            phoneError.style.display = 'none';
        });
    }

    function validatePhone() {
        if (!iti) return true;
        const valid = iti.isValidNumber();
        if (!valid) {
            phoneError.style.display = 'block';
            return false;
        }
        phoneError.style.display = 'none';
        phoneFull.value = iti.getNumber(); // E.164
        return true;
    }

    // Login submit (demo only)
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const email = document.getElementById('loginEmail')?.value.trim();
            const pass = document.getElementById('loginPassword')?.value;
            if (!email || !pass) return;
            // TODO: Replace with your login API call
            console.log('Login with', { email, pass, remember: document.getElementById('rememberMe')?.checked });
            loginForm.querySelector('#loginBtn').disabled = true;
            setTimeout(() => {
                loginForm.querySelector('#loginBtn').disabled = false;
                alert('Logged in (demo). Hook this to your backend.');
            }, 800);
        });
    }

    // Register submit (demo only)
    if (registerForm) {
        registerForm.addEventListener('submit', (e) => {
            e.preventDefault();
            initPhone();
            if (!validatePhone()) return;

            const payload = {
                name: document.getElementById('signupName')?.value.trim(),
                email: document.getElementById('signupEmail')?.value.trim(),
                phone: phoneFull.value,
                password: document.getElementById('signupPassword')?.value
            };
            if (!payload.name || !payload.email || !payload.password) return;

            // TODO: Replace with your signup API call
            console.log('Sign up with', payload);
            registerForm.querySelector('#signupBtn').disabled = true;
            setTimeout(() => {
                registerForm.querySelector('#signupBtn').disabled = false;
                alert('Account created (demo). Hook this to your backend.');
                // Optionally switch to login
                document.querySelector('.login-btn')?.click();
            }, 900);
        });
    }

    // If user opens page with #signup hash, show register
    if (window.location.hash.toLowerCase() === '#signup') {
        container.classList.add('show-register');
        initPhone();
    }
})();