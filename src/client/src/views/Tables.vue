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
import CreateCateringList from "@/views/components/CreateCateringList.vue";
import {StoreMutations} from "@/store/catering/mutations";


const store = useStore();

const cateringListState = store.state.cateringList;
const profileState = store.state.profile;

const list = computed(() => cateringListState[StoreState.list]);
const createListAdapter = computed(() => cateringListState[StoreState.createListAdapter]);
const createListLoading = computed(() => cateringListState[StoreState.createListLoading]);
const createErrors = computed(() => cateringListState[StoreState.createErrors]);
const loadingLists = computed(() => cateringListState[StoreState.loadingLists]);
const showCreateList = computed(() => cateringListState[StoreState.createListOption]);
const user = computed(() => profileState[ProfileStoreState.user]);

const getLists = () => store.dispatch(`${StoreIndex.storeName}/${StoreActions.getLists}`);
const getUserInfo = () => store.dispatch(`${ProfileStoreIndex.storeName}/${ProfileStoreActions.getUser}`);
const createList = (list) => store.dispatch(`${StoreIndex.storeName}/${StoreActions.createList}`, list);
const updateCreateListOption = () => store.commit(`${StoreIndex.storeName}/${StoreMutations.FLIP_CREATE_LIST_OPTION}`);

function updateCreateList(value, payload) {
  store.commit(`${StoreIndex.storeName}/${StoreMutations.UPDATE_CREATE_LIST_ADAPTER}`, {key: value, value: payload})
}

onMounted(() => {
  getLists()
  getUserInfo()
});
</script>
<template>
  <div class="py-4 container-fluid">
    <div class="row">
      <div :class="showCreateList ? 'col-lg-8' : 'col-lg-12'">
        <catering-list-table
            :list="list"
            :title="`${user.username}'s Catering Lists`"
            :loading="loadingLists"
            :is-expanded="!showCreateList"
            @show:creat-list="() => updateCreateListOption()"
        />
      </div>
      <div class="col-lg-4">
        <div class="collapse" id="collapseCreateList">
          <div class="card card-body">
            <create-catering-list
                :list-adapter="createListAdapter"
                :loading="createListLoading"
                :errors="createErrors"
                @update:update-create-adapter="updateCreateList"
                @create:submit-form="createList"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="mt-4 row">
      <div class="col-12">
        <projects-table />
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>