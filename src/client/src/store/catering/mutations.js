import { StoreState } from './state';

export const StoreMutations = {
  SET_LIST: 'SET_LIST',
  SET_LOADING_LIST: 'SET_LOADING_LIST'
};

export default {
  SET_LIST(state, payload) {
    state[StoreState.list] = payload
  },
  SET_LOADING_LIST(state, payload) {
    state[StoreState.loadingLists] = payload
  }
};
