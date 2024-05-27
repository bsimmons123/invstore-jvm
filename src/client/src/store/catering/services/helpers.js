export default class CateringListHelpers {
  static paths = {
    root: '/api',
    cateringListPath: '/v1/cateringlist/',
    cateringListUserPath: '/v1/cateringlist/user/',
    cateringListSessionPath: '/v1/cateringlist/session/',
    cateringItemPath: '/v1/cateringitem/list/',
    cateringItemCreatePath: '/v1/cateringitem/',
    cateringItemTypeCreatePath: '/v1/cateringitemtype/',
    cateringItemTypePath: '/v1/cateringitemtype/list/',
    cateringInviteCreatePath: '/v1/invitelist/',
    cateringInviteListIdPath: '/v1/invitelist/list/',
    cateringInviteListRecPath: '/v1/invitelist/received/',

    cateringList() {
      return this.root + this.cateringListPath;
    },
    cateringListByUser() {
      return this.root + this.cateringListUserPath;
    },
    createCateringList() {
      return this.root + this.cateringListPath;
    },
    getCateringListBySessionId() {
      return this.root + this.cateringListSessionPath;
    },
    getCateringListById() {
      return this.root + this.cateringItemPath;
    },
    createCateringItem() {
      return this.root + this.cateringItemCreatePath;
    },
    createCateringItemType() {
      return this.root + this.cateringItemTypeCreatePath;
    },
    cateringItemType() {
      return this.root + this.cateringItemTypePath;
    },
    cateringInviteCreate() {
      return this.root + this.cateringInviteCreatePath
    },
    cateringInviteListId() {
      return this.root + this.cateringInviteListIdPath
    },
    cateringInviteListRec() {
      return this.root + this.cateringInviteListRecPath
    }
  };
}
