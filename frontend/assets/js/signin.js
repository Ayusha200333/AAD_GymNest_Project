// $(document).ready(function () {
//     $(".toggle-password").click(function () {
//         $(this).toggleClass("fa-eye fa-eye-slash");
//         var input = $(this).prev();
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
//                 // Store the token
//                 localStorage.setItem("jwt_token", response.data.token);
//
//                 // Add debugging to see what's in the response
//                 console.log("Authentication response:", response);
//                 console.log("User role:", response.data.role);
//
//                 // Reset the form
//                 $("#loginForm")[0].reset();
//
//                 // Redirect based on role
//                 const role = response.data.role.toUpperCase(); // Convert to uppercase
//
//                 console.log("User role after conversion:", role);
//
//                 if (role === "ADMIN") {
//                     console.log("Redirecting to admin dashboard");
//                     window.location.href = "AdminDashboard.html";
//                 } else if (role === "USER") {
//                     console.log("Redirecting to customer dashboard");
//                     window.location.href = "CustomerDashboard.html";
//                 } else {
//                     console.log("Role not recognized, redirecting to index");
//                     window.location.href = "index.html";
//                 }
//
//             },
//             error: function (xhr, status, error) {
//                 console.error("Login error details:", xhr.responseText);
//                 alert("Login failed. Please check your credentials.");
//             },
//         });
//     });
//
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












$("#showPassword").on("change", function() {
    const pwd = $("#password");
    if (this.checked) {
        pwd.attr("type", "text");
    } else {
        pwd.attr("type", "password");
    }
});

$("#signinForm").on("submit", function(e) {
    e.preventDefault();
    const username = $("#username").val().trim();
    const password = $("#password").val();

    if (!username || !password) {
        alert("Please fill in all fields.");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/auth/login",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({ username, password }),
        success: function(response) {
            const data = response.data || {};
            const token = data.accessToken;
            const role = data.role;
            const userName = data.username || data.userName || username;

            if (!token || !role) {
                alert("Login successful, but missing token or role.");
                return;
            }

            localStorage.setItem("accessToken", token);
            localStorage.setItem("username", userName);
            localStorage.setItem("role", role);

            alert("Login successful!");
            window.location.href = "admin-panel.html";
        },
        error: function(xhr) {
            let errorMsg = "Login failed. Please try again.";
            if (xhr.responseJSON && xhr.responseJSON.message) {
                errorMsg = xhr.responseJSON.message;
            }
            if (xhr.status === 401 || xhr.status === 403) {
                alert("Username or password incorrect. Please try again.");
            } else if (xhr.status === 404) {
                alert("User not found. Redirecting to signup...");
                setTimeout(() => window.location.href = "signup.html", 1500);
            } else {
                alert(errorMsg);
            }
        }
    });
});