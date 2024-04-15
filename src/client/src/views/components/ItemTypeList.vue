<script setup>
import ArgonButton from "@/components/ArgonButton.vue";
import {createCateringItemTypeAdapter} from "@/store/catering/services/CreateCateringItemTypeAdapter";

defineProps({
  title: {
    type: String,
    default: "Item List",
  },
  loading: {
    type: Boolean,
    default: false,
  },
  types: {
    type: Object,
    default: createCateringItemTypeAdapter()
  },
});
</script>
<template>
  <div class="card h-100 mb-4">
    <div class="card-header pb-0 px-3">
      <div class="row">
        <div class="col-md-6">
          <h6 class="mb-0">Types</h6>
        </div>
        <div class="col-6 text-end">
          <argon-button color="dark" variant="gradient" data-bs-toggle="modal" data-bs-target="#addCateringItemTypeModal">
            <i class="fas fa-plus me-2"></i>
            Add Type
          </argon-button>
        </div>
      </div>
    </div>
    <div class="card-body pt-4 p-3">
      <h6 class="text-uppercase text-body text-xs font-weight-bolder mb-3">
        Newest
      </h6>
      <ul class="list-group">
        <li
          class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg"
          v-for="type in types"
          :key="type.id"
        >
          <div class="d-flex align-items-center">
            <argon-button
              color="danger"
              variant="outline"
              size="sm"
              class="btn-icon-only btn-rounded mb-0 me-3 d-flex align-items-center justify-content-center"
            >
              <i class="fas fa-arrow-down" aria-hidden="true"></i>
            </argon-button>
            <div class="d-flex flex-column">
              <h6 class="mb-1 text-dark text-sm">{{ type.label }}</h6>
              <span class="text-xs">{{ type.description }}</span>
            </div>
          </div>
          <div
            class="d-flex align-items-center text-danger text-gradient text-sm font-weight-bold"
          >
            Uses here?
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
