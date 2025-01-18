@import dto.BasePage
@param BasePage page

@template.layout.page(
content = @`
    <main class="flax-grow-1">
        @if(page.getFlash() != null)
            <div class="rounded-0 m-0 alert alert-dismissible fade show alert-success" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
        <body>
        <nav class="navbar navbar-dark navbar-expand-md bg-dark py-3">
            <div class="container"><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-6"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse flex-grow-0 order-md-first" id="navcol-6">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item"><a class="nav-link active" href="${page.getStartPath()}">Главная</a></li>
                        <li class="nav-item"><a class="nav-link" href="">Статистика игр</a></li>
                    </ul>
                </div>
                @if(page.getUserInfo() == null)
                    <div class="d-none d-md-block">
                        <a class="btn btn-light me-2" role="button" href="${page.getRegistrationPath()}">Регистрация</a>
                        <a class="btn btn-primary" role="button" href="">Войти</a>
                    </div>
                @else
                    <div class="d-none d-md-block">
                        <a class="btn btn-primary" role="button" href="${page.getUserPath(page.getUserInfo().getId())}">Мой профиль</a>
                        <a class="btn btn-primary" role="button" href="">Выйти</a>
                    </div>
                @endif
            </div>
        </nav>
        <section>
            <div class="container py-4 py-xl-5">
                <div class="row mb-5">
                    <div class="col-md-8 col-xl-6 mx-auto">
                        <h1 class="col-md-8 col-xl-6 text-center mx-auto">QuestGame</h1>
                        <p></p>
                    </div>
                </div>
                <div class="row gy-4 row-cols-1 row-cols-md-2 row-cols-xl-3">
                    <div class="col">
                        <div class="card"><img class="card-img-top w-100 d-block fit-cover" style="height: 200px;" src="https://cdn.bootstrapstudio.io/placeholders/1400x800.png">
                            <div class="card-body p-4">
                                <h4 class="card-title">Игра "Калькулятор"</h4>
                                <p class="card-text">В данной игре для двух чисел нужно произвести математическую операцию "+", "-", "*" или "/" и дать верный ответ.</p>
                                <div class="d-flex">
                                    <div></div>
                                </div>
<%--                                @if(page.getUser() != null)--%>
<%--                                    <form action="${NamedRoutes.startPath()}" method="post" name="CalcGame" class="rss-form text-body">--%>
<%--                                        <button class="btn btn-primary" type="submit" name="game" value="CalcGame">Играть</button>--%>
<%--                                    </form>--%>
<%--                                @else--%>
<%--                                    <p>чтобы играть нужна авторизация</p>--%>
<%--                                @endif--%>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card"><img class="card-img-top w-100 d-block fit-cover" style="height: 200px;" src="https://cdn.bootstrapstudio.io/placeholders/1400x800.png">
                            <div class="card-body p-4">
                                <h4 class="card-title">Игра "Чётное/не чётное"</h4>
                                <p class="card-text">В данной игре для числа нужно определить является ли оно чётным или не чйтным, ответив "да" или "нет"</p>
                                <div class="d-flex">
                                    <div></div>
                                </div>
<%--                                @if(page.getUser() != null)--%>
<%--                                    <form action="${NamedRoutes.startPath()}" method="post" name="EvenGame" class="rss-form text-body">--%>
<%--                                        <button class="btn btn-primary" type="submit" name="game" value="EvenGame">Играть</button>--%>
<%--                                    </form>--%>
<%--                                @else--%>
<%--                                    <p>чтобы играть нужна авторизация</p>--%>
<%--                                @endif--%>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card"><img class="card-img-top w-100 d-block fit-cover" style="height: 200px;" src="https://cdn.bootstrapstudio.io/placeholders/1400x800.png">
                            <div class="card-body p-4">
                                <h4 class="card-title">Игра "Прогрессия"</h4>
                                <p class="card-text">В данной игре предоставляется прогрессия с пропущенным числом нужно ответить какое число пропущено.</p>
                                <div class="d-flex">
                                    <div></div>
                                </div>
<%--                                @if(page.getUser() != null)--%>
<%--                                    <form action="${NamedRoutes.startPath()}" method="post" name="ProgressionGame" class="rss-form text-body">--%>
<%--                                        <button class="btn btn-primary" type="submit" name="game" value="ProgressionGame">Играть</button>--%>
<%--                                    </form>--%>
<%--                                @else--%>
<%--                                    <p>чтобы играть нужна авторизация</p>--%>
<%--                                @endif--%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        </body>
    </main>
`
)