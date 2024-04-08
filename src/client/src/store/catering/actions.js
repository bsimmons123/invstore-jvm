import { StoreMutations } from './mutations';
import useApi from "@/api/axios";
import CateringListHelpers from "@/store/catering/helpers";
import {StoreState} from "@/store/catering/state";
import {createCateringListAdapter} from "@/store/catering/CreateCateringListAdapter";

export const StoreActions = {
  getLists: 'getLists',
  createList: 'createList',
  getList: 'getList',
  getItems: 'getItems'
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
    createList({ commit, state }) {
        commit(StoreMutations.SET_CREATE_LIST_LOADING, true)
        const api = useApi();
        const newValue = JSON.parse(JSON.stringify(state[StoreState.createListAdapter]));
        newValue.visibility = state[StoreState.createListAdapter].visibility.value
        commit(StoreMutations.SET_CREATE_ERRORS, {})
        api.post(`${CateringListHelpers.paths.createCateringList()}`, newValue)
            .then((res) => {
                commit(StoreMutations.SET_CREATE_LIST_LOADING, false)
                if (!res.data.success) {
                    commit(StoreMutations.SET_CREATE_ERRORS, res.data.errors)
                } else {
                    const newList = [...state[StoreState.list] ]
                    newList.push(res.data.value)
                    commit(StoreMutations.SET_LIST, newList);
                    commit(StoreMutations.SET_CREATE_LIST_ADAPTER, createCateringListAdapter())
                }
            })
            .catch((error) => {
                    console.log(error)
                    commit(StoreMutations.SET_CREATE_LIST_LOADING, false)
                }
            );
    },
    getList({ commit }, sessionId) {
        commit(StoreMutations.SET_LOADING_ITEM, true)
        const api = useApi();

        return api.get(`${CateringListHelpers.paths.getCateringListBySessionId()}${sessionId}`)
            .then((res) => {
                commit(StoreMutations.SET_SEL_LIST, res.data.value)
                commit(StoreMutations.SET_LOADING_ITEM, false)
            })
            .catch((error) => {
                    console.log(error)
                    commit(StoreMutations.SET_LOADING_ITEM, false)
                }
            );
    },
    getItems({ commit, state }) {
        commit(StoreMutations.SET_LOADING_ITEM, true)
        const api = useApi();

        api.get(`${CateringListHelpers.paths.getCateringListById()}${state[StoreState.selList].id}`)
            .then((res) => {
                commit(StoreMutations.SET_ITEMS, res.data.value)
                commit(StoreMutations.SET_LOADING_ITEM, false)
            })
            .catch((error) => {
                    console.log(error)
                    commit(StoreMutations.SET_LOADING_ITEM, false)
                }
            );
    }
};
