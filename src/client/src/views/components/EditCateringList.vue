<script setup>
import ArgonInput from "@/components/ArgonInput.vue";
import {ref, watchEffect} from "vue";
import {createCateringListAdapter} from "@/store/catering/services/CreateCateringListAdapter";

const props = defineProps({
  title: {
    type: String,
    default: "Update list",
  },
  model: {
    type: Object,
    default: () => ({})
  },
  modelIndex: {
    type: Number
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

const list = ref(createCateringListAdapter())
const emit = defineEmits(['update:submitForm'])

function updateValue(value, payload) {
  list.value[value] = payload
}

watchEffect(() => {
  if (props.model) {
    list.value = props.model;
  }
});
</script>

<template>
  <div class="modal fade" id="editCateringList" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
                  :model-value="list.label"
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
                    :model-value="list.description"
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
                  @click="emit('update:submitForm', {list: list, index: modelIndex})"
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