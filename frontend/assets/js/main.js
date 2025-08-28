// Highlight active nav link
(function(){
    const links = document.querySelectorAll('.navbar .nav-link');
    const current = location.pathname.split('/').pop() || 'index.html';
    links.forEach(a=>{
        const href = a.getAttribute('href');
        if(href === current || (current === '' && href==='index.html')) a.classList.add('active');
    });
})();

// Generic image fallback via data-fallback
document.addEventListener('error', function(e){
    const el = e.target;
    if(el.tagName === 'IMG' && el.dataset.fallback && !el.dataset.tried){
        el.dataset.tried = '1';
        el.src = el.dataset.fallback;
    }
}, true);

// Bootstrap validation helper
(() => {
    'use strict';
    const forms = document.querySelectorAll('.needs-validation');
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
})();

// Year in footer
document.addEventListener('DOMContentLoaded', ()=>{
    const y = document.querySelectorAll('.year');
    y.forEach(el=> el.textContent = new Date().getFullYear());
});



'use strict';

/* Add event on element(s) */
const addEventOnElem = function (elem, type, callback) {
    if (!elem) return;
    if (NodeList.prototype.isPrototypeOf(elem) || Array.isArray(elem)) {
        for (let i = 0; i < elem.length; i++) elem[i].addEventListener(type, callback);
    } else {
        elem.addEventListener(type, callback);
    }
};

/* Navbar toggle (old UI behavior) */
const navbar = document.querySelector("[data-navbar]");
const navTogglers = document.querySelectorAll("[data-nav-toggler]");
const navLinks = document.querySelectorAll("[data-nav-link]");
const toggleNavbar = () => navbar.classList.toggle("active");
addEventOnElem(navTogglers, "click", toggleNavbar);
const closeNavbar = () => navbar.classList.remove("active");
addEventOnElem(navLinks, "click", closeNavbar);

/* Header + back-top on scroll */
const header = document.querySelector("[data-header]");
const backTopBtn = document.querySelector("[data-back-top-btn]");
window.addEventListener("scroll", () => {
    if (window.scrollY >= 100) {
        header.classList.add("active");
        backTopBtn.classList.add("active");
    } else {
        header.classList.remove("active");
        backTopBtn.classList.remove("active");
    }
});

/* Highlight current nav link based on pathname */
(function(){
    const links = document.querySelectorAll('.navbar .navbar-link');
    const current = location.pathname.split('/').pop() || 'index.html';
    links.forEach(a=>{
        const href = a.getAttribute('href');
        if (href === current || (current === '' && href === 'index.html')) {
            a.classList.add('active');
        }
    });
})();

/* Generic image fallback via data-fallback */
document.addEventListener('error', function(e){
    const el = e.target;
    if(el.tagName === 'IMG' && el.dataset.fallback && !el.dataset.tried){
        el.dataset.tried = '1';
        el.src = el.dataset.fallback;
    }
}, true);

/* Year in footer */
document.addEventListener('DOMContentLoaded', ()=>{
    document.querySelectorAll('.year').forEach(el => el.textContent = new Date().getFullYear());
});