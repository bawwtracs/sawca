const portoal = "http";
const host = "localhost";
const port = "18080";

const remote = `${portoal}://${host}:${port}`;

const api = {
    checkUsernameAvail(username) {
        return `${remote}/api/acc/avail/username/${username}`
    },
    checkEmailAvail(email) {
        return `${remote}/api/acc/avail/email/${email}`
    },
    register() {
        return `${remote}/api/acc/register`;
    },
    login() {
        return `${remote}/api/acc/login`;
    },
    guest() {
        return `${remote}/api/acc/guest`;
    },
    userinfo() {
        return `${remote}/api/acc/info`;
    },
    userHead(id) {
        return `${remote}/api/acc/${id}/head`;
    },
    notes(accid) {
        return `${remote}/api/note/list/acc/${accid}`
    },
    note: noteId => {
        return `${remote}/api/note${
            noteId ? "/" + noteId : ""
            }`;
    },
    todos(accid) {
        return `${remote}/api/todo/list/acc/${accid}`
    },
    todo: todoId => {
        return `${remote}/api/todo${
            todoId ? "/" + todoId : ""
            }`;
    },
    origins: () => {
        return `${remote}/api/vocabulary/origins`;
    },
    origin: originId => {
        return `${remote}/api/vocabulary/origin${
            originId ? "/" + originId : ""
            }`;
    },
    words: () => {
        return `${remote}/api/vocabulary/words`;
    },
    word: wordId => {
        return `${remote}/api/vocabulary/word${
            wordId ? "/" + wordId : ""
            }`;
    }
}
export default api;