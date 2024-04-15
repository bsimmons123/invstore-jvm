<script setup>
import ArgonInput from "@/components/ArgonInput.vue";
import {ref} from "vue";
import {createCateringItemTypeAdapter} from "@/store/catering/services/CreateCateringItemTypeAdapter";

defineProps({
  title: {
    type: String,
    default: "Add Type",
  },
  loading: {
    type: Boolean,
    default: false,
  },
  errors: {
    type: Object,
    default: () => ({})
  },
});

const type = ref(createCateringItemTypeAdapter())
const emit = defineEmits(['create:submitForm'])

function updateValue(value, payload) {
  type.value[value] = payload
}
</script>

<template>
  <div class="modal fade" id="addCateringItemTypeModal" style="" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="staticBackdropLabel">{{ title }}</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form>
            <div class="row mb-3">
              <label for="label" class="form-control-label">Label</label>
              <argon-input
                  type="text"
                  :model-value="type.label"
                  @update:model-value="value => updateValue('label', value)"
              />
              <p class="error-message">{{ errors.Label }}</p>
            </div>
            <div class="row">
              <div class="col-md-12">
                <label for="example-text-input" class="form-control-label"
                >Description</label
                >
                <argon-input
                    type="text"
                    :model-value="type.description"
                    @update:model-value="value => updateValue('description', value)"
                />
                <p class="error-message">{{ errors.Description }}</p>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
              <button
                  type="submit"
                  class="btn btn-primary"
                  @click="emit('create:submitForm', type)"
                  :class="loading ? 'disabled' : ''"
              >Submit</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.error-message {
  color: #dc3545; /* Bootstrap's default alert color */
  font-size: 0.7em;
  font-weight: bold;
  margin-top: -10px;
}
</style>v