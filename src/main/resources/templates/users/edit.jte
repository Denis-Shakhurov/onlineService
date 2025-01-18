@import dto.UserPage
@param UserPage page

@template.layout.page(
content = @`
    <main class="flax-grow-1">
        <section>
            <div class="container-lg mt-5">
                <h1>${page.getUser().getFirstName()}</h1>
                @if(page.getUserInfo() != null)
                    <h1>${page.getUserInfo().getRole()}</h1>
                @else
                    <h1>${page.getUserInfo().getRole()}</h1>
                @endif
            </div>
        </section>
    </main>
`
)