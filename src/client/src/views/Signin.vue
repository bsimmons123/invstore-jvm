<script setup>
import {onBeforeUnmount, onBeforeMount, computed, onMounted} from "vue";
import { useStore } from "vuex";
import Navbar from "@/examples/PageLayout/Navbar.vue";
import ArgonInput from "@/components/ArgonInput.vue";
import ArgonSwitch from "@/components/ArgonSwitch.vue";
import ArgonButton from "@/components/ArgonButton.vue";
import StoreIndex from "@/store/login/_StoreIndex";
import {StoreActions} from "@/store/login/actions";
import ArgonAlert from "@/components/ArgonAlert.vue";
import {useRoute} from "vue-router";
const body = document.getElementsByTagName("body")[0];

const store = useStore();
const route = useRoute()

const argonState = store.state.argon;
const loginState = store.state.userLogin;

const email = computed(() => loginState.email);
const password = computed(() => loginState.password);

const showErrors = computed(() => loginState.showMessage);
const errors = computed(() => loginState.message);

const emailUpdate = (payload) => store.commit("userLogin/SET_EMAIL", payload);
const passwordUpdate = (payload) => store.commit("userLogin/SET_PASSWORD", payload);
const showError = (payload) => store.commit("userLogin/TOGGLE_SHOW_MESSAGE", payload);

