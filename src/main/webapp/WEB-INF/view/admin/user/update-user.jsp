<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Update User</title>

                <!-- Latest compiled and minified CSS -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <!-- Latest compiled JavaScript -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

            </head>

            <body>
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3>Update a User</h3>
                            <hr />
                            <form:form method="post" action="/admin/user/update" modelAttribute="updateUser">
                                <div class="mb-3" style="display: none;">
                                    <label for="exampleInputID1" class="form-label">Id:</label>
                                    <form:input type="text" class="form-control" id="exampleInputID1" path="id" />
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputEmail1" class="form-label">Email:</label>
                                    <form:input type="email" class="form-control" id="exampleInputEmail1"
                                        aria-describedby="emailHelp" path="email"/>
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputPhoneNumber1" class="form-label">Phone Number:</label>
                                    <form:input type="text" class="form-control" id="exampleInputPhoneNumber1"
                                        path="phone" />
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputFullName" class="form-label">Full Name:</label>
                                    <form:input type="text" class="form-control" id="exampleInputFullName" path="fullName" />
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputAddress" class="form-label">Address:</label>
                                    <form:input type="text" class="form-control" id="exampleInputAddress" path="address" />
                                </div>
                                <button type="submit" class="btn btn-warning">Update</button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </body>

            </html>