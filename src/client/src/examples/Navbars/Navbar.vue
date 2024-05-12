<script setup>
import {computed, onMounted, ref} from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import Breadcrumbs from "../Breadcrumbs.vue";
import StoreIndex from "@/store/login/_StoreIndex";
import CateringStoreIndex from "@/store/catering/_StoreIndex";
import ProfileStoreIndex from "@/store/profile/_StoreIndex";
import {StoreActions} from "@/store/login/actions";
import {StoreActions as CateringStoreActions} from "@/store/catering/actions";
import {StoreActions as ProfileStoreActions} from "@/store/profile/actions";

const showMenu = ref(false);
const store = useStore();

const route = useRoute();

const loginState = store.state.userLogin;
const profile = store.state.profile;
const cateringList = store.state.cateringList;

const loggedIn = computed(() => loginState.isLoggedIn);
const user = computed(() => profile.user)
const invites = computed(() => cateringList.userInvites)

const logout = () => store.dispatch(`${StoreIndex.storeName}/${StoreActions.logout}`);
const getUserProfile = () => store.dispatch(`${ProfileStoreIndex.storeName}/${ProfileStoreActions.getUser}`);
const getInvitesForUser = (userEmail) => store.dispatch(`${CateringStoreIndex.storeName}/${CateringStoreActions.getInvitesForUser}`, userEmail)

const currentRouteName = computed(() => {
  return route.name;
});
const currentDirectory = computed(() => {
  let dir = route.path.split("/")[1];
  return dir.charAt(0).toUpperCase() + dir.slice(1);
});

const minimizeSidebar = () => store.commit("argon/sidebarMinimize");
const toggleConfigurator = () => store.commit("argon/toggleConfigurator");

const closeMenu = () => {
  setTimeout(() => {
    showMenu.value = false;
  }, 100);
};

onMounted(() => {
  getUserProfile().then(() => {
    console.log(user.value)
    getInvitesForUser(user.value.email)
  })
});
</script>
<template>
  <nav
    class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl"
    :class="''"
    v-bind="$attrs"
    id="navbarBlur"
    data-scroll="true"
  >
    <div class="px-3 py-1 container-fluid">
      <breadcrumbs
        :current-page="currentRouteName"
        :current-directory="currentDirectory"
      />

      <div
        class="mt-2 collapse navbar-collapse mt-sm-0 me-md-0 me-sm-4"
        :class="'me-sm-4'"
        id="navbar"
      >
        <div
          class="pe-md-3 d-flex align-items-center"
          :class="'ms-md-auto'"
        >
          <div class="input-group">
            <span class="input-group-text text-body">
              <i class="fas fa-search" aria-hidden="true"></i>
            </span>
            <input
              type="text"
              class="form-control"
              :placeholder="'Type here...'"
            />
          </div>
        </div>
        <ul class="navbar-nav justify-content-end">
          <li class="nav-item d-flex align-items-center">
            <a v-if="loggedIn" @click="logout" style="cursor: pointer">
              <i class="fa fa-user" :class="'me-sm-2'"></i>
              <span class="d-sm-inline d-none">Logout</span>
            </a>

            <router-link
              :to="{ name: 'Signin' }"
              class="px-0 nav-link font-weight-bold text-white"
              v-if="!loggedIn"
            >
              <i class="fa fa-user" :class="'me-sm-2'"></i>
              <span class="d-sm-inline d-none">Sign In</span>
            </router-link>
          </li>
          <li class="nav-item d-xl-none ps-3 d-flex align-items-center">
            <a
              href="#"
              @click="minimizeSidebar"
              class="p-0 nav-link text-white"
              id="iconNavbarSidenav"
            >
              <div class="sidenav-toggler-inner">
                <i class="sidenav-toggler-line bg-white"></i>
                <i class="sidenav-toggler-line bg-white"></i>
                <i class="sidenav-toggler-line bg-white"></i>
              </div>
            </a>
          </li>
          <li class="px-3 nav-item d-flex align-items-center">
            <a class="p-0 nav-link text-white" @click="toggleConfigurator">
              <i class="cursor-pointer fa fa-cog fixed-plugin-button-nav"></i>
            </a>
          </li>
          <li
            class="nav-item dropdown d-flex align-items-center"
            :class="'pe-2'"
          >
            <a
              href="#"
              class="p-0 nav-link text-white"
              :class="[showMenu ? 'show' : '']"
              id="dropdownMenuButton"
              data-bs-toggle="dropdown"
              aria-expanded="false"
              @click="showMenu = !showMenu"
              @blur="closeMenu"
            >
              <i class="cursor-pointer fa fa-bell"></i>
            </a>
            <ul
              class="px-2 py-3 dropdown-menu dropdown-menu-end me-sm-n4"
              :class="showMenu ? 'show' : ''"
              aria-labelledby="dropdownMenuButton"
            >
              <li class="mb-2" v-for="inv in invites" :key="inv.id">
                <a class="dropdown-item border-radius-md" href="javascript:;">
                  <div class="py-1 d-flex">
                    <div class="my-auto">
                      <img
                        src="../../assets/img/team-2.jpg"
                        class="avatar avatar-sm me-3"
                        alt="user image"
                      />
                    </div>
                    <div class="d-flex flex-column justify-content-center">
                      <h6 class="mb-1 text-sm font-weight-normal">
                        <span class="font-weight-bold">New invite</span> from
                        {{ inv.whoInvited.username }}
                      </h6>
                      <p class="mb-0 text-xs text-secondary">
                        <i class="fa fa-clock me-1"></i>
                        to {{ inv.relatedList.label }}
                      </p>
                    </div>
                  </div>
                </a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>
