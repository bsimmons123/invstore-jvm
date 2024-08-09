<template>

      <v-menu
        min-width="200px"
        rounded
      >
        <template v-slot:activator="{ props }">
          <v-btn
            icon
            v-bind="props"
          >
            <v-avatar
              color="brown"
              size="large"
              :image="`/api/v1/image/user/${user.id}`"
            >
            </v-avatar>
          </v-btn>
        </template>
        <v-card>
          <v-card-text>
            <div class="mx-auto text-center">
              <v-avatar
                color="brown"
                :image="`/api/v1/image/user/${user.id}`"
              >

              </v-avatar>
              <h3>{{ user.username }}</h3>
              <p class="text-caption mt-1">
                {{ user.email }}
              </p>
              <v-divider class="my-3"></v-divider>
              <v-btn
                variant="text"
                rounded
                @click="alert('Not available yet')"
              >
                Edit Account
              </v-btn>
              <v-divider class="my-3"></v-divider>
              <v-btn
                variant="text"
                rounded
                @click="emit('logout:disconnect')"
              >
                Disconnect
              </v-btn>
            </div>
          </v-card-text>
        </v-card>
      </v-menu>
</template>
<script setup>
const emit = defineEmits(["logout:disconnect"]);

defineProps({
  user: {
    type: Object,
    default: {},
  }
})
</script>
