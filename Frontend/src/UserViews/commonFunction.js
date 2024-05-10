import CryptoJS from 'crypto-js';

// 加密密码的方法
const  encryptPassword=(password) => {
    // 使用SHA256算法对密码进行加密
    const encryptedPassword = CryptoJS.SHA256(password).toString();
    return encryptedPassword;
}


export {encryptPassword}