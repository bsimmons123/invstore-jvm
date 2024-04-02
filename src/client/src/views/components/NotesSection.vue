<script setup>
import ArgonTextarea from "@/components/ArgonTextarea.vue";

defineProps({
  notes: {
    type: String,
    default: "",
  }
})
</script>
<template>
  <div class="card mt-4">
    <div class="card-header pb-0 p-3">
      <div class="row">
        <div class="col-6 d-flex align-items-center">
          <h6 class="mb-0">Notes</h6>
        </div>
      </div>
    </div>
    <div class="card-body p-3">
      <div class="row">
        <div class="col-md-12 mb-md-0 mb-4">
          <argon-textarea id="notes-section" :placeholder="notes"/>
        </div>
      </div>
    </div>
  </div>
</template>
