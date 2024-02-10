import { localAxios } from "@/util/http-commons";

const local = localAxios();

function list(param, success, fail) {
    // console.log("파라미터 확인해보자", param)
    // local.get(`/article/list`, { params: param }).then(success).catch(fail);
    local.get(`/article/list`, { params: param })
    .then(success)
    .catch((error) => {
        console.log("에러tㅅㅂ", error);
        fail(error);
    });
        console.log("파라미터 ㄱㄱ", param)

}

  function detailArticle(articleno, success, fail) {
    local.get(`/article/getArticle?articleNo=${articleno}`).then(success).catch(fail);
  }
  
  function registArticle(article, success, fail) {
    console.log("boardjs article", article);
    local.post(`/article/write`, JSON.stringify(article)).then(success).catch(fail);
  }
  
  function getModifyArticle(articleno, success, fail) {
    local.get(`/article/modify/${articleno}`).then(success).catch(fail);
  }
  
  function modifyArticle(article, success, fail) {
    local.put(`/article`, JSON.stringify(article)).then(success).catch(fail);
  }
  
  function deleteArticle(articleno, success, fail) {
    local.get(`/article/delete?articleNo=${articleno}`).then(success).catch(fail);
  }
  
  export {
    list,
    detailArticle,
    registArticle,
    getModifyArticle,
    modifyArticle,
    deleteArticle,
  };