<template>
  <particle-background/>
  <v-container fluid>
    <v-row align="center" justify="center">
      <v-col lg="5" xl="5" cols="12" class="d-lg-flex align-center justify-center">
        <v-img src="/assets/login-bg.svg" alt="login-background" class="position-relative d-none d-lg-flex"></v-img>
      </v-col>
      <v-col lg="4" xl="4" cols="12">
        <h2 class="text-h3 font-weight-bold mb-2">Welcome to Invstore</h2>
        <v-row>
          <v-col sm="6" cols="6">
            <v-btn block outlined color="blue">
              <v-icon left>mdi-google</v-icon>
              <span>Sign in with Google</span>
            </v-btn>
          </v-col>
          <v-col sm="6" cols="6">
            <v-btn block outlined color="blue">
              <v-icon left>mdi-github</v-icon>
              <span>Sign in with Github</span>
            </v-btn>
          </v-col>
        </v-row>
        <v-divider class="my-5"></v-divider>
        <v-form ref="form" validate-on="submit lazy" @submit.prevent="submit">
          <v-text-field
            label="Email"
            v-model="email"
            :error-messages="emailError"
          ></v-text-field>
          <v-text-field
            label="Password"
            type="password"
            v-model="password"
            :error-messages="passwordError"
            required
          ></v-text-field>
          <v-btn block color="primary" :loading="loading" type="submit">Sign in</v-btn>
        </v-form>
        <v-divider class="my-5"></v-divider>

        <v-alert :title="loginErrorMessage" v-if="loginErrorMessage" color="#C51162"></v-alert>
        <v-spacer></v-spacer>
        <h6 class="text-h6 d-flex align-center mt-6 font-weight-medium">
          New to Invstore?
          <v-spacer></v-spacer>
          <v-btn color="primary" to="/signup">Create an account</v-btn>
        </h6>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
import {computed, ref} from 'vue';
import {useAppStore} from "@/stores/app";
import ParticleBackground from "@/components/ParticleBackground.vue";

let appStore = useAppStore()

const loading = computed(() => appStore.isLoading);
const loginErrorMessage = computed(() => appStore.errorMessage);

let email = ref('');
let password = ref('');
let emailError = ref('');
let passwordError = ref('');
let timeout;

const checkApi = (email, pass) => {
  return new Promise(resolve => {
    clearTimeout(timeout);
    let errors = []

    if (!email || !/\S+@\S+\.\S+/.test(email)) {
      errors.push({ key: 'email', value: 'Please enter a valid email.' })
    }

    if (!pass) {
      errors.push({ key: 'password', value: 'Password cannot be blank.' })
    }

    clearErrors();

    if (errors.length > 0)
      return resolve(errors);
    else {
      appStore.errorMessage = '';
      return appStore.login({email: email, password: pass})
    }
  });
};

const submit = async () => {
  const results = await checkApi(email.value, password.value);

  if (results.length > 0) {
    results.forEach((res) => {
      if (res.key === 'email') {
        emailError.value = res.value;
      }
      if (res.key === 'password') {
        passwordError.value = res.value;
      }
    })
    appStore.errorMessage = '';
  }
};


const clearErrors = () => {
  emailError.value = '';
  passwordError.value = '';
};
</script>
