<script setup>
import ArgonButton from "@/components/ArgonButton.vue";
import CreateInvite from "@/views/components/CreateInvite.vue";
import StoreIndex from "@/store/catering/_StoreIndex";
import {StoreActions} from "@/store/catering/actions";
import {useStore} from "vuex";

defineProps({
  title: {
    type: String,
    default: "Invite List",
  },
  loading: {
    type: Boolean,
    default: false,
  },
  invites: {
    type: Array,
    default: (() => [])
  },
});

const store = useStore();

const createInv = (user) => {
  store.dispatch(`${StoreIndex.storeName}/${StoreActions.createInvite}`, user)
}
</script>
<template>
  <div class="card pb-4">
    <div class="card-header pb-0 p-3">
      <div class="row">
        <div class="col-6 d-flex align-items-center">
          <h6 class="mb-0">Invited Users</h6>
        </div>
        <div class="col-6 text-end">
          <argon-button
              color="success"
              size="sm"
              variant="outline"
              data-bs-toggle="modal"
              data-bs-target="#createInvite"
          >
            Invite
          </argon-button>
        </div>
      </div>
    </div>
    <template  v-if="loading" >
      <div v-for="n in 5" :key="n" class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg">
        <!-- Placeholder skeleton for while data is loading -->
        <div class="skeleton-box" style="width: 100px; height: 20px;"></div>
        <!-- You can add more skeleton elements here as necessary -->
      </div>
    </template>
    <div class="card-body p-3 pb-0 mb-0" v-else>
      <ul class="list-group">
        <li
            class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg"
            v-for="inv in invites"
            :key="inv.id"
        >
          <div class="d-flex flex-column">
            <h6 class="mb-1 text-dark font-weight-bold text-sm">
              {{ inv.userEmail }}
            </h6>
            <span class="text-xs">By: {{ inv.whoInvited.username }}</span>
          </div>
          <div class="d-flex align-items-center text-sm">
            <span class="badge badge-sm" :class="inv.accepted ? 'bg-gradient-primary' : 'bg-gradient-secondary'">{{ inv.accepted ? 'Accepted' : 'Pending' }}</span>
          </div>
        </li>
      </ul>
    </div>
    <create-invite
      @create:submit-form="createInv"
    />
  </div>
</template>
