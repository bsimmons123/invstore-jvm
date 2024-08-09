<template>
  <v-main>
    <v-container>
      <v-row>
        <v-col cols="4">
          <h1 class="display-3 mb-4">{{ formatDate(new Date().toLocaleDateString()) }}</h1>
        </v-col>
        <v-col>
          <create-demo-list-item
           @create:time-item="addTimeItem"
          ></create-demo-list-item>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="4" style="height: 100%">
            <v-timeline align="start" side="end">
              <v-timeline-item
                v-for="(item, index) in timeline"
                :key="index"
                :dot-color="item.dotColor"
                size="small"
              >
                    <div class="d-flex" style="flex-wrap: wrap; word-break: break-all;">
                  <v-btn  style="height: 55px; overflow: hidden; max-width: 100%;" @click="eventClicked(item.title)">
                      <div>
                        <strong>{{ truncateTitle(item.title) }}</strong>
                      </div>
                  </v-btn>
                    </div>
              </v-timeline-item>
            </v-timeline>
        </v-col>

        <v-col cols="8" >
          <v-sheet elevation="2" rounded class="overflow-hidden">
            <v-toolbar dense :color="selectedItem.dotColor" dark>
              <v-toolbar-title>{{ `${selectedItem.title} - (${convertTo12HourFormat(selectedItem.time)})` }}</v-toolbar-title>
            </v-toolbar>

            <v-divider></v-divider>

            <v-card-text class="px-4">
              <h3 class="mb-2">{{ selectedItem.subtitle }}</h3>
              <p>{{ selectedItem.description }}</p>
            </v-card-text>

            <v-divider></v-divider>

            <v-card-text class="px-4">
              <div class="d-flex align-center mb-2">
                <h5 class="mr-2">Location:</h5>
                <div class="body-2">
                  <div class="body-2">
                    {{ selectedItem.location }}
                  </div>
                </div>
              </div>

              <div class="d-flex align-center mb-2">
                <h5 class="mr-2">Priority:</h5>
                <div>
                  <v-chip x-small :color="chipColor">
                    {{ selectedItem.priority }}
                  </v-chip>
                </div>
              </div>



              <div class="d-flex align-center mb-2">
                <h5 class="mr-2">Created Date:</h5>
                <div class="body-2">
                  <div class="body-2">
                    {{ selectedItem.createdDate }}
                  </div>
                </div>
              </div>

              <div class="d-flex align-center mb-2">
                <h5 class="mr-2">Modified Date:</h5>
                <div class="body-2">
                  {{ selectedItem.updatedDate }}
                </div>
              </div>

              <div class="d-flex align-center mb-2">
                <h5 class="mr-2">Tags:</h5>
                <div>
                  <v-chip x-small color="blue-grey lighten-5" v-for="tag in selectedItem.tags" :key="tag">
                    {{ tag }}
                  </v-chip>
                </div>
              </div>
            </v-card-text>

            <v-divider></v-divider>

            <v-card-actions class="justify-center py-8">
              <v-btn small color="teal-accent-4">Edit</v-btn>
            </v-card-actions>
          </v-sheet>
          <v-container >
            <v-timeline
              density="compact"
              side="end"
            >
              <v-slide-x-transition group>
                <v-timeline-item
                  v-for="item in eventList(selectedItem.events)"
                  :key="item.id"
                  class="mb-4"
                  size="small"
                >
                  <p>{{ item.text }}</p>
                  <v-spacer />
                  <p>
                    <span style="color: grey">
                    Time:
                    {{ convertTo12HourFormat(item.time) }}
                    </span>
                  </p>
                </v-timeline-item>
              </v-slide-x-transition>
            </v-timeline>
          </v-container>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script setup>
import {computed, ref} from 'vue'
import CreateDemoListItem from "@/components/demo/CreateDemoListItem.vue";

