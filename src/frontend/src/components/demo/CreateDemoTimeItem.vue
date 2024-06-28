<template>
  <div>
    <v-dialog
      v-model="dialog"
    >
      <template v-slot:activator="{ props: activatorProps }">
        <v-btn
          class="text-none font-weight-regular"
          prepend-icon="mdi-account"
          text="Add Event"
          variant="tonal"
          v-bind="activatorProps"
        ></v-btn>
      </template>

      <v-card
        prepend-icon="mdi-account"
        title="Add Event Item"
      >
        <v-card-text>
          <v-row dense>
            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                label="Title*"
                required
                v-model="newItem.title"
              ></v-text-field>
            </v-col>

            <v-col
              cols="12"
              md="6"
              sm="6"
            >
              <v-text-field
                hint="Enter subtitle here"
                label="Subtitle"
                v-model="newItem.subtitle"
              ></v-text-field>
            </v-col>

            <v-col
              cols="12"
              md="4"
              sm="6"
            >
              <v-text-field
                label="Location"
                v-model="newItem.location"
              ></v-text-field>
            </v-col>

            <v-col
              cols="12"
              md="4"
              sm="6"
            >
              <v-select
                hint="Select Priority"
                label="Priority"
                v-model="newItem.priority"
                :items="['Minor', 'Normal', 'Important', 'Major', 'Severe']"
              ></v-select>
            </v-col>

            <v-col
              cols="12"
              md="4"
              sm="6"
            >
              <v-combobox
                v-model="model"
                v-model:search="newItem.tags"
                :hide-no-data="false"
                :items="items"
                hint="Maximum of 5 tags, type to add more"
                label="Tags"
                hide-selected
                multiple
                persistent-hint
                small-chips
              >
                <template v-slot:no-data>
                  <v-list-item>
                    <v-list-item-title>
                      No results matching "<strong>{{ newItem.tags }}</strong>". Press <kbd>enter</kbd> to create a new one
                    </v-list-item-title>
                  </v-list-item>
                </template>
              </v-combobox>
            </v-col>

            <v-col
              cols="12"
            >
              <v-text-field
                hint="Enter description here"
                label="Description"
                v-model="newItem.description"
              ></v-text-field>
            </v-col>

            <v-col
              cols="12"
              md="4"
              sm="6"
            >
              <v-text-field
                v-model="newItem.time"
                :active="timeMenu"
                label="Event Start Time"
                prepend-icon="mdi-clock-time-four-outline"
                readonly
              >
                <v-menu
                  v-model="timeMenu"
                  :close-on-content-click="false"
                  activator="parent"
                  transition="scale-transition"
                >
                  <v-time-picker
                    v-if="timeMenu"
                    v-model="newItem.time"
                    :ampm-in-title="true"
                    color="green-lighten-1"
                    full-width
                  ></v-time-picker>
                </v-menu>
              </v-text-field>
            </v-col>

            <!-- Additional inputs for newItem -->

          </v-row>

          <small class="text-caption text-medium-emphasis">*indicates required field</small>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            text="Close"
            variant="plain"
            @click="dialog = false"
          ></v-btn>

          <v-btn
            color="primary"
            text="Save"
            variant="tonal"
            @click="addItem()"
          ></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script setup>
import {nextTick, ref} from "vue";
import { VTimePicker } from 'vuetify/labs/VTimePicker'
import { defineEmits } from 'vue'
import {watch} from "vue-demi";

const dialog = ref(false)
const timeMenu = ref(false);
const items = ref(['Personal', 'Lunch', 'Buisness']); // Initially available options
const model = ref([]);
const colorNames = ['red', 'green', 'blue', 'yellow', 'pink', 'purple', 'orange', 'teal', 'indigo', 'amber']; // Add as many colors as you need
const dotColor = () => {return colorNames[Math.floor(Math.random() * colorNames.length)]};

const emit = defineEmits(['create:timeItem'])

let newItem = ref({
  title: '',
  subtitle: '',
  description: '',
  eventDate: '',
  time: '',
  location: '',
  tags: [],
  priority: '',
  dotColor: '',
  createdDate: new Date().toLocaleDateString(), // Current date
  updatedDate: new Date().toLocaleDateString(), // Current date
  events: [
    {
      id: 1,
      text: 'Event Created.',
      time: new Date().toTimeString(),
      color: 'grey',
    }
    ]
  /* Initialize all other fields with appropriate defaults */
})

function addItem() {
  if (!newItem.value.title) {
    return;
  }
  // Add logic to store your newItem, you could emit an event, call a method,
  // or directly push to the array holding your timeline data (if within scope).
  dialog.value = false;

  newItem.value.tags = model.value;
  newItem.value.dotColor = dotColor();

  let item = {...newItem.value}

  emit('create:timeItem', item)
}

watch(model, (newVal) => {
  if (newVal.length > 5) {
    nextTick(() => model.value.pop());
  }
});
</script>

<style scoped>

</style>
