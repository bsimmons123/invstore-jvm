<script setup>
defineProps({
  title: {
    type: String,
    default: "Catering List",
  },
  list: {
    type: Array,
    default: () => ([]),
  },
  loading: {
    type: Boolean,
    default: false,
  },
  selected: {
    type: Object,
    default: () => ({}),
  },
});
</script>

<template>
  <div class="card">
    <div class="card-header pb-0">
      <h6>{{ title }}</h6>
    </div>
    <div class="card-body px-0 pt-0 pb-2">
      <div class="table-responsive p-0">
        <table class="table align-items-center mb-0">
          <thead>
          <tr>
            <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
            >
              Label
            </th>
            <th
                class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2"
            >
              Created By
            </th>
            <th
                class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
            >
              Status
            </th>
            <th
                class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"
            >
              Created At
            </th>
            <th class="text-secondary opacity-7"></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(value, index) in list" :key="index">
            <td>
              <div class="d-flex px-2 py-1">
                <div>
                  <img src="../../assets/img/team-3.jpg" class="avatar avatar-sm me-3" alt="user2" />
                </div>
                <div class="d-flex flex-column justify-content-center">
                  <h6 class="mb-0 text-sm">{{ value.label }}</h6>
                  <p class="text-xs text-secondary mb-0">{{ value.description }}</p>
                </div>
              </div>
            </td>
            <td>
              <p class="text-xs font-weight-bold mb-0">{{ value.user.name }}</p>
              <p class="text-xs text-secondary mb-0">{{ value.user.email }}</p>
            </td>
            <td class="align-middle text-center text-sm">
              <span class="badge badge-sm" :class="value.isActive ? 'bg-gradient-primary' : 'bg-gradient-secondary'">{{ value.isActive ? 'Active' : 'Inactive' }}</span>
            </td>
            <td class="align-middle text-center">
              <span class="text-secondary text-xs font-weight-bold">{{ value.createdAt }}</span>
            </td>
            <td class="align-middle">
              <a href="javascript:;"
                 class="text-secondary font-weight-bold text-xs"
                 data-toggle="tooltip"
                 data-original-title="Edit user">
                Edit
              </a>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>
