import { StoreMutations } from './mutations';
import useApi from "@/api/axios";
import CateringListHelpers from "@/store/catering/helpers";

export const StoreActions = {
  getLists: 'getLists',
};

export default {
    getLists({ commit }) {
        commit(StoreMutations.SET_LOADING_LIST, true)
        const api = useApi();

        api.get(`${CateringListHelpers.paths.cateringListByUser()}`)
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
