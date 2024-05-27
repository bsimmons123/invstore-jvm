<script setup>
import ArgonInput from "@/components/ArgonInput.vue";
import {ref} from "vue";
import {createInviteAdapter} from "@/store/catering/services/CreateInviteAdapter";

defineProps({
  title: {
    type: String,
    default: "Invite User",
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

const item = ref(createInviteAdapter())
const emit = defineEmits(['create:submitForm'])

function updateValue(value, payload) {
  item.value[value] = payload
}
</script>

<template>
  <div class="modal fade" id="createInvite" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
                  :model-value="item.userEmail"
                  @update:model-value="value => updateValue('userEmail', value)"
              />
              <p class="error-message">{{ errors.userEmail }}</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
              <button
                  type="submit"
                  class="btn btn-primary"
                  @click="emit('create:submitForm', item)"
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