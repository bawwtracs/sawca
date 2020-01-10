import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      redirect: "origin"
    },
    {
      path: "/origin",
      name: "origin",
      component: () =>
        import(/* webpackChunkName: "origin" */ "./components/Origin.vue"),
      props: true
    },
    {
      path: "/word/originId/:originId",
      name: "word",
      component: () =>
        import(/* webpackChunkName: "word" */ "./components/Word.vue"),
      props: true
    }
  ]
});
