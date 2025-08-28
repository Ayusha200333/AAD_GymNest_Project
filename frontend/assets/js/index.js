// GymNest interactions
(function () {
    'use strict';

    // Set current year in footer
    document.addEventListener('DOMContentLoaded', () => {
        const yearEl = document.querySelector('.year');
        if (yearEl) yearEl.textContent = new Date().getFullYear();
    });

    // Activate nav link on scroll
    const sections = ['home', 'about', 'classes', 'coaches', 'contact']
        .map(id => document.getElementById(id))
        .filter(Boolean);
    const navLinks = Array.from(document.querySelectorAll('.navbar .nav-link'));

    const activateLink = (id) => {
        navLinks.forEach(a => a.classList.toggle('active', a.getAttribute('href') === `#${id}`));
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) activateLink(entry.target.id);
        });
    }, { rootMargin: '-40% 0px -55% 0px', threshold: 0.1 });

    sections.forEach(sec => observer.observe(sec));

    // Bootstrap validation
    (() => {
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

})();