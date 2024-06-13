import {StoreState} from "@/store/catering/state";

export const StoreGetters = {
    usersLists: 'usersLists',
    invitedLists: 'invitedLists'
};
export default {
    [StoreGetters.usersLists]: (state) => (userId) => {
        return state[StoreState.list].filter(list => list.user.id === userId);
    },
    [StoreGetters.invitedLists]: (state) => (userId) => {
        return state[StoreState.list].filter(list => list.user.id !== userId);
    }
};
