const wordsTable = document.getElementById('wordsTable')
const searchBar = document.getElementById('searchInput')

const allWords = [];

let tableHead =
    el('thead', [
        el('tr', [
            el('th', 'English', {className: 'col-md-4 text-center'}),
            el('th', 'Bulgarian', {className: 'col-md-4 text-center'}),
            el('th', 'Hear', {className: 'col-md-2 text-center'}),
            el('th', 'My words', {className: 'col-md-2 text-center'})
        ], {className: 'row mx-auto'}),
    ]);

let tableBody = el('tbody', '');

fetch("http://localhost:8080/words/api").then(response => response.json()).then(data => {
    for (let word of data) {
        allWords.push(word);
    }
});

console.log(allWords);

searchBar.addEventListener('keyup', (e) => {
    let searchingCharacters = searchBar.value.trim().toLowerCase();
    if (searchingCharacters === '') {
        wordsTable.innerHTML = '';
        return
    }
    let filteredWords = allWords.filter(word => {
        return word.english.toLowerCase().startsWith(searchingCharacters)
            || word.bulgarian.toLowerCase().startsWith(searchingCharacters);
    });
    wordsTable.innerHTML = '';
    tableBody.innerHTML = '';
    displayWords(filteredWords).then(r => console.log(r));
})


async function displayWords(words) {
    if (words.length > 0) {

        let allWordsByUser = [];
        await (fetch("http://localhost:8080/words/user/api").then(response => response.json()).then(data => {
            for (let word of data) {
                allWordsByUser.push(word);
            }
        }));

        console.log(allWordsByUser);
        wordsTable.appendChild(tableHead);
        wordsTable.appendChild(tableBody);

        words.forEach((word) => {

            let myWords = el('td', '', {className: 'col-md-2 text-center'});

            let addButton = el('button', 'Add', { className: 'btn btn-success font-weight-bold text-white'});
            let removeButton = el('button', 'Remove', { className: 'btn btn-danger font-weight-bold text-white'});

            addButton.addEventListener('click', async () => {
                await fetch(`http://localhost:8080/words/add/${word.id}/api`);
                myWords.removeChild(addButton);
                myWords.appendChild(removeButton);
            });

            removeButton.addEventListener('click', async () => {
                await fetch(`http://localhost:8080/words/remove/${word.id}/api`);
                myWords.removeChild(removeButton);
                myWords.appendChild(addButton);
            });

            if (allWordsByUser.length === 0) {
                myWords.appendChild(addButton);
            } else {
                if (allWordsByUser.includes(word.id)) {
                    myWords.appendChild(removeButton);
                } else {
                    myWords.appendChild(addButton);
                }
            }

            let hearWord = el('td', '', {className: 'col-md-2 text-center'});
            let hearSpan = el('span', [
                el('i', '', { className: 'far fa-play-circle'})
            ]);

            hearSpan.addEventListener('click', () => {
                textSpeak(`${word.english}`)
            });

            hearWord.appendChild(hearSpan);

            let tBodyContent =  el('tr', [
                el('td', `${word.english}`, { className: 'col-md-4 text-center'}),
                el('td', `${word.bulgarian}`, { className: 'col-md-4 text-center'}),
                hearWord,
                myWords
            ], { className: 'row mx-auto'})

            tableBody.appendChild(tBodyContent);
        });
    }
}

function textSpeak(text) {
    responsiveVoice.speak(text);
}

function el(type, content, attributes) {
    const result = document.createElement(type);

    if (attributes !== undefined) {
        Object.assign(result, attributes);
    }

    if (Array.isArray(content)) {
        content.forEach(append);
    } else if (content !== null && content !== undefined) {
        append(content);
    }

    function append(node) {
        if (typeof node === 'string' || typeof node === 'number') {
            node = document.createTextNode(node);
        }
        result.appendChild(node);
    }

    return result;
}
