const portoal = "http";
const host = "localhost";
const port = "18080";

const remote = `${portoal}://${host}:${port}`;

const api = {
    advice() {
        return "https://api.adviceslip.com/advice";
    },
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
    }
}
export default api;