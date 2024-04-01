import { StoreMutations } from './mutations';
import router from "../../router";
import MessageTypes from "@/store/global-helpers/MessageTypes";
import RouterList from "@/store/global-helpers/routerList";
import useApi from "@/api/axios";
import LoginHelpers from "@/store/login/helpers";

export const StoreActions = {
  login: 'login',
  check_login: 'check_login',
  logout: 'logout',
  register: 'register'
};

export default {
  login({ commit, state }) {
    if (state.user.password === '' || state.user.email === '') {
        commit(StoreMutations.SET_MESSAGE, 'Please enter email and password');
        commit(StoreMutations.TOGGLE_SHOW_MESSAGE, true);
        commit(StoreMutations.SET_ALERT_COUNTDOWN, 5);
        commit(StoreMutations.SET_MESSAGE_TYPE, MessageTypes.warning);
        return
    }
    const api = useApi();

    api.post(LoginHelpers.paths.login(), state.user)
      .then((res) => {
        localStorage.setItem('jwt', res.data.jwtToken);
        commit(StoreMutations.SET_LOGGED_IN, true);
        commit(StoreMutations.SET_MESSAGE, res.data.message);
        commit(StoreMutations.TOGGLE_SHOW_MESSAGE, true);
        commit(StoreMutations.SET_ALERT_COUNTDOWN, 5);
        commit(StoreMutations.SET_MESSAGE_TYPE, MessageTypes.success);
        router.push({ name: RouterList.routes.dashboard.name })
      })
      .catch((error) => {
          console.log(error)
        if (error.response && error.response.status === 401) {
          // Unauthorized error
          commit(StoreMutations.SET_MESSAGE, 'Invalid credentials. Please try again.');
          commit(StoreMutations.TOGGLE_SHOW_MESSAGE, true);
          commit(StoreMutations.SET_ALERT_COUNTDOWN, 5);
          commit(StoreMutations.SET_MESSAGE_TYPE, MessageTypes.warning);
        } else {
          // Other errors
          console.error(error);
        }
      });
  },
  register({ commit, state }) {
    if (state.user.password === '' || state.user.email === '' || state.user.username === '') {
        commit(StoreMutations.SET_MESSAGE, 'Please enter email and password');
        commit(StoreMutations.TOGGLE_SHOW_MESSAGE, true);
        commit(StoreMutations.SET_ALERT_COUNTDOWN, 5);
        commit(StoreMutations.SET_MESSAGE_TYPE, MessageTypes.warning);
        return
    }
    const api = useApi();

    api.post(LoginHelpers.paths.register(), state.user)
      .then((res) => {
        commit(StoreMutations.SET_MESSAGE, res.data.message);
        commit(StoreMutations.TOGGLE_SHOW_MESSAGE, true);
        commit(StoreMutations.SET_ALERT_COUNTDOWN, 5);
        commit(StoreMutations.SET_MESSAGE_TYPE, MessageTypes.success);
        router.push({ name: RouterList.routes.dashboard.name })
      })
      .catch((error) => {
        if (error.response && error.response.status === 401) {
          // Unauthorized error
          commit(StoreMutations.SET_MESSAGE, 'Invalid credentials. Please try again.');
          commit(StoreMutations.TOGGLE_SHOW_MESSAGE, true);
          commit(StoreMutations.SET_ALERT_COUNTDOWN, 5);
          commit(StoreMutations.SET_MESSAGE_TYPE, MessageTypes.warning);
        } else {
          // Other errors
          console.error(error);
        }
      });
  },
  check_login({commit}) {
      const api = useApi();

      return api.get(LoginHelpers.paths.checkLogin())
          .then((res) => {
              localStorage.setItem('jwt', res.data);
              commit(StoreMutations.SET_LOGGED_IN, true);
              router.push({ name: RouterList.routes.dashboard.name })
          })
          .catch((error) => {
              if (error.response && error.response.status === 401) {
                  // Unauthorized error
                  localStorage.removeItem('jwt');
                  commit(StoreMutations.SET_LOGGED_IN, false);
              } else if (error.response && error.response.status === 404) {
                  commit(StoreMutations.SET_MESSAGE, 'User not found');
                  commit(StoreMutations.TOGGLE_SHOW_MESSAGE, true);
                  commit(StoreMutations.SET_ALERT_COUNTDOWN, 5);
                  commit(StoreMutations.SET_MESSAGE_TYPE, MessageTypes.warning);
              } else {
                  // Other errors
                  console.error(error);
              }
          });
  },
  logout(state) {
    const api = useApi();

    return api.post(LoginHelpers.paths.logout())
      .then((res) => {
        localStorage.removeItem('jwt');
        state.commit(StoreMutations.SET_LOGGED_IN, false);
        state.commit(StoreMutations.SET_MESSAGE, res.data.message);
        state.commit(StoreMutations.TOGGLE_SHOW_MESSAGE, true);
        state.commit(StoreMutations.SET_ALERT_COUNTDOWN, 5);
        state.commit(StoreMutations.SET_MESSAGE_TYPE, MessageTypes.primary);
        router.push({ name: RouterList.routes.signin.name })
      })
      .catch((error) => {
        // Other errors
        localStorage.removeItem('jwt');
        state.commit(StoreMutations.SET_LOGGED_IN, false);
        router.push({ name: RouterList.routes.signin.name })
        console.error(error);
      });
  },
};
