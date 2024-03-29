export default class Helpers {
  static paths = {
    root: '/api',
    cateringListPath: '/v1/cateringlist/',
    cateringListUserPath: '/v1/cateringlist/user/',
    cateringList() {
      return this.root + this.cateringListPath;
    },
    cateringListByUser() {
      return this.root + this.cateringListUserPath;
    },
  };
}
