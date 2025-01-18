@import gg.jte.Content
@param Content content

<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>QuestGame</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</head>

<body class="d-flex flex-column min-vh-100">
<main class="flex-grow-1">
    ${content}
</main>
<footer class="footer border-top py-3 mt-50 bg-dark text-white">
    <div class="container-xl">
        <div class="text-center">
            created by
            <a href="https://github.com/Denis-Shakhurov" target="_blank">Denis Shakhurov</a>
        </div>
    </div>
</footer>
</body>
</html>