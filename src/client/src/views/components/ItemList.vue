<script setup>
import ArgonButton from "@/components/ArgonButton.vue";
import {createCateringItemAdapter} from "@/store/catering/services/CreateCateringItemAdapter";

defineProps({
  title: {
    type: String,
    default: "Item List",
  },
  loading: {
    type: Boolean,
    default: false,
  },
  items: {
    type: Object,
    default: createCateringItemAdapter()
  },
});
</script>

<template>
  <div class="card">
    <div class="card-header pb-0 p-3">
      <div class="row">
        <div class="col-6 d-flex align-items-center">
          <h6 class="mb-0">Items</h6>
        </div>
        <div class="col-6 text-end">
          <argon-button color="dark" variant="gradient" data-bs-toggle="modal" data-bs-target="#addCateringItemModal">
            <i class="fas fa-plus me-2"></i>
            Add Item
          </argon-button>
        </div>
      </div>
    </div>
<!--    If items are loading show empty pills of data-->
    <template  v-if="loading" >
      <div v-for="n in 5" :key="n" class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg">
        <!-- Placeholder skeleton for while data is loading -->
        <div class="skeleton-box" style="width: 100px; height: 20px;"></div>
        <!-- You can add more skeleton elements here as necessary -->
      </div>
    </template>
    <div class="card-body pt-4 p-3" v-else>
      <ul class="list-group">
        <li
            class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg"
            v-for="item in items"
            :key="item.id"
        >
          <div class="d-flex flex-column">
            <h6 class="mb-3 text-sm">{{ item.label }}</h6>
            <span class="mb-2 text-xs">
              Description:
              <span class="text-dark font-weight-bold ms-sm-2"
              >{{ item.description }}</span
              >
            </span>
            <span class="mb-2 text-xs">
              Type:
              <span class="text-dark font-weight-bold ms-sm-2"
              >{{ item.type.label }}</span
              >
            </span>
          </div>
          <div class="ms-auto text-end">
            <a
                class="btn btn-link text-danger text-gradient px-3 mb-0"
                href="javascript:;"
            >
              <i class="far fa-trash-alt me-2" aria-hidden="true"></i>Delete
            </a>
            <a class="btn btn-link text-dark px-3 mb-0" href="javascript:;">
              <i class="fas fa-pencil-alt text-dark me-2" aria-hidden="true"></i
              >Edit
            </a>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
