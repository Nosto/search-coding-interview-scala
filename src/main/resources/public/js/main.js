import { executeSearch } from './search.js'

document.addEventListener('DOMContentLoaded', async () => {
    const form = document.getElementById('searchform')

    form.addEventListener('submit', event => {
        event.preventDefault()
        executeSearch(new FormData(form).get('query') || '', {})
    })

    executeSearch('', {})

    document.body.classList.remove('loading')
})

