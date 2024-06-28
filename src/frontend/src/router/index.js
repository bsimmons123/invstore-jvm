/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createRouter, createWebHistory } from 'vue-router/auto'
import { setupLayouts } from 'virtual:generated-layouts'
import Index from "@/pages/index.vue";
import Demo from "@/pages/demo.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: Index },
    { path: '/demo', component: Demo },
  ],
  extendRoutes: setupLayouts,
})

export default router
