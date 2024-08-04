<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Create Product</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#productFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#productPreview").attr("src", imgURL);
                            $("#productPreview").css({ "display": "block" });
                        });
                    });
                </script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manage Product</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Products</li>
                                </ol>
                                <div>
                                    <div class="mt-5">
                                        <div class="row">
                                            <div class="col-md-6 mx-auto">
                                                <h3>Create a Product</h3>
                                                <hr />
                                                <form:form method="post" action="/admin/product/create" modelAttribute="newProduct"
                                                     class="row" enctype="multipart/form-data">
                                                    <div class="col-md-6 col-12 mb-3">
                                                        <c:set var="errorName">
                                                            <form:errors path="name"  cssClass="invalid-feedback"/>
                                                        </c:set>
                                                        <label for="exampleInputName1" class="form-label">Name:</label>
                                                        <form:input type="text" class="form-control ${not empty errorName ? 'is-invalid' : ''}"
                                                            id="exampleInputName1" path="name" />
                                                            ${errorName}
                                                    </div>
                                                    <div class="col-md-6 col-12 mb-3">
                                                        <c:set var="errorPrice">
                                                            <form:errors path="price"  cssClass="invalid-feedback"/>
                                                        </c:set>
                                                        <label for="exampleInputPrice1" class="form-label">Price:</label>
                                                        <form:input type="number" class="form-control ${not empty errorPrice ? 'is-invalid' : ''}"
                                                            id="exampleInputPrice1" path="price" />
                                                            ${errorPrice}
                                                    </div>
                                                    <div class="col-12 mb-3">
                                                        <c:set var="errorDetailDesc">
                                                            <form:errors path="detailDesc"  cssClass="invalid-feedback"/>
                                                        </c:set>
                                                        <label for="exampleInputDetailDesc1" class="form-label">Detail Description:</label>
                                                        <form:textarea type="text" class="form-control ${not empty errorDetailDesc ? 'is-invalid' : ''}"
                                                            id="exampleInputDetailDesc1" path="detailDesc" ></form:textarea>   
                                                            ${errorDetailDesc}
                                                    </div>
                                                    <div class="col-md-6 col-12 mb-3">
                                                        <c:set var="errorShortDesc">
                                                            <form:errors path="shortDesc"  cssClass="invalid-feedback"/>
                                                        </c:set>
                                                        <label for="exampleInputShortDesc" class="form-label">Short Description:</label>
                                                        <form:input type="text" class="form-control ${not empty errorShortDesc ? 'is-invalid' : ''}"
                                                            id="exampleInputShortDesc" path="shortDesc" />
                                                            ${errorShortDesc}
                                                    </div>
                                                    <div class="col-md-6 col-12 mb-3">
                                                        <c:set var="errorQuantity">
                                                            <form:errors path="quantity"  cssClass="invalid-feedback"/>
                                                        </c:set>
                                                        <label for="exampleInputQuantity"
                                                            class="form-label">Quantity:</label>
                                                        <form:input type="number" class="form-control ${not empty errorQuantity ? 'is-invalid' : ''}"
                                                            id="exampleInputQuantity" path="quantity" />
                                                            ${errorQuantity}
                                                    </div>
                                                    <div class="col-md-6 col-12 mb-3">
                                                        <label for="exampleInputFactory" class="form-label">Factory:</label>
                                                        <form:select class="form-select" aria-label="Default select example"
                                                            path="factory">
                                                            <form:option value="APPLE">Apple (Macbook)</form:option>
                                                            <form:option value="LENOVO">Lenovo</form:option>
                                                            <form:option value="ACER">Acer</form:option>
                                                            <form:option value="ASUS">Asus</form:option>
                                                            <form:option value="MSI">MSI</form:option>
                                                            <form:option value="DELL">Dell</form:option>
                                                            <form:option value="LG">LG</form:option>
                                                        </form:select>
                                                    </div>
                                                    <div class="col-md-6 col-12 mb-3">
                                                        <label for="exampleInputTarget" class="form-label">Target:</label>
                                                        <form:select class="form-select" aria-label="Default select example"
                                                            path="target">
                                                            <form:option value="GAMING">Gaming</form:option>
                                                            <form:option value="SINHVIEN-VANPHONG">Sinh viên - Văn phòng</form:option>
                                                            <form:option value="DOANH-NHAN">Doanh nhân</form:option>
                                                            <form:option value="MONG-NHE">Mỏng - Nhẹ</form:option>
                                                            <form:option value="THIET-KE-DO-HOA">Thiết kế dồ họa</form:option>
                                                        </form:select>
                                                    </div>
                                                    <div class="col-md-6 col-12 mb-3">
                                                        <label for="formFile" class="form-label">Image:</label>
                                                        <input class="form-control" type="file" id="productFile"
                                                            name="productFile" accept=".png, .jpg, .jpeg" />
                                                    </div>
                                                    <div class="col-12 mb-3">
                                                        <img style="max-height: 250px; display: none;"
                                                            alt="product preview" id="productPreview">
                                                    </div>
                                                    <div class="col-12 mb-5">
                                                        <button type="submit" class="btn btn-primary">Create</button>
                                                    </div>
                                                </form:form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
            </body>

            </html>