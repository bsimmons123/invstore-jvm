<template>
  <v-app :theme="theme">
    <app-header
      :theme="theme"
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
import {useAppStore} from "@/stores/app";

const theme = ref(localStorage.getItem("theme") ? localStorage.getItem("theme") : "light")
let store = useAppStore()
store.theme = theme.value;

function handleUpdateTheme (val) {
  localStorage.setItem("theme", val)
  store.theme = val;
  theme.value = val
}
</script>
