import { StoreState } from './state';

export const StoreMutations = {
  SET_LIST: 'SET_LIST',
  SET_LOADING_LIST: 'SET_LOADING_LIST',
  SET_CREATE_LIST_LOADING: 'SET_CREATE_LIST_LOADING',
  SET_CREATE_ERRORS: 'SET_CREATE_ERRORS',
  SET_CREATE_LIST_ADAPTER: 'SET_CREATE_LIST_ADAPTER',
  UPDATE_CREATE_LIST_ADAPTER: 'UPDATE_CREATE_LIST_ADAPTER',
  FLIP_CREATE_LIST_OPTION: 'FLIP_CREATE_LIST_OPTION'
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
  }
};
