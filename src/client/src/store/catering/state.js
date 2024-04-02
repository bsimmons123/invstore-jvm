import {createCateringListAdapter} from "@/store/catering/CreateCateringListAdapter";

export const StoreState = {
  list: 'list',
  loadingLists: 'loadingLists',
  createListLoading: 'createListLoading',
  createErrors: 'createErrors',
  createListAdapter: 'createListAdapter',
  createListOption: 'createListOption'
};

export default {
  list: [],
  loadingLists: false,
  createListLoading: false,
  createErrors: [],
  createListAdapter: createCateringListAdapter(),
  createListOption: false
};
