<script setup>
import ProjectsTable from "./components/ProjectsTable.vue";
import CateringListTable from "@/views/components/CateringListTable.vue";
import {useStore} from "vuex";
import {computed, onMounted} from "vue";
import StoreIndex from "@/store/catering/_StoreIndex";
import ProfileStoreIndex from "@/store/profile/_StoreIndex";
import {StoreActions} from "@/store/catering/actions";
import {StoreActions as ProfileStoreActions} from "@/store/profile/actions";
import {StoreState} from "@/store/catering/state";
import {StoreState as ProfileStoreState} from "@/store/profile/state";


const store = useStore();

const cateringListState = store.state.cateringList;
const profileState = store.state.profile;

const list = computed(() => cateringListState[StoreState.list]);
const loadingLists = computed(() => cateringListState[StoreState.loadingLists]);
const user = computed(() => profileState[ProfileStoreState.user]);

const getLists = () => store.dispatch(`${StoreIndex.storeName}/${StoreActions.getLists}`);
const getUserInfo = () => store.dispatch(`${ProfileStoreIndex.storeName}/${ProfileStoreActions.getUser}`);

onMounted(() => {
  getLists()
  getUserInfo()
});
</script>
<template>
  <div class="py-4 container-fluid">
    <div class="row">
      <div class="col-12">
        <catering-list-table
            :list="list"
            :title="`${user.username}'s Catering Lists`"
            :loading="loadingLists"
        />
      </div>
    </div>
    <div class="mt-4 row">
      <div class="col-12">
        <projects-table />
      </div>
    </div>
  </div>
</template>
