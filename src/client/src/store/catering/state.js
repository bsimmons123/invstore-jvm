import {createCateringListAdapter} from "@/store/catering/services/CreateCateringListAdapter";
import {createCateringItemTypeAdapter} from "@/store/catering/services/CreateCateringItemTypeAdapter";
import {createCateringItemAdapter} from "@/store/catering/services/CreateCateringItemAdapter";

export const StoreState = {
  list: 'list',
  loadingLists: 'loadingLists',
  createListLoading: 'createListLoading',
  createErrors: 'createErrors',
  createListAdapter: 'createListAdapter',
  createListOption: 'createListOption',
  loadingUpdateList: 'loadingUpdateList',
  updateErrors: 'updateErrors',

  selList: 'selList',
  items: 'items',
  loadingItems: 'loadingLists',
  createItemLoading: 'createItemLoading',
  createItemErrors: 'createItemErrors',
  createItemAdapter: 'createItemAdapter',
  createItemOption: 'createItemOption',

  types: 'types',
  loadingTypes: 'loadingTypes',
  createTypeLoading: 'createTypeLoading',
  createTypeErrors: 'createTypeErrors',
  createTypeAdapter: 'createTypeAdapter',
  createTypeOption: 'createTypeOption',

  invites: 'invites',
  invitesLoading: 'invitesLoading',
  createInviteErrors: 'createInviteErrors',
  createInviteLoading: 'createInviteLoading',

  userInvites: 'userInvites',
  userInvitesLoading: 'userInvitesLoading',
};

export default {
  list: [],
  loadingLists: false,
  createListLoading: false,
  createErrors: [],
  createListAdapter: createCateringListAdapter(),
  createListOption: false,

  items: [],
  loadingItems: false,
  createItemLoading: false,
  createItemErrors: [],
  createItemAdapter: createCateringItemAdapter(),
  createItemOption: false,
  selList: {},

  types: [],
  loadingTypes: false,
  createTypeLoading: false,
  createTypeErrors: [],
  createTypeAdapter: createCateringItemTypeAdapter(),
  createTypeOption: false,

  invites: [],
  invitesLoading: false,
  createInviteErrors: [],
  createInviteLoading: false,

  userInvites: [],
  userInvitesLoading: false
};
