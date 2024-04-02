<script setup>
defineProps({
  checked: {
    type: Boolean,
    default: false,
  },
  dropdownItems: {
    type: Array,
    default: () => ([])
  },
  modelValue: {
    type: Object,
    default: () => ({}),
  }
});

// Emitting setup
const emit = defineEmits(['update:modelValue'])
</script>

<template>
  <div>
    <button class="btn btn-outline-primary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
      {{ modelValue.label }}
    </button>
    <ul class="dropdown-menu">
      <li v-for="(item) in dropdownItems" :key="item.label" @click="emit('update:modelValue', item)"><a class="dropdown-item">{{ item.label }}</a></li>
    </ul>
  </div>
</template>