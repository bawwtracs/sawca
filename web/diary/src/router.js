import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      redirect: "diaries"
    },
    {
      path: "/diaries",
      name: "diaries",
      component: () => import(/* webpackChunkName: "list" */ "./views/List.vue")
    },
    {
      path: "/diary/:id",
      name: "diary",
      component: () =>
        import(/* webpackChunkName: "edit" */ "./views/Edit.vue"),
      props: true
    },
    {
      path: "/diary",
      name: "diary",
      component: () =>
        import(/* webpackChunkName: "create" */ "./views/Edit.vue"),
      props: true
    }
  ]
});
