// Utilities
import { defineStore } from 'pinia'
import useApi from "@/stores/axios";
import ApiHelpers from "@/stores/ApiHelpers";
import router from "@/router";

export const useAppStore = defineStore('app', {
  state: () => ({
    isLoading: false,
    errorMessage: '',
    message: '',
    showMessage: false,
    alertDismissCountdown: 0,
    user: {},
    theme: 'dark'
  }),
  actions: {
    login(userCredentials) {
      this.isLoading = true;
      const api = useApi();
      return api.post(ApiHelpers.paths.login(), userCredentials)
        .then((res) => {
          localStorage.setItem('jwt', res.data.jwtToken);
          router.push({ path: '/' });
      }).catch((error) => {
        if (error.response.status === 401) {
          this.errorMessage = error.response.data.message;
        }
      })
        .finally(() => {
        this.isLoading = false;
      })
    },
    register(userCredentials) {
      this.isLoading = true;
      const api = useApi();
      return api.post(ApiHelpers.paths.register(), userCredentials)
        .then((res) => {
          localStorage.setItem('jwt', res.data.jwtToken);
          router.push({ path: '/' });
        }).catch((error) => {
          if (error.response.status === 401) {
            this.errorMessage = error.response.data.message;
          }
        })
        .finally(() => {
          this.isLoading = false;
        })
    },
    logout() {
      this.isLoading = true;
      const api = useApi();
      return api.post(ApiHelpers.paths.logout())
        .then(() => {
          localStorage.removeItem('jwt');
          router.push('/').then(() => router.go(0));
        }).catch((error) => {
          if (error.response.status === 401) {
            this.errorMessage = error.response.data.message;
          }
        })
        .finally(() => {
          this.isLoading = false;
        })
    },
    getUser() {
      this.isLoading = true;

      const api = useApi();
      return api.get(ApiHelpers.paths.getUser())
        .then((res) => {
          console.log(res)
          this.user = res.data.value;
        })
        .catch((error) => {
          this.user = null;
          console.log(error);
        })
        .finally(() => {
          this.isLoading = false;
        });
    }
  },
})
