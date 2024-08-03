<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="id" dir="ltr">

            <head>
                <meta charset="utf-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />

                <!-- Title -->
                <title>Sorry, This Page Can&#39;t Be Accessed</title>
                <link rel="stylesheet"
                    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" />
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
                    integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
                    crossorigin="anonymous" />
            </head>

            <body class="bg-white text-black py-5">
                <div class="container py-5">
                    <div class="row">
                        <div class="col-md-2 text-center">
                            <p><i class="fa fa-exclamation-triangle fa-5x"></i><br />Status Code: 403</p>
                        </div>
                        <div class="col-md-10">
                            <h3>OPPSSS!!!! Sorry...</h3>
                            <p id="errorContentText"></p>
                            <p> You'll be automatically redirected <span id="countDown">15 </span> Seconds</p>

                            <a class="btn btn-primary" href="/">Go Back</a>
                        </div>
                    </div>
                </div>


            </body>

            <script>

                var page = "home"
                const countDown = document.getElementById("countDown");
                var simpleText = "Sorry, your access is refused due to security reasons of our server and also our sensitive data. Please go back to the previous page to continue browsing.";
                const urlParams = new URLSearchParams(window.location.search);
                const errorMessage = urlParams.get("message");
                const redirect = urlParams.get("redirect");
                window.onload = function () {
                    document.getElementById("errorContentText").innerHTML = errorMessage || simpleText;
                }

                var timeLeft = 14;
                var downloadTimer = setInterval(function () {
                    timeLeft--;
                    countDown.textContent = timeLeft;

                    if (timeLeft <= 0)
                        clearInterval(downloadTimer);
                    window.location.replace(buttonGoTo.href)
                }, 1000);

            </script>

            </html>