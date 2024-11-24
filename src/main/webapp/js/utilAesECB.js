function cifrar(message, key) {
    var keyHex = CryptoJS.enc.Utf8.parse(key);

    var encrypted = CryptoJS.AES.encrypt(message, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return encrypted.toString();
}

function descifrar(ciphertext, key) {
    var keyHex = CryptoJS.enc.Utf8.parse(key);

    var decrypted = CryptoJS.AES.decrypt(ciphertext, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });

    return decrypted.toString(CryptoJS.enc.Utf8);
}
