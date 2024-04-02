<script setup>
import ArgonInput from "@/components/ArgonInput.vue";
import ArgonSwitch from "@/components/ArgonSwitch.vue";
import {createCateringListAdapter} from "@/store/catering/CreateCateringListAdapter";
import ArgonButton from "@/components/ArgonButton.vue";
import ArgonDropdown from "@/components/ArgonDropdown.vue";
import {Visibility} from "@/store/catering/Visibility";

defineProps({
  title: {
    type: String,
    default: "Create Catering List",
  },
  loading: {
    type: Boolean,
    default: false,
  },
  errors: {
    type: Object,
    default: () => ({})
  },
  list: {
    type: Object,
    default: createCateringListAdapter()
  },
});

const emit = defineEmits(['create:submitForm', 'update:updateCreateAdapter'])

function updateVisibility(newValue) {
  emit('update:updateCreateAdapter', 'visibility', newValue)
}

function updateValue(value, payload) {
  emit('update:updateCreateAdapter', value, payload)
}
</script>

<template>
<div>
  <div class="row">
    <div>
      <div class="card">
        <div class="card-header pb-0">
          <div class="d-flex align-items-center">
            <p class="mb-0">{{ title }}</p>
          </div>
        </div>
        <div class="card-body">
          <div class="row">
            <div class="col-md-6">
              <label for="validationDefault01" class="form-control-label form-label"
                >Label
              </label>
              <argon-input
                  type="text"
                  id="validationDefault01"
                  :value="list.label"
                  @update:model-value="value => updateValue('label', value)"
              />
              <p class="error-message">{{ errors.Label }}</p>
            </div>
            <div class="col-md-6">
              <label for="example-text-input" class="form-control-label"
              >Visibility</label
              >
              <argon-dropdown
                  :model-value="list.visibility"
                  :dropdown-items="Visibility"
                  @update:model-value="updateVisibility"
              />
              <p class="error-message">{{ errors.Visibility }}</p>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12">
              <label for="example-text-input" class="form-control-label"
                >Description</label
              >
              <argon-input
                type="text"
                :value="list.description"
                @update:model-value="value => updateValue('description', value)"
              />
              <p class="error-message">{{ errors.Description }}</p>
            </div>
            <div class="col-md-4">
              <label for="example-text-input" class="form-control-label"
                >Max Users</label
              >
              <argon-input
                  type="number"
                  :model-value="list.maxUsers"
                  @update:model-value="value => updateValue('maxUsers', value)"
              />
              <p class="error-message">{{ errors.MaxUsers }}</p>
            </div>
            <div class="col-md-4">
              <label for="example-text-input" class="form-control-label"
                >Max Items</label
              >
              <argon-input
                  type="number"
                  :model-value="list.itemLimit"
                  @update:model-value="value => updateValue('itemLimit', value)"
              />
              <p class="error-message">{{ errors.ItemLimit }}</p>
            </div>
            <div class="col-md-4">
              <label for="example-text-input" class="form-control-label"
                >Active</label
              >
              <argon-switch
                  type="text"
                  checked
                  :disabled="true"
                  id="active"
                  name="Active"
              />
            </div>
          </div>
          <hr class="horizontal dark" />
          <p class="text-uppercase text-sm">Notes</p>
          <div class="row">
            <div class="col-md-12">
              <argon-input
                type="text"
                :value="list.notes"
                @update:model-value="value => updateValue('notes', value)"
              />
              <p class="error-message">{{ errors.Notes }}</p>
            </div>
          </div>
          <div class="row">
            <argon-button
                color="success"
                size="sm"
                class="ms-auto"
                @click="emit('create:submitForm', list)"
              >
              <div class="spinner-grow" role="status" v-if="loading">
                <span class="visually-hidden">Loading...</span>
              </div>
              <template v-else>
                Create
              </template>
            </argon-button>
          </div>
        </div>
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
</style>