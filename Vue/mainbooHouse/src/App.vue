<script setup>
import { RouterLink, RouterView } from "vue-router";
import LoginView from "./views/LoginView.vue";
import Footer from "./components/layout/Footer.vue";
import NavBar from "./components/layout/NavBar.vue";

var Tawk_API = Tawk_API || {},
    Tawk_LoadStart = new Date();
(function () {
    var s1 = document.createElement("script"),
        s0 = document.getElementsByTagName("script")[0];
    s1.async = true;
    s1.src = "https://embed.tawk.to/655d9d3491e5c13bb5b28b9b/1hfqqs5ii";
    s1.charset = "UTF-8";
    s1.setAttribute("crossorigin", "*");
    s0.parentNode.insertBefore(s1, s0);
})();
</script>

<template>
    <NavBar />
    <RouterView />

    <Footer :temp="holymoly"></Footer>
</template>

<style scoped>
@import "./assets/css/volt.css";
</style>
