// $(document).ready(function () {
//     // Password visibility toggle
//     $(".toggle-password").click(function () {
//         $(this).toggleClass("fa-eye fa-eye-slash");
//         var input = $($(this).attr("toggle"));
//         if (input.attr("type") === "password") {
//             input.attr("type", "text");
//         } else {
//             input.attr("type", "password");
//         }
//     });
//
//     $("#loginForm").on("submit", function (e) {
//         e.preventDefault();
//
//         $.ajax({
//             url: "http://localhost:8080/api/v1/auth/authenticate",
//             type: "POST",
//             contentType: "application/json",
//             data: JSON.stringify({
//                 email: $("#email").val(),
//                 password: $("#password").val(),
//             }),
//             success: function (response) {
//                 localStorage.setItem("jwt_token", response.token);
//                 $("#loginForm")[0].reset();
//                 window.location.href = "index.html";
//             },
//             error: function (xhr, status, error) {
//                 alert("Login failed. Please check your credentials.");
//                 console.error("Error:", error);
//             },
//         });
//     });
//
//     // -------------------------------- Signup --------------------------------
//     $("#registerForm").on("submit", function (e) {
//         e.preventDefault();
//
//         const password = $("#password").val();
//         const confirmPassword = $("#confirmPassword").val();
//
//         if (password !== confirmPassword) {
//             alert("Passwords do not match!");
//             return;
//         }
//
//         $.ajax({
//             url: "http://localhost:8080/api/v1/user/register",
//             type: "POST",
//             contentType: "application/json",
//             data: JSON.stringify({
//                 email: $("#email").val(),
//                 password: password,
//                 name: $("#name").val(),
//                 role: $("#role").val(),
//             }),
//             success: function (response) {
//                 alert("Registration successful! Please login.");
//                 $("#registerForm")[0].reset();
//                 window.location.href = "../register.html";
//             },
//             error: function (xhr, status, error) {
//                 alert("Registration failed. Please try again.");
//                 console.error("Error:", error);
//                 if (xhr.responseJSON && xhr.responseJSON.message) {
//                     alert(xhr.responseJSON.message);
//                 }
//             },
//         });
//     });
// });










$(function () {
    // Toggle show/hide for both password fields
    $("#showPassword").on("change", function () {
        const type = this.checked ? "text" : "password";
        $("#password, #confirmPassword").attr("type", type);
    });

    $("#signupForm").on("submit", function (e) {
        e.preventDefault();

        const username = $("#username").val().trim();
        const email = $("#email").val().trim();
        const password = $("#password").val();
        const confirmPassword = $("#confirmPassword").val();
        const role = $("#role").val();

        // Required fields
        if (!username || !email || !password || !confirmPassword || !role) {
            alert("Please fill in all fields.");
            return;
        }

        // Email format check
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email)) {
            alert("Please enter a valid email address.");
            return;
        }

        // Confirm password match
        if (password !== confirmPassword) {
            alert("Passwords do not match.");
            return;
        }

        $.ajax({
            url: "http://localhost:8080/auth/register",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ username, email, password, confirmPassword, role }),
            success: function (response) {
                alert(response.message || "Registered successfully!");
                window.location.href = "signin.html";
            },
            error: function (xhr) {
                let errorMsg = "Registration failed.";
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    errorMsg = xhr.responseJSON.message;
                } else if (xhr.responseText) {
                    try {
                        const parsed = JSON.parse(xhr.responseText);
                        if (parsed.message) errorMsg = parsed.message;
                    } catch (_) {}
                }
                alert(errorMsg);
            }
        });
    });
});