<template>
  <v-app :theme="theme">
    <app-header
      @update:theme="handleUpdateTheme"
    />

    <v-main style="margin-top: -24px">
      <router-view />
    </v-main>

    <app-footer/>
  </v-app>
</template>

<script setup>
import AppFooter from "@/components/AppFooter.vue";
import AppHeader from "@/components/AppHeader.vue";
import {ref} from "vue";

const theme = ref('light')

function handleUpdateTheme (val) {
  theme.value = val
}
</script>
