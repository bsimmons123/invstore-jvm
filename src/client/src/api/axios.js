import axios from 'axios';

// Create a new instance of axios
const instance = axios.create({withCredentials: true});

// a helper function to get the JWT token
function getToken() {
    return localStorage.getItem('jwt'); // or wherever you store your token
}

// Add a request interceptor
instance.interceptors.request.use((config) => {
    const token = getToken();

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
}, (error) => {
    return Promise.reject(error);
});

export default instance;