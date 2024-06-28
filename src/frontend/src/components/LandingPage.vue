<template>
  <v-main>
    <v-container>
      <v-row
        align="center"
        justify="center"
      >
        <v-col
          cols="12"
          md="20"
          class="d-flex flex-column align-center justify-center text-center white--text"
        >
          <h1 class="text-h2 mb-4">Inventory Store</h1>
          <p class="text-body-1">Here is some introduction text to grab your attention.</p>
          <br>
          <v-btn to="/demo">View demo Event</v-btn>
        </v-col>

        <v-col md="4">
          <v-card
            class="mx-auto"
            prepend-icon="$vuetify"
            subtitle="Features"
            width="400"
          >
            <template v-slot:title>
              <span class="font-weight-black">Built using Kotlin and Vuetify!!</span>
            </template>

            <v-card-text class="bg-surface-light pt-4">
              This probably means very little to you, but it does the thing all right!
            </v-card-text>
          </v-card>
        </v-col>

        <v-col md="4" class="pa-8">
          <v-card
            class="mx-auto"
            width="400"
          >
            <template v-slot:title>
              <span class="font-weight-black">What is this?</span>
            </template>

            <v-card-text class="bg-surface-light pt-4">
              By using Invstore you'll find that most of your event planning problems might be solved or made more complicated (Only time will tell!)
            </v-card-text>
          </v-card>
        </v-col>

        <v-col md="4" class="pa-13">
          <v-card
            class="mx-auto"
            width="400"
          >
            <template v-slot:title>
              <span class="font-weight-black">What do I do now!</span>
            </template>

            <v-card-text class="bg-surface-light pt-4">
              Break everything in this website to the best of your ability and then hopefully create an issue <a href="https://github.com/bsimmons123/invstore-jvm/issues/new">here</a>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>
