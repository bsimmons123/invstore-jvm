import { StoreState } from './state';

export const StoreMutations = {
  SET_USER: 'SET_USER',
  SET_LOADING_USER: 'SET_LOADING_USER'
};

export default {
  SET_USER(state, payload) {
    state[StoreState.user] = payload
  },
  SET_LOADING_USER(state, payload) {
    state[StoreState.loadingUser] = payload
  }
};
