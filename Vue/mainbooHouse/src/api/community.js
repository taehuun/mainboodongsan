import { localAxios } from "@/util/http-commons";

const local = localAxios();

function list(param, success, fail) {
    // console.log("파라미터 확인해보자", param)
    // local.get(`/article/list`, { params: param }).then(success).catch(fail);
    local
        .get(`/community/list`)
        .then(success)
        .catch((error) => {
            console.log("에러tㅅㅂ", error);
            fail(error);
        });
    console.log("파라미터 ㄱㄱ", param);
}
function detailArticle(articleno, success, fail) {
    local.get(`/community/getArticle?articleNo=${articleno}`).then(success).catch(fail);
}

function registArticle(article, success, fail) {
    console.log("boardjs article", article);
    local.post(`/community/write`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyArticle(articleno, success, fail) {
    local.get(`/community/modify/${articleno}`).then(success).catch(fail);
}

function modifyArticle(article, success, fail) {
    local.put(`/community`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(articleno, success, fail) {
    local.get(`/community/delete?articleNo=${articleno}`).then(success).catch(fail);
}

function rippleWrite(ripple, success, fail) {
    local.post(`/community/ripplewrite`, JSON.stringify(ripple)).then(success).catch(fail);
}

function deleteRipple(rippleno, articleno, success, fail) {
    local.get(`/community/deleteRipple?rippleNo=${rippleno}&articleNo=${articleno}`).then(success).catch(fail);
}

export { list, detailArticle, registArticle, getModifyArticle, modifyArticle, deleteArticle, rippleWrite, deleteRipple };
