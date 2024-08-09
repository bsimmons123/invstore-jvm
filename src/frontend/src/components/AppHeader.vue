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

    <template v-if="loading">
      <v-btn
        :loading="loading"
      ></v-btn>
    </template>
    <template v-else>
      <user-avatar
        v-if="user"
        :user="user"
        @logout:disconnect="store.logout()"
      />

      <v-btn rounded="xl" color="blue" variant="elevated" to="/signin" v-else>Signin</v-btn>
    </template>
  </v-app-bar>
</template>

<script setup>
import {computed, ref} from 'vue'
import {useAppStore} from "@/stores/app";
import UserAvatar from "@/components/UserAvatar.vue";

let store = useAppStore()

const loading = computed(() => store.isLoading);
const user = computed(() => store.user);

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
