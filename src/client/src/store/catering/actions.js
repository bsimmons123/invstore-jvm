import { StoreMutations } from './mutations';
import useApi from "@/api/axios";
import CateringListHelpers from "@/store/catering/services/helpers";
import {StoreState} from "@/store/catering/state";
import {createCateringListAdapter} from "@/store/catering/services/CreateCateringListAdapter";

export const StoreActions = {
  getLists: 'getLists',
  createList: 'createList',
  getList: 'getList',
  getItems: 'getItems',
  createItem: 'createItem',
  createType: 'createType',
  getTypes: 'getTypes',
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
    },
    createItem({ commit, state }, item) {
        commit(StoreMutations.SET_CREATE_ITEM_LOADING, true)
        const api = useApi();

        console.log(item)
        item.listId = state[StoreState.selList].id
        item.status = item.status.value

        api.post(`${CateringListHelpers.paths.createCateringItem()}`, item)
            .then((res) => {
                if (!res.data.success) {
                    commit(StoreMutations.SET_CREATE_ITEM_ERRORS, res.data.errors)
                } else {
                    commit(StoreMutations.ADD_ITEM, res.data.value)
                }
                commit(StoreMutations.SET_CREATE_ITEM_LOADING, false)
            })
            .catch((error) => {
                    console.log(error)
                    commit(StoreMutations.SET_CREATE_ITEM_LOADING, false)
                }
            );
    },
    createType({ commit, state }, type) {
        commit(StoreMutations.SET_CREATE_TYPE_LOADING, true)
        const api = useApi();

        type.listId = state[StoreState.selList].id

        api.post(`${CateringListHelpers.paths.createCateringItemType()}`, type)
            .then((res) => {
                if (!res.data.success) {
                    commit(StoreMutations.SET_CREATE_TYPE_ERRORS, res.data.errors)
                } else {
                    commit(StoreMutations.ADD_TYPE, res.data.value)
                }
                commit(StoreMutations.SET_CREATE_TYPE_LOADING, false)
            })
            .catch((error) => {
                    console.log(error)
                    commit(StoreMutations.SET_CREATE_TYPE_LOADING, false)
                }
            );
    },
    getTypes({ commit, state }) {
        commit(StoreMutations.SET_LOADING_TYPE, true)
        const api = useApi();

        api.get(`${CateringListHelpers.paths.cateringItemType()}${state[StoreState.selList].id}`)
            .then((res) => {
                commit(StoreMutations.SET_TYPES, res.data.value)
                commit(StoreMutations.SET_LOADING_TYPE, false)
            })
            .catch((error) => {
                    console.log(error)
                    commit(StoreMutations.SET_LOADING_TYPE, false)
                }
            );
    },
};
