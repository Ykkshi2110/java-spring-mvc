<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Details</title>

     <!-- Latest compiled and minified CSS -->
     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
     <!-- Latest compiled JavaScript -->
     <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
    <div class="container mt-5">
        <div class="row">
            <div class="col-8 mx-auto">
                <div>
                    <h3>User Information With ID: ${user_detail.id}</h3>
                    <hr/>
                </div>
                <div class="card" style="width: 80%;">
                    <ul class="list-group list-group-flush">
                      <li class="list-group-item">ID: ${user_detail.id}</li>
                      <li class="list-group-item">Email: ${user_detail.email}</li>
                      <li class="list-group-item">Full Name: ${user_detail.fullName}</li>
                      <li class="list-group-item">Address: ${user_detail.address}</li>
                    </ul>
                  </div>
                  <a href="/admin/user" class="btn btn-outline-success btn-lg mt-3">Back</a>
            </div>
        </div>
    </div>
</body>
</html>