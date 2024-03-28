<script setup>
import {onBeforeUnmount, onBeforeMount, computed} from "vue";
import { useStore } from "vuex";

import Navbar from "@/examples/PageLayout/Navbar.vue";
import AppFooter from "@/examples/PageLayout/Footer.vue";
import ArgonInput from "@/components/ArgonInput.vue";
import ArgonCheckbox from "@/components/ArgonCheckbox.vue";
import ArgonButton from "@/components/ArgonButton.vue";
import StoreIndex from "@/store/login/_StoreIndex";
import {StoreActions} from "@/store/login/actions";
import ArgonAlert from "@/components/ArgonAlert.vue";
const body = document.getElementsByTagName("body")[0];

const store = useStore();

const argonState = store.state.argon;
const loginState = store.state.userLogin;

const email = computed(() => loginState.user.email);
const password = computed(() => loginState.user.password);
const name = computed(() => loginState.user.username);

const showErrors = computed(() => loginState.showMessage);
const errors = computed(() => loginState.message);

const emailUpdate = (payload) => store.commit("userLogin/SET_EMAIL", payload);
const passwordUpdate = (payload) => store.commit("userLogin/SET_PASSWORD", payload);
const nameUpdate = (payload) => store.commit("userLogin/SET_NAME", payload);
const showError = (payload) => store.commit("userLogin/TOGGLE_SHOW_MESSAGE", payload);

const register = () => store.dispatch(`${StoreIndex.storeName}/${StoreActions.register}`);

onBeforeMount(() => {
  argonState.hideConfigButton = true;
  argonState.showNavbar = false;
  argonState.showSidenav = false;
  argonState.showFooter = false;
  showError(false);
  body.classList.remove("bg-gray-100");
});
onBeforeUnmount(() => {
  argonState.hideConfigButton = false;
  argonState.showNavbar = true;
  argonState.showSidenav = true;
  argonState.showFooter = true;
  showError(false);
  body.classList.add("bg-gray-100");
});
</script>
<template>
  <div class="container top-0 position-sticky z-index-sticky">
    <div class="row">
      <div class="col-12">
        <navbar isBtn="bg-gradient-light" />
      </div>
    </div>
  </div>
  <main class="main-content mt-0">
    <div
      class="page-header align-items-start min-vh-50 pt-5 pb-11 m-3 border-radius-lg"
      style="
        background-image: url(&quot;https://raw.githubusercontent.com/creativetimofficial/public-assets/master/argon-dashboard-pro/assets/img/signup-cover.jpg&quot;);
        background-position: top;
      "
    >
      <span class="mask bg-gradient-dark opacity-6"></span>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-5 text-center mx-auto">
            <h1 class="text-white mb-2 mt-5">Welcome!</h1>
            <p class="text-lead text-white">
              Use these awesome forms to login or create new account in your
              project for free.
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row mt-lg-n10 mt-md-n11 mt-n10 justify-content-center">
        <div class="col-xl-4 col-lg-5 col-md-7 mx-auto">
          <div class="card z-index-0">
            <div class="card-header text-center pt-4">
              <h5>Register</h5>
            </div>
            <div class="card-body">
              <form role="form" @submit.prevent="register">
                <argon-input
                  id="name"
                  type="text"
                  placeholder="Name"
                  aria-label="Name"
                  :model-value="name"
                  @update:model-value="nameUpdate"
                />
                <argon-input
                  id="email"
                  type="email"
                  placeholder="Email"
                  aria-label="Email"
                  :model-value="email"
                  @update:model-value="emailUpdate"
                />
                <argon-input
                  id="password"
                  type="password"
                  placeholder="Password"
                  aria-label="Password"
                  :model-value="password"
                  @update:model-value="passwordUpdate"
                />
                <argon-checkbox checked>
                  <label class="form-check-label" for="flexCheckDefault">
                    I agree the
                    <a href="javascript:;" class="text-dark font-weight-bolder"
                      >Terms and Conditions</a
                    >
                  </label>
                </argon-checkbox>
                <argon-alert
                  :dismissible="false"
                  color="danger"
                  v-if="showErrors"
                >
                  <template v-if="showErrors">
                      {{ errors }}
                  </template>
                </argon-alert>
                <div class="text-center">
                  <argon-button
                    fullWidth
                    color="dark"
                    variant="gradient"
                    class="my-4 mb-2"
                    >Sign up</argon-button
                  >
                </div>
                <p class="text-sm mt-3 mb-0">
                  Already have an account?
                  <a href="javascript:;" class="text-dark font-weight-bolder"
                    >Sign in</a
                  >
                </p>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
  <app-footer />
</template>
