import axios from 'axios';
import {useAppStore} from "@/stores/app";

let instance = null;

export default function useApi() {
    let appStore = useAppStore()

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
                appStore.isLoggedIn = false;

                window.location.hash = "/";
            }
            return Promise.reject(error);
        });
    }

    return instance
}
