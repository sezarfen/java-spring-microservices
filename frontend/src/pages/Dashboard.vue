<script setup>
  import api from '@/services/api.js';
  import {onMounted} from "vue";

  const getDashboard = async () => {
    const response = await api.get("/auth/me");

    console.log(response.data);
  }

  onMounted(() => {
    getDashboard();
  })

</script>

<template>
  <h1>Welcome to the dashboard</h1>
</template>