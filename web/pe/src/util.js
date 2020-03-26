const hex = (length, n) => {
    n = n.toString(16);
    return (n.length === length) ? n : "00000000".substring(n.length, length) + n;
}

let util = {
    hexString(objectId) {
        return `${hex(8, objectId.timestamp)}${hex(6, objectId.machineIdentifier)}${hex(4, objectId.processIdentifier)}${hex(6, objectId.counter)}`;
    }
}

export default util;