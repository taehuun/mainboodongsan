import {localAxios} from "@/util/http-commons";

const local = localAxios();

async function userLoginApi(param, success, fail) {
    console.log("userLoginApi started");
    await local.post(`/login`, param).then(success).catch(fail);
    console.log("userConfirm ok");
}

async function userAuthApi(params, success, fail){
    console.log("userAuthApi started");
    local.defaults.headers["Authorization"] = params.token;
    await local.get(`/userAuth/${params.email}`).then(success).catch(fail);
    console.log("userAuthApi 끝");
}

async function getUserInfoApi(token, success, fail){
    console.log("getUserInfoApi start");
    local.defaults.headers["GetUserInfo"] = token;
    await local.get(`/getUserInfo`).then(success).catch(fail);
}

async function refreshTokenApi(userInfo, success, fail){
    console.log("refreshTokenApi started");
    local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken");
    await local.post(`/refresh`, userInfo).then(success).catch(fail);
}

async function userLogoutApi(params, success, fail){
    console.log("userLogoutApi started");
    await local.get(`/logout/${params.email}`).then(success).catch(fail);
}

async function userJoinApi(params, success, fail) {
    console.log("userJoinApi started");
    await local.post(`/join`, params).then(success).catch(fail);
}

async function userDeleteApi(params, success, fail) {
    console.log("userDeleteApi started");
    await local.get(`/userDelete/${params.email}`).then(success).catch(fail);
}

async function userEditApi(params, success, fail) {
    console.log("userEditApi started");
    await local.post(`/userEdit`, params.update, { params : {originalEmail : params.email}}).then(success).catch(fail);
}

export {
    userLoginApi, 
    userAuthApi, 
    refreshTokenApi, 
    userLogoutApi, 
    getUserInfoApi, 
    userJoinApi,
    userDeleteApi,
    userEditApi,
};

