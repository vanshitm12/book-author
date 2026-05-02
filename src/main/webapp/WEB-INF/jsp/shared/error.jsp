<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="/css/style.css" rel="stylesheet" />
</head>
<body class="page-bg">
<div class="container py-5">
    <div class="card border-danger shadow-lg">
        <div class="card-body p-4 p-md-5">
            <h2 class="text-danger mb-3">Something went wrong</h2>
            <p class="mb-4">${errorMessage}</p>
            <div class="d-flex gap-2">
                <a href="/books/list" class="btn btn-primary">Go to Books</a>
                <a href="/authors/list" class="btn btn-outline-dark">Go to Authors</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
