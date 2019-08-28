import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/list",
      name: "list",
      component: () =>
        import(/* webpackChunkName: "list" */ "./views/List.vue"),
      props: true
    },
    {
      path: "/detail/:id",
      name: "detail",
      component: () =>
        import(/* webpackChunkName: "detail" */ "./views/Detail.vue"),
      props: true
    }
  ]
});
