import { StoreState } from './state';

export const StoreMutations = {
  SET_LIST: 'SET_LIST',
  SET_LOADING_LIST: 'SET_LOADING_LIST',
  SET_CREATE_LIST_LOADING: 'SET_CREATE_LIST_LOADING',
  SET_CREATE_ERRORS: 'SET_CREATE_ERRORS',
  SET_CREATE_LIST_ADAPTER: 'SET_CREATE_LIST_ADAPTER',
  UPDATE_CREATE_LIST_ADAPTER: 'UPDATE_CREATE_LIST_ADAPTER',
  FLIP_CREATE_LIST_OPTION: 'FLIP_CREATE_LIST_OPTION',

  SET_ITEMS: 'SET_ITEMS',
  ADD_ITEM: 'ADD_ITEM',
  SET_LOADING_ITEM: 'SET_LOADING_ITEM',
  SET_CREATE_ITEM_LOADING: 'SET_CREATE_ITEM_LOADING',
  SET_CREATE_ITEM_ERRORS: 'SET_CREATE_ITEM_ERRORS',
  SET_CREATE_ITEM_ADAPTER: 'SET_CREATE_ITEM_ADAPTER',
  UPDATE_CREATE_ITEM_ADAPTER: 'UPDATE_CREATE_ITEM_ADAPTER',
  FLIP_CREATE_ITEM_OPTION: 'FLIP_CREATE_ITEM_OPTION',
  SET_SEL_LIST: 'SET_SEL_LIST',

  SET_TYPES: 'SET_TYPES',
  ADD_TYPE: 'ADD_TYPE',
  SET_LOADING_TYPE: 'SET_LOADING_TYPE',
  SET_CREATE_TYPE_LOADING: 'SET_CREATE_TYPE_LOADING',
  SET_CREATE_TYPE_ERRORS: 'SET_CREATE_TYPE_ERRORS',
  SET_CREATE_TYPE_ADAPTER: 'SET_CREATE_TYPE_ADAPTER',
  UPDATE_CREATE_TYPE_ADAPTER: 'UPDATE_CREATE_TYPE_ADAPTER',
};

export default {
  SET_LIST(state, payload) {
    state[StoreState.list] = payload;
  },
  SET_LOADING_LIST(state, payload) {
    state[StoreState.loadingLists] = payload;
  },
  SET_CREATE_LIST_LOADING(state, payload) {
    state[StoreState.createListLoading] = payload;
  },
  SET_CREATE_ERRORS(state, payload) {
    state[StoreState.createErrors] = payload;
  },
  SET_CREATE_LIST_ADAPTER(state, payload) {
    state[StoreState.createListAdapter] = payload;
  },
  UPDATE_CREATE_LIST_ADAPTER(state, payload) {
    console.log(payload)
    state[StoreState.createListAdapter][payload.key] = payload.value;
  },
  FLIP_CREATE_LIST_OPTION(state) {
    state[StoreState.createListOption] = !state[StoreState.createListOption];
  },

  SET_LOADING_ITEM(state, payload) {
    state[StoreState.loadingItems] = payload;
  },
  SET_CREATE_ITEM_LOADING(state, payload) {
    state[StoreState.createItemLoading] = payload;
  },
  SET_CREATE_ITEM_ERRORS(state, payload) {
    state[StoreState.createItemErrors] = payload;
  },
  SET_CREATE_ITEM_ADAPTER(state, payload) {
    state[StoreState.createItemAdapter] = payload;
  },
  UPDATE_CREATE_ITEM_ADAPTER(state, payload) {
    state[StoreState.createItemAdapter][payload.key] = payload.value;
  },
  FLIP_CREATE_ITEM_OPTION(state) {
    state[StoreState.createItemOption] = !state[StoreState.createListOption];
  },
  SET_ITEMS(state, payload) {
    state[StoreState.items] = payload;
  },
  ADD_ITEM(state, payload) {
    state[StoreState.items].push(payload)
  },
  SET_SEL_LIST(state, payload) {
    state[StoreState.selList] = payload;
  },

  SET_LOADING_TYPE(state, payload) {
    state[StoreState.loadingTypes] = payload;
  },
  SET_CREATE_TYPE_LOADING(state, payload) {
    state[StoreState.createTypeLoading] = payload;
  },
  SET_CREATE_TYPE_ERRORS(state, payload) {
    state[StoreState.createTypeErrors] = payload;
  },
  SET_CREATE_TYPE_ADAPTER(state, payload) {
    state[StoreState.createTypeAdapter] = payload;
  },
  UPDATE_CREATE_TYPE_ADAPTER(state, payload) {
    state[StoreState.createTypeAdapter][payload.key] = payload.value;
  },
  SET_TYPES(state, payload) {
    state[StoreState.types] = payload;
  },
  ADD_TYPE(state, payload) {
    state[StoreState.types].push(payload)
  },
};
