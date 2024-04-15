<script setup>
import DefaultInfoCard from "@/examples/Cards/DefaultInfoCard.vue";
import ItemTypeList from "./components/ItemTypeList.vue";
import {useStore} from "vuex";
import {computed, onMounted} from "vue";
import {StoreState} from "@/store/catering/state";
import StoreIndex from "@/store/catering/_StoreIndex";
import {StoreActions} from "@/store/catering/actions";
import {useRoute} from "vue-router";
import InviteList from "@/views/components/InviteList.vue";
import ItemList from "@/views/components/ItemList.vue";
import NotesSection from "@/views/components/NotesSection.vue";
import AddCateringItem from "@/views/components/AddCateringItem.vue";
import AddCateringItemType from "@/views/components/AddCateringItemType.vue";

const store = useStore();
const route = useRoute();

const cateringListState = store.state.cateringList;

const list = computed(() => cateringListState[StoreState.selList]);
const createItemErrors = computed(() => cateringListState[StoreState.createItemErrors]);
const createItemLoading = computed(() => cateringListState[StoreState.createItemLoading]);
const items = computed(() => cateringListState[StoreState.items]);
const itemsLoading = computed(() => cateringListState[StoreState.loadingItems]);
const types = computed(() => cateringListState[StoreState.types]);
const typesLoading = computed(() => cateringListState[StoreState.loadingTypes]);
const createItemTypeErrors = computed(() => cateringListState[StoreState.createTypeErrors]);
const createItemTypeLoading = computed(() => cateringListState[StoreState.createTypeLoading]);

const getList = (id) => store.dispatch(`${StoreIndex.storeName}/${StoreActions.getList}`, id);
const getItems = () => store.dispatch(`${StoreIndex.storeName}/${StoreActions.getItems}`);
const getTypes = () => store.dispatch(`${StoreIndex.storeName}/${StoreActions.getTypes}`);
const createItem = (item) => store.dispatch(`${StoreIndex.storeName}/${StoreActions.createItem}`, item)
const createItemType = (item) => store.dispatch(`${StoreIndex.storeName}/${StoreActions.createType}`, item)

onMounted(() => {
  getList(route.params.sessionId).then(() => {
    getItems()
    getTypes()
  })
});
</script>
<template>
  <div class="container-fluid">
    <div class="row">
      <div class="col-lg-8">
        <div class="row mt-4">
          <div class="col-xl-6 mb-xl-0 mb-4">
            <default-info-card
                :icon="{
                    component: 'fas fa-landmark',
                    background: 'bg-gradient-success'
                  }"
                :title="list.label"
                :description="list.description"
                value="+$2000"
            />
          </div>
          <div class="col-xl-6">
            <div class="row">
              <div class="col-md-6">
                <default-info-card
                    :icon="{
                    component: 'fas fa-landmark',
                    background: 'bg-gradient-success'
                  }"
                    title="Max Users"
                    description="Max users able to join list"
                    :value="list.maxUsers"
                />
              </div>
              <div class="col-md-6">
                <default-info-card
                    :icon="{
                    component: 'fab fa-paypal',
                    background: 'bg-gradient-success'
                  }"
                    title="Item Limit"
                    description="Max amount of items for this list"
                    :value="list.itemLimit"
                />
              </div>
            </div>
          </div>
          <div class="col-md-12 mb-4">
            <notes-section
              :notes="list.notes"
            />
          </div>
        </div>
      </div>
      <div class="col-lg-4">
        <invite-list class="mt-4" />
      </div>
    </div>
    <div class="row">
      <div class="col-md-7">
        <item-list
          :items="items"
          :loading="itemsLoading"
          title="Items"
        />
        <add-catering-item
          @create:submit-form="createItem"
          :errors="createItemErrors"
          :loading="createItemLoading"
          :types="types"
        />
      </div>
      <div class="col-md-5">
        <item-type-list
          :types="types"
          :loading="typesLoading"
        />
        <add-catering-item-type
            @create:submit-form="createItemType"
            :errors="createItemTypeErrors"
            :loading="createItemTypeLoading"
        />
      </div>
    </div>
  </div>
</template>
