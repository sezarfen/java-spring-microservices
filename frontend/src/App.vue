<script setup>
 import Navbar from '@/components/Navbar.vue';
 import {useRoute} from "vue-router";

 const route = useRoute();
</script>

<template>
  <Navbar title="Patient" smallTitle="management" v-if="!route.meta.hideNavbar"/>
  <RouterView />
</template>