import Helpers from './helpers';
import { StoreMutations } from './mutations';
import axios from "@/api/axios";

export const StoreActions = {
  getLists: 'getLists',
};

export default {
    getLists({ commit }) {
        commit(StoreMutations.SET_LOADING_LIST, true)
        axios.get(`${Helpers.paths.cateringListByUser()}`)
          .then((res) => {
              commit(StoreMutations.SET_LIST, res.data.value)
              commit(StoreMutations.SET_LOADING_LIST, false)
          })
          .catch((error) => {
              console.log(error)
              commit(StoreMutations.SET_LOADING_LIST, false)
          }
      );
  },
};
