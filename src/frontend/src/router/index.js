/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createRouter } from 'vue-router/auto'
import { setupLayouts } from 'virtual:generated-layouts'
import Index from "@/pages/index.vue";
import {createWebHashHistory} from "vue-router";
import DemoEvent from "@/pages/demoEvent.vue";
import DemoList from "@/pages/demoList.vue";
import Signin from "@/pages/signin.vue";
import Signup from "@/pages/signup.vue";
import Calendar from "@/pages/calendar.vue";

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: Index },
    { path: '/demoEvent', component: DemoEvent },
    { path: '/demoList', component: DemoList },
    { path: '/calendar', component: Calendar},
    { path: '/signin', component: Signin},
    { path: '/signup', component: Signup},
  ],
  extendRoutes: setupLayouts,
})

export default router
