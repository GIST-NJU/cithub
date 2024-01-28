// 这里存放一些公用的js
// import pinia from './pinia';
// import { useUserStore } from "./store/userStore"

import { request } from "./request"



export const CheckLoginStatus = async () => {
    // console.log("CheckLoginStatus！！！！！！")
    let tokenForVerify = localStorage.getItem("userToken");

    if (tokenForVerify) {
        // console.log("有token，向后台检测是否有效");

        try {
            const res = await request({
                url: 'user/users/VerifyToken',
                method: 'GET',
                params: {
                    tokenForVerify
                }
            });

            // console.log("验证token的结果是", res);

            if (res.verifstatus === 'success') {
                // console.log("验证token成功");
                return { "loginStatus": true, "token": tokenForVerify };
            } else {
                // console.log("该token无效，返回false");

                return { "loginStatus": false, "token": tokenForVerify }
            }
        } catch (error) {
            console.error("请求出错", error);
            // 处理请求错误
            return { "loginStatus": false, "token": tokenForVerify }
        }
    } else {

        // console.log("该token无效，返回false");


        return { "loginStatus": false, "token": tokenForVerify }
    }
};


// 通过token 获得user信息

export const getUserInfoByToken = async (token) => {
    try {
        const res = await request({
            url: "user/users/getUserInfoByToken",
            method: 'GET',
            params: {
                userToken: token
            }
        });

        // 在这里处理获取到的用户信息
        return {
            "account": res.account,
            "userToken": res.userToken,
            "usertype": res.usertype,
            "name": res.name,
            "userid": res.userid,
            "email": res.email,
            "institution": res.institution
        }


    } catch (error) {
        console.error("获取用户信息错误", error);
        throw error; // 抛出错误，以便在调用方进行处理
    }
};