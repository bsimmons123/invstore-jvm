<template>]
  <v-app-bar
    class="px-3"
    density="compact"
    flat
  >
    <div class="text-h6 text-uppercase font-weight-bold">Invstore</div>

    <v-spacer></v-spacer>

    <v-btn
      :prepend-icon="theme === 'light' ? 'mdi-weather-sunny' : 'mdi-weather-night'"
      text="Toggle Theme"
      slim
      @click="onClick"
    ></v-btn>

    <v-tabs
      color="grey-darken-2"
      centered
    >
      <v-tab
        v-for="link in links"
        :key="link"
        :text="link.label"
        :to="link.link"
      ></v-tab>
    </v-tabs>

    <v-avatar
      class="hidden-sm-and-down"
      color="grey-darken-1"
      size="32"
    ></v-avatar>
    <!--  This should be a Sign In button if not logged in  -->
  </v-app-bar>
</template>

<script setup>
import { ref } from 'vue'
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  theme: {
    type: String,
    default: 'light'
  }
})

const emit = defineEmits(['update:theme'])
const links = ref(
  [
    {
    label: 'Home',
    link: '/'
    }
  ],
)

const theme = ref(props.theme)

function onClick () {
  theme.value = theme.value === 'light' ? 'dark' : 'light'
  emit('update:theme', theme.value)
}
</script>
