<script setup>
const props = defineProps({
    showModal: Boolean
})
</script>

<template>
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="modal-notification" aria-hidden="true">
        <div class="modal-dialog modal-info modal-dialog-centered" role="document">
            <div class="modal-content bg-gradient-danger">
                <div class="modal-header">
                    <p class="modal-title" id="modal-title-notification">회원 탈퇴</p>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="py-3 text-center">
                        <span class="modal-icon display-1-lg"><span class="far fa-envelope-open"></span></span>
                        <h2 class="h4 modal-title my-3">회원 탈퇴 하시겠습니까?</h2>
                        <p>개인 정보가 영구 삭제되며, 복구가 불가능 합니다.</p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" @click="deleteInfo" class="btn btn-sm btn-white">회원탈퇴</button>
                    <button type="button" @click="close" class="btn btn-sm btn-dark">닫기</button>
                </div>
            </div>
        </div>
    </div>

    <!-- <div v-show="showModal" class="modal-mask">
      <div class="modal-container">
        <div class="modal-header">
          <slot name="header22">default header</slot>
        </div>

        <div class="modal-body">
          <slot name="body">default body</slot>
        </div>

        <div class="modal-footer">
          <slot name="footer">
            default footer
            <button
              class="modal-default-button"
              @click="$emit('close')"
            >OK</button>
          </slot>
        </div>
      </div>
    </div> -->
</template>

<style scoped>

</style>