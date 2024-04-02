<script setup>
defineProps({
  id: {
    type: String,
    required: true,
  },
  placeholder: {
    type: String,
    default: "",
  },
  rows: {
    type: Number,
    default: 5,
  },
});
</script>
<template>
  <div class="form-group">
    <label :for="id">
      <slot />
    </label>
    <textarea
      :id="id"
      class="form-control large-text"
      :rows="rows"
      :value="placeholder"
      readonly
    ></textarea>
  </div>
</template>

<style scoped>
.large-text {
  overflow: auto;
  line-height: 1.5;
  resize: none; /* Optional: Disable resizing */
}
</style>