<template>
  <v-main>
    <particle-background/>
    <v-row
      align="center"
      justify="center"
      style="height: 500px"
    >
      <v-col
        cols="6"
        md="6"
        class="d-flex flex-column align-center justify-center text-center"
      >
        <h1 class="text-h2 mb-4">Inventory Store</h1>
        <p class="subtitle-1">Here is some introduction text to grab your attention.</p>
        <div>
          <v-btn color="secondary" to="/demoEvent">View demo Event</v-btn>
          <v-btn color="secondary" to="/demoList">View demo List</v-btn>
          <v-btn color="secondary" to="/calendar">Calendar</v-btn>
        </div>
      </v-col>
    </v-row>

    <v-container>
      <v-row>
        <!-- Card White -->
        <v-col
          cols="12"
          sm="6"
          md="4"
          class="pa-2"
        >
          <v-card
            outlined
            class="pa-4"
          >
            <v-card-title>
              Built using Kotlin and Vuetify!!
            </v-card-title>

            <v-card-text>
              This probably means very little to you, but it does the thing all right!
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Card Blue -->
        <v-col
          cols="12"
          sm="6"
          md="4"
          class="pa-2"
        >
          <v-card
            outlined
            color="light-blue lighten-4"
            class="pa-4"
          >
            <v-card-title>
              What is this?
            </v-card-title>

            <v-card-text>
              By using Invstore you'll find that most of your event planning problems might be solved or made more complicated (Only time will tell!)
            </v-card-text>
          </v-card>
        </v-col>

        <!-- Card Green -->
        <v-col
          cols="12"
          sm="6"
          md="4"
          class="pa-2"
        >
          <v-card
            outlined
            class="pa-4"
          >
            <v-card-title>
              What do I do now!
            </v-card-title>

            <v-card-text>
              Break everything in this website to the best of your ability and then hopefully create an issue
            </v-card-text>

            <v-card-actions>
              <v-btn color="secondary" href="https://github.com/bsimmons123/invstore-jvm/issues/new">Go To Issue</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

  </v-main>
</template>

<script setup>
import { useAppStore } from "@/stores/app";
import {onMounted} from "vue";
import ParticleBackground from "@/components/ParticleBackground.vue";

onMounted(() => {
  let store = useAppStore()

  store.getUser()
})
</script>
