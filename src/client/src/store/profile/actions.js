import Helpers from './helpers';
import { StoreMutations } from './mutations';
import axios from "@/api/axios";

export const StoreActions = {
  getUser: 'getUser',
};

export default {
    getUser({ commit }) {
        commit(StoreMutations.SET_LOADING_USER, true)
        axios.get(Helpers.paths.user())
          .then((res) => {
              commit(StoreMutations.SET_USER, res.data.value)
              commit(StoreMutations.SET_LOADING_USER, false)
          })
          .catch(() => {
              commit(StoreMutations.SET_LOADING_USER, false)
          }
      );
  },
};
