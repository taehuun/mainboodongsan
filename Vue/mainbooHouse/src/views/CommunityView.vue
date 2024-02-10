<!-- <script setup></script>

<template>
    <main class="">
        <div class="row">
            <div class="col-12 mb-4">
                <div class="card border-light shadow-sm">
                    <div class="card-body">
                        

                        <div class="mt-3 mb-3" style="margin-left: 50px; margin-right: 50px">
                           
                            <div class="d-flex justify-content-center mb-2" style="height: 100px">
                                <div class="row">
                                    <div class="col align-self-center">
                                        <input type="text" @keydown.enter="boardList" placeholder="검색어를 입력하세요" class="form-control" id="searchText" style="width: 400px" />
                                    </div>
                                    <div class="col align-self-center">
                                        <button @click="boardList" class="btn btn-secondary" type="button">Search</button>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="float: right; margin-right: 20px">
                                <button class="btn mb-3 btn-secondary btn-rounded" data-mdb-ripple-color="dark" @click="showInsertModal">글쓰기</button>
                            </div>
                            <table class="table table-hover text-center">
                                <thead class="bg-primary text-white">
                                    <tr>
                                        <th>글번호</th>
                                        <th style="width: 700px">제목</th>
                                        <th>작성자</th>
                                        <th>작성일시</th>
                                        <th>조회수</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="(board, index) in listGetters" @click="boardDetail(board.boardId)" v-bind:key="index">
                                        <td>{{ board.boardId }}</td>
                                        <td>{{ board.title }}</td>
                                        <td>{{ board.userName }}</td>

                                        <td>{{ formatDate[index] }}</td>

                                        <td>{{ board.readCount }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <pagination v-on:call-parent="movePage"></pagination>

                        <insert-modal v-on:call-parent-insert="closeAfterInsert"></insert-modal>
                        <detail-modal v-on:call-parent-change-to-update="changeToUpdate" v-on:call-parent-change-to-delete="changeToDelete"></detail-modal>
                        <update-modal v-on:call-parent-update="closeAfterUpdate"></update-modal>
                    </div>
                </div>
            </div>
        </div>
    </main>
</template>

<style scoped></style> -->

<script setup>
import BasicHeader from "../components/layout/BasicHeader.vue";
</script>

<template>
    <div>
        <BasicHeader name="커뮤니티"/>
        <router-view></router-view>
    </div>
</template>

<style scoped>

</style>