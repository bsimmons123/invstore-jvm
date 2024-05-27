<script setup>
import EditCateringList from "@/views/components/EditCateringList.vue";
import {ref} from "vue";
import StoreIndex from "@/store/catering/_StoreIndex";
import {StoreActions} from "@/store/catering/actions";
import {useStore} from "vuex";

defineProps({
  title: {
    type: String,
    default: "Catering List",
  },
  list: {
    type: Array,
    default: () => ([]),
  },
  loading: {
    type: Boolean,
    default: false,
  },
  selected: {
    type: Object,
    default: () => ({}),
  },
  isExpanded: {
    type: Boolean,
    default: false,
  },
});

const selList = ref()
const selListIndex = ref()

const store = useStore();

const toggleEdit = function(list, index) {
  selList.value = list;
  selListIndex.value = index;
}

const updateList = (list) => store.dispatch(`${StoreIndex.storeName}/${StoreActions.updateList}`, list)

const emit = defineEmits(['show:creatList'])
</script>

<template>
  <div class="card">
    <div class="card-header pb-0 d-flex justify-content-between align-items-center">
      <h6>{{ title }}</h6>
      <button class="btn btn-primary"
              @click.stop.prevent="emit('show:creatList')"
              data-bs-toggle="collapse"
              data-bs-target="#collapseCreateList"
              :aria-expanded="!isExpanded.toString()"
              aria-controls="collapseCreateList"
      >Create</button>
    </div>
    <div class="card-body px-0 pt-0 pb-2">
      <div class="table-responsive p-0">
        <table class="table align-items-center mb-0">
          <thead>
          <tr>
            <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
            >
              Label
            </th>
            <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
            >
              Created By
            </th>
            <th
                class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
            >
              Status
            </th>
            <th
                class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
            >
              Created At
            </th>
            <th class="text-secondary opacity-7"></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(value, index) in list" :key="index">
            <td>
              <div class="d-flex px-2 py-1">
                <div>
                  <img :src="`/api/v1/image/list/${value.id}`" class="avatar avatar-sm me-3" alt="user2" />
                </div>
                <div class="d-flex flex-column justify-content-center">
                  <h6 class="mb-0 text-sm">{{ value.label }}</h6>
                  <p class="text-xs text-secondary mb-0">{{ value.description }}</p>
                </div>
              </div>
            </td>
            <td>
              <p class="text-xs font-weight-bold mb-0">{{ value.user.name }}</p>
              <p class="text-xs text-secondary mb-0">{{ value.user.email }}</p>
            </td>
            <td class="align-middle text-center text-sm">
              <span class="badge badge-sm" :class="value.isActive ? 'bg-gradient-primary' : 'bg-gradient-secondary'">{{ value.isActive ? 'Active' : 'Inactive' }}</span>
            </td>
            <td class="align-middle text-center">
              <span class="text-secondary text-xs font-weight-bold">{{ value.createdAt }}</span>
            </td>
            <td class="align-middle">
              <a href="javascript:;"
                 class="text-secondary font-weight-bold text-xs"
                 data-toggle="tooltip"
                 data-original-title="Edit list"
                 data-bs-toggle="modal"
                 data-bs-target="#editCateringList"
                 @click="toggleEdit(value, index)"
              >
                Edit
              </a>
            </td>
            <td class="align-middle">
                <router-link
                    class="text-secondary font-weight-bold text-xs"
                    data-toggle="tooltip"
                    data-original-title="Edit user"
                    :to="{ name: 'CateringList', params: { sessionId: value.sessionId } }"
                >
                  View
                </router-link>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <edit-catering-list
      :model="selList"
      :model-index="selListIndex"
      @update:submit-form="updateList"
    />
  </div>
</template>
