import {localAxios} from "@/util/http-commons";

const local = localAxios();

async function favorRegisterApi(param, success, fail) {
    console.log("favorRegisterApi started");
    await local.post(`/addrs/register`, param.house, {params : {email : param.email}}).then(success).catch(fail);
}

async function favorDeleteApi(param, success, fail){
    console.log("favorDeleteApi started");
    await local.post(`/addrs/delete`, param.house, {params : {email : param.email}}).then(success).catch(fail);
}

async function getFavorListApi(param, success, fail){
    console.log("getFavorListApi started");
    await local.get(`/addrs/dealsFavor`, {params : {email : param}}).then(success).catch(fail);
}

async function getDealYearApi(param, success, fail){
    console.log("getDealYearApi started");
    await local.get(`/addrs/getDealYear`, {params : {aptCode : param}}).then(success).catch(fail);
}

async function getDealByYearApi(param, success, fail) {
    console.log("getDealByYearApi started");
    await local.get(`/addrs/getDealByYear`, {params: {aptCode : param.aptCode, dealYear : param.dealYear}}).then(success).catch(fail);

}

export {
    favorRegisterApi,
    favorDeleteApi,
    getFavorListApi,
    getDealYearApi,
    getDealByYearApi,
};