const login = () => store.dispatch(`${StoreIndex.storeName}/${StoreActions.login}`);
const checkLogin = () => store.dispatch(`${StoreIndex.storeName}/${StoreActions.check_login}`);

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
  // disable errors
  showError(false);
  body.classList.add("bg-gray-100");
});
onMounted(() => {
  if (route.query.checklogin) {
    checkLogin()
  }
})
</script>
<template>
  <div class="container top-0 position-sticky z-index-sticky">
    <div class="row">
      <div class="col-12">
        <navbar
          isBlur="blur  border-radius-lg my-3 py-2 start-0 end-0 mx-4 shadow"
          v-bind:darkMode="true"
          isBtn="bg-gradient-success"
        />
      </div>
    </div>
  </div>
  <main class="mt-0 main-content">
    <section>
      <div class="page-header min-vh-100">
        <div class="container">
          <div class="row">
            <div
              class="mx-auto col-xl-4 col-lg-5 col-md-7 d-flex flex-column mx-lg-0"
            >
              <div class="card card-plain">
                <div class="pb-0 card-header text-start">
                  <h4 class="font-weight-bolder">Sign In</h4>
                  <p class="mb-0">Enter your email and password to sign in</p>
                </div>
                <argon-alert
                  :dismissible="false"
                  color="danger"
                  v-if="showErrors"
                >
                  <template v-if="showErrors">
                      {{ errors }}
                  </template>
                </argon-alert>
                <div class="card-body">
                  <form role="form"  @submit.prevent="login">
                    <div class="mb-3">
                      <argon-input
                        id="email"
                        type="email"
                        placeholder="Email"
                        name="email"
                        size="lg"
                        :model-value="email"
                        @update:model-value="emailUpdate"
                      />
                    </div>
                    <div class="mb-3">
                      <argon-input
                        id="password"
                        type="password"
                        placeholder="Password"
                        name="password"
                        size="lg"
                        :model-value="password"
                        @update:model-value="passwordUpdate"
                      />
                    </div>
                    <argon-switch id="rememberMe" name="remember-me"
                      >Remember me
                    </argon-switch>
                    <!--         GITHUB           -->
                    <div class="row px-xl-5 px-sm-4 px-3">
                      <div class="mt-2 position-relative text-center">
                        <p
                            class="text-sm font-weight-bold mb-2 text-secondary text-border d-inline z-index-2 bg-white px-3"
                        >
                          or
                        </p>
                      </div>
                      <div class="col-3 ms-auto px-1">
                        <a class="btn btn-outline-light w-100" href="/oauth2/authorization/github">
                          <svg
                              width="24px"
                              height="32px"
                              viewBox="0 0 64 64"
                              version="1.1"
                          >
                            <g
                                id="Symbols"
                                stroke="none"
                                stroke-width="1"
                                fill="none"
                                fill-rule="evenodd"
                            >
                              <g
                                  transform="translate(0, 3.000000) scale(1.8)"
                                  fill="#100000"
                                  fill-rule="nonzero"
                              >
                                <g id="UI/icons/dark/github" fill="#182359">
                                  <path d="M17.9985267,2 C9.16436969,2 2,9.16338746 2,18.0004911 C2,25.0695847 6.58405721,31.0660855 12.9420179,33.1818042 C13.7425335,33.3291384 14.0342552,32.8350778 14.0342552,32.4107554 C14.0342552,32.0306332 14.020504,31.0248319 14.0126462,29.6899843 C9.56217195,30.6564965 8.62316216,27.5447988 8.62316216,27.5447988 C7.89533135,25.696246 6.84631204,25.2041499 6.84631204,25.2041499 C5.3935971,24.2120998 6.95632156,24.2317444 6.95632156,24.2317444 C8.56226404,24.3447006 9.40697996,25.8809049 9.40697996,25.8809049 C10.834157,28.3256699 13.1522146,27.6194481 14.063722,27.2098591 C14.2090917,26.1765554 14.6226097,25.4713159 15.0793456,25.0715492 C11.5266276,24.6678535 7.7912152,23.294699 7.7912152,17.163633 C7.7912152,15.417232 8.41492986,13.9880905 9.43841125,12.8703152 C9.27339697,12.4656374 8.72433162,10.8380859 9.5955677,8.63593112 C9.5955677,8.63593112 10.9382731,8.20571534 13.9949661,10.2762516 C15.27088,9.9206851 16.6401056,9.7438841 18.0004911,9.7370085 C19.3598944,9.7438841 20.7281378,9.9206851 22.0060161,10.2762516 C25.0607447,8.20571534 26.4014856,8.63593112 26.4014856,8.63593112 C27.2746861,10.8380859 26.7256208,12.4656374 26.5615888,12.8703152 C27.5870346,13.9880905 28.2058381,15.417232 28.2058381,17.163633 C28.2058381,23.3104147 24.4645324,24.6629424 20.9010099,25.0587802 C21.4746309,25.5528408 21.9863716,26.5291752 21.9863716,28.0211793 C21.9863716,30.1604715 21.966727,31.8862457 21.966727,32.4107554 C21.966727,32.8390067 22.255502,33.3369962 23.0668222,33.180822 C29.4198717,31.0601921 34,25.0676202 34,18.0004911 C34,9.16338746 26.8356303,2 17.9985267,2" id="icons/icon-github"/>
                                </g>
                              </g>
                            </g>
                          </svg>
                        </a>
                      </div>
                      <!--         GOOGLE           -->
                      <div class="col-3 me-auto px-1">
                        <a class="btn btn-outline-light w-100" href="/oauth2/authorization/google">
                          <svg
                              width="24px"
                              height="32px"
                              viewBox="0 0 64 64"
                              version="1.1"
                          >
                            <g
                                stroke="none"
                                stroke-width="1"
                                fill="none"
                                fill-rule="evenodd"
                            >
                              <g
                                  transform="translate(3.000000, 2.000000)"
                                  fill-rule="nonzero"
                              >
                                <path
                                    d="M57.8123233,30.1515267 C57.8123233,27.7263183 57.6155321,25.9565533 57.1896408,24.1212666 L29.4960833,24.1212666 L29.4960833,35.0674653 L45.7515771,35.0674653 C45.4239683,37.7877475 43.6542033,41.8844383 39.7213169,44.6372555 L39.6661883,45.0037254 L48.4223791,51.7870338 L49.0290201,51.8475849 C54.6004021,46.7020943 57.8123233,39.1313952 57.8123233,30.1515267"
                                    fill="#4285F4"
                                />
                                <path
                                    d="M29.4960833,58.9921667 C37.4599129,58.9921667 44.1456164,56.3701671 49.0290201,51.8475849 L39.7213169,44.6372555 C37.2305867,46.3742596 33.887622,47.5868638 29.4960833,47.5868638 C21.6960582,47.5868638 15.0758763,42.4415991 12.7159637,35.3297782 L12.3700541,35.3591501 L3.26524241,42.4054492 L3.14617358,42.736447 C7.9965904,52.3717589 17.959737,58.9921667 29.4960833,58.9921667"
                                    fill="#34A853"
                                />
                                <path
                                    d="M12.7159637,35.3297782 C12.0932812,33.4944915 11.7329116,31.5279353 11.7329116,29.4960833 C11.7329116,27.4640054 12.0932812,25.4976752 12.6832029,23.6623884 L12.6667095,23.2715173 L3.44779955,16.1120237 L3.14617358,16.2554937 C1.14708246,20.2539019 0,24.7439491 0,29.4960833 C0,34.2482175 1.14708246,38.7380388 3.14617358,42.736447 L12.7159637,35.3297782"
                                    fill="#FBBC05"
                                />
                                <path
                                    d="M29.4960833,11.4050769 C35.0347044,11.4050769 38.7707997,13.7975244 40.9011602,15.7968415 L49.2255853,7.66898166 C44.1130815,2.91684746 37.4599129,0 29.4960833,0 C17.959737,0 7.9965904,6.62018183 3.14617358,16.2554937 L12.6832029,23.6623884 C15.0758763,16.5505675 21.6960582,11.4050769 29.4960833,11.4050769"
                                    fill="#EB4335"
                                />
                              </g>
                            </g>
                          </svg>
                        </a>
                      </div>
                    </div>

                    <div class="text-center">
                      <argon-button
                        class="mt-4"
                        variant="gradient"
                        color="success"
                        fullWidth
                        size="lg"
                      >
                        Sign in
                      </argon-button>
                    </div>
                  </form>
                </div>
                <div class="px-1 pt-0 text-center card-footer px-lg-2">
                  <p class="mx-auto mb-4 text-sm">
                    Don't have an account?
                    <router-link to="signup" class="text-success text-gradient font-weight-bold">
                      Sign up
                    </router-link>
                  </p>
                </div>
              </div>
            </div>
            <div
              class="top-0 my-auto text-center col-6 d-lg-flex d-none h-100 pe-0 position-absolute end-0 justify-content-center flex-column"
            >
              <div
                class="position-relative bg-gradient-primary h-100 m-3 px-7 border-radius-lg d-flex flex-column justify-content-center overflow-hidden"
                style="
                  background-image: url(&quot;https://raw.githubusercontent.com/creativetimofficial/public-assets/master/argon-dashboard-pro/assets/img/signin-ill.jpg&quot;);
                  background-size: cover;
                "
              >
                <span class="mask bg-gradient-success opacity-6"></span>
                <h4
                  class="mt-5 text-white font-weight-bolder position-relative"
                >
                  "Attention is the new currency"
                </h4>
                <p class="text-white position-relative">
                  The more effortless the writing looks, the more effort the
                  writer actually put into the process.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>
