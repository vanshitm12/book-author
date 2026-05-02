<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Add Book</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body class="page-bg">
<div class="container py-5">
    <div class="card shadow-lg border-0">
        <div class="card-body p-4 p-md-5">
            <h2 class="mb-4">Add Book</h2>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>

            <form action="/books/add" method="post" class="row g-3">
                <div class="col-md-6">
                    <label class="form-label">Title</label>
                    <input type="text" name="title" class="form-control" value="${book.title}" required />
                </div>
                <div class="col-md-6">
                    <label class="form-label">ISBN</label>
                    <input type="text" name="isbn" class="form-control" value="${book.isbn}" required />
                </div>
                <div class="col-md-6">
                    <label class="form-label">Publication Year</label>
                    <input type="number" name="publicationYear" class="form-control" value="${book.publicationYear}" required />
                </div>
                <div class="col-md-6">
                    <label class="form-label">Select Existing Author (Optional)</label>
                    <select name="authorId" class="form-select">
                        <option value="">-- Create New Author --</option>
                        <c:forEach var="author" items="${authors}">
                            <option value="${author.id}">${author.name} (${author.email})</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-12">
                    <h5 class="mt-3">New Author Details (required if no existing author selected)</h5>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Name</label>
                    <input type="text" name="newAuthorName" class="form-control" />
                </div>
                <div class="col-md-4">
                    <label class="form-label">Email</label>
                    <input type="email" name="newAuthorEmail" class="form-control" />
                </div>
                <div class="col-md-4">
                    <label class="form-label">Age</label>
                    <input type="number" name="newAuthorAge" class="form-control" />
                </div>

                <div class="col-12 d-flex gap-2 mt-4">
                    <button type="submit" class="btn btn-primary">Save Book</button>
                    <a href="/books/list" class="btn btn-outline-secondary">Back to Books</a>
                    <a href="/authors/list" class="btn btn-outline-dark">Authors</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
