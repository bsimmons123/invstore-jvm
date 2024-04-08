export default class CateringListHelpers {
  static paths = {
    root: '/api',
    cateringListPath: '/v1/cateringlist/',
    cateringListUserPath: '/v1/cateringlist/user/',
    cateringListSessionPath: '/v1/cateringlist/session/',
    cateringItemPath: '/v1/cateringitem/list/',

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
    }
  };
}
