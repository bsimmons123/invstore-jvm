import axios from 'axios';
import {useStore} from "vuex";
import {StoreMutations} from "@/store/login/mutations";
import StoreIndex from "@/store/login/_StoreIndex";

let instance = null;

export default function useApi() {
    const store = useStore();

    if (!instance) {
        instance = axios.create({withCredentials: true});

        // Add a request interceptor
        instance.interceptors.request.use((config) => {
            const token = localStorage.getItem('jwt'); // or wherever you store your token

            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }

            return config;
        }, (error) => {
            return Promise.reject(error);
        });

        // Response interceptor
        instance.interceptors.response.use((response) => {
            // If response is ok
            return response;
        }, (error) => {
            // If response is unauthorized
            if (error.response.status === 401) {
                // Redirect to sign-in route if 401 response received
                localStorage.removeItem('jwt');

                localStorage.removeItem('logged_in');
                store.commit(`${StoreIndex.storeName}/${StoreMutations.SET_LOGGED_IN}`, false);

                window.location.hash = "/signin?checklogin=true";
            }
            return Promise.reject(error);
        });
    }

    return instance
}