import { StoreMutations } from './mutations';
import useApi from "@/api/axios";
import ProfileHelpers from "@/store/profile/profileHelpers";

export const StoreActions = {
  getUser: 'getUser',
};

export default {
    getUser({ commit }) {
        commit(StoreMutations.SET_LOADING_USER, true)
        const api = useApi();

        return api.get(ProfileHelpers.paths.user())
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