const timeLineItems = ref([
  {
    title: 'Eat Breakfast',
    time: '09:00',
    subtitle: 'Green Eggs and Ham!',
    description: "This board is taking forever, come and help me!!",
    dotColor: 'pink',
    createdDate: '2024-05-05',
    updatedDate: '2024-06-10',
    eventDate: '2024-07-15',
    currentDate: new Date().toLocaleDateString(), // Current date
    location: 'Kichen',
    priority: 'Major',
    tags: ['Breakfast', 'Eggs'],
    events: [
      {
        id: 1,
        text: 'Event Created.',
        time: '13:26',
        color: 'grey',
      },
      {
        id: 2,
        text: 'Event Updated.',
        time: '15:25',
        color: 'primary',
      },
    ]
  },
  {
    title: 'Design Stand Up',
    time: '11:00',
    subtitle: 'Hangouts',
    description: "This board is taking forever, come and help me!!",
    dotColor: 'teal-lighten-3',
    createdDate: '2024-05-06',
    updatedDate: '2024-06-12',
    eventDate: '2024-07-16',
    currentDate: new Date().toLocaleDateString(), // Current date
    location: 'Online',
    priority: 'Minor',
    tags: ['Design', 'StandUp'],
    events: [
      {
        id: 1,
        text: 'Event Created.',
        time: '11:26',
        color: 'grey',
      },
      {
        id: 2,
        text: 'Event Marked Inactive.',
        time: '15:25',
        color: 'primary',
      },
      {
        id: 3,
        text: 'Event Updated',
        time: '17:35',
        color: 'primary',
      },
    ]
  },
  {
    title: 'Lunch',
    time: '12:00',
    subtitle: 'Lets get Panera',
    description: "I'm thinking a salad today",
    dotColor: 'purple',
    createdDate: '2024-06-24',
    updatedDate: '2024-06-24',
    eventDate: '2024-07-16',
    currentDate: new Date().toLocaleDateString(), // Current date
    location: 'Online',
    status: 'Completed',
    priority: 'Normal',
    tags: ['Design', 'StandUp'],
    events: [
      {
        id: 1,
        text: 'Event Created.',
        time: '11:26',
        color: 'grey',
      },
      {
        id: 2,
        text: 'Event Marked Inactive.',
        time: '15:25',
        color: 'primary',
      },
      {
        id: 3,
        text: 'Event Updated',
        time: '17:35',
        color: 'primary',
      },
      {
        id: 4,
        text: 'Event Marked Inactive.',
        time: '11:25',
        color: 'primary',
      },
      {
        id: 5,
        text: 'Event Updated',
        time: '13:35',
        color: 'primary',
      },
    ]
  },
  {
    title: 'Tech Meeting',
    time: '13:00',
    subtitle: 'Hangouts',
    description: "This board is taking forever, come and help me!!",
    dotColor: 'orange',
    createdDate: '2024-05-06',
    updatedDate: '2024-06-12',
    eventDate: '2024-07-16',
    currentDate: new Date().toLocaleDateString(), // Current date
    location: 'Online',
    priority: 'Normal',
    tags: ['Design', 'StandUp'],
    events: [
      {
        id: 1,
        text: 'Event Created.',
        time: '11:26',
        color: 'grey',
      },
      {
        id: 2,
        text: 'Event Marked Inactive.',
        time: '15:25',
        color: 'primary',
      },
      {
        id: 3,
        text: 'Event Updated',
        time: '17:35',
        color: 'primary',
      },
      {
        id: 4,
        text: 'Event Updated',
        time: '13:35',
        color: 'primary',
      },
    ]
  },
  {
    title: 'Actually develop all the things',
    time: '14:00',
    subtitle: 'Jetbrains <3',
    description: "Lets do the work of 10 men in the time span of 3 hours",
    dotColor: 'pink',
    createdDate: '2024-05-06',
    updatedDate: '2024-06-12',
    currentDate: new Date().toLocaleDateString(), // Current date
    location: 'Online',
    priority: 'Severe',
    tags: ['Design', 'StandUp'],
    events: [
      {
        id: 1,
        text: 'Event Created.',
        time: '11:26',
        color: 'grey',
      },{
        id: 2,
        text: 'Event Updated',
        time: '17:35',
        color: 'primary',
      },
      {
        id: 3,
        text: 'Event Updated',
        time: '13:35',
        color: 'primary',
      },
    ]
  },
  // ... add more items
])

const selectedItem = ref(timeLineItems.value[0])

const eventList = (events) => events.sort((a, b) => {
  // sort events based on the time property
  return a.time.localeCompare(b.time);
});

const timeline = computed(() => timeLineItems.value.sort((a, b) => {
  // sort events based on the time property
  return a.time.localeCompare(b.time);
}));

const formatDate = (dateString) => {
  var options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString(undefined, options);
}

const truncateTitle = (title) => {
  if (title.length > 20) return title.substring(0,20).concat('...'); else return title;
}

function convertTo12HourFormat(time) {
  const [hour, minute] = time.split(':');
  return ((hour % 12 || 12) + ':' + minute + ' ' + (hour < 12 ? 'AM' : 'PM'));
}

const eventClicked = (eventName) => {
  // Update selectedItem based on the item title passed to eventClicked()
  selectedItem.value = timeLineItems.value.find(item => item.title === eventName);
}

const addTimeItem = (item) => {
  timeLineItems.value.push(item)
}

// Map of priorities to colors
const priorityColors = {
  'Minor': 'green',
  'Normal': 'blue',
  'Important': 'orange',
  'Major': 'brown',
  'Severe': 'red'
};

const chipColor = computed(() => {
  return priorityColors[selectedItem.value.priority];
});
</script>

<style scoped>

</style>
