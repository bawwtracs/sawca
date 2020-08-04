export default {
    put(key, value) {
        if (typeof value !== "string") {
            value = JSON.stringify(value);
        }
        window.localStorage[key] = value;
    },
    get(key) {
        if (window.localStorage[key]) {
            return JSON.parse(window.localStorage[key]);
        } else {
            return false;
        }
    }
}
