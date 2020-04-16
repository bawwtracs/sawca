const portoal = "http";
const host = "localhost";
const port = "18080";

const api = {
    advice() {
        return "https://api.adviceslip.com/advice";
    },
    register() {
        return `${portoal}://${host}:${port}/api/acc/register`;
    },
    login() {
        return `${portoal}://${host}:${port}/api/acc/login`;
    },
    guest() {
        return `${portoal}://${host}:${port}/api/acc/guest`;
    },
    userinfo() {
        return `${portoal}://${host}:${port}/api/acc/info`;
    }
}
export default api;