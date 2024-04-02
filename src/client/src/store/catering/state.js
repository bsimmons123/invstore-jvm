import {createCateringListAdapter} from "@/store/catering/CreateCateringListAdapter";

export const StoreState = {
  list: 'list',
  loadingLists: 'loadingLists',
  createListLoading: 'createListLoading',
  createErrors: 'createErrors',
  createListAdapter: 'createListAdapter',
  createListOption: 'createListOption',

  selList: 'selList',
  items: 'items',
  loadingItems: 'loadingLists',
  createItemLoading: 'createListLoading',
  createItemErrors: 'createErrors',
  createItemAdapter: 'createListAdapter',
  createItemOption: 'createListOption',
};

export default {
  list: [],
  loadingLists: false,
  createListLoading: false,
  createErrors: [],
  createListAdapter: createCateringListAdapter(),
  createListOption: false,

  items: [],
  loadingItems: false,
  createItemLoading: false,
  createItemErrors: [],
  createItemAdapter: createCateringListAdapter(),
  createItemOption: false,
  selList: {}
};
