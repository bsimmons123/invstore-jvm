<script setup>
import { computed } from "vue";
import { useStore } from "vuex";
import StoreIndex from "@/store/catering/_StoreIndex";
import {StoreActions} from "@/store/catering/actions";

const store = useStore();
const isRTL = computed(() => store.state.isRTL);

defineProps({
  title: {
    type: String,
    default: "Categories",
  },
  categories: {
    type: Array,
    required: true,
    icon: {
      component: String,
      background: String,
    },
    label: String,
    description: String,
  },
});

const joinList = (id) => store.dispatch(`${StoreIndex.storeName}/${StoreActions.joinList}`, id);
</script>
<template>
  <div class="card">
    <div class="p-3 pb-0 card-header">
      <h6 class="mb-0">{{ title }} ({{ categories.length}})</h6>
    </div>
    <div class="p-3 card-body">
      <ul :class="`list-group ${isRTL ? 'pe-0' : ''}`">
        <li
          v-for="(
            { icon, label, description }, index
          ) of categories"
          :key="index"
          :class="`mb-2 border-0 list-group-item d-flex justify-content-between border-radius-lg
          ${isRTL ? 'pe-0' : 'ps-0'}`"
        >
          <div class="d-flex align-items-center">
            <div
              :class="`text-center shadow icon icon-shape icon-sm me-3`"
            >
              <i :class="`text-white opacity-10`"></i>
              <img :src="`/api/v1/image/list/${icon.relatedList.id}`" class="avatar avatar-sm me-3" alt="user2" />
            </div>
            <div class="d-flex flex-column">
              <h6 class="mb-1 text-sm text-dark">{{ label }}</h6>
              <!-- eslint-disable-next-line vue/no-v-html -->
              <span class="text-xs" v-html="description"> </span>
            </div>
          </div>
          <div class="d-flex">
            <button
              class="my-auto btn btn-link btn-icon-only btn-rounded btn-sm text-dark icon-move-right"
              @click="joinList(icon.id)"
            >
              <i
                :class="`ni ${isRTL ? 'ni-bold-left' : 'ni-bold-right'}`"
                aria-hidden="true"
              ></i>
            </button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
