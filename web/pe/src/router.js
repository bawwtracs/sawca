import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
    routes: [{
            path: "/",
            redirect: "index"
        },
        {
            path: "/login",
            name: "login",
            component: () =>
                import ( /* webpackChunkName: "login" */ "./components/Login.vue"),
            props: true
        },
        {
            path: "/register",
            name: "register",
            component: () =>
                import ( /* webpackChunkName: "register" */ "./components/Register.vue"),
            props: true
        },
        {
            path: "/forgotpassward",
            name: "forgotPassword",
            component: () =>
                import ( /* webpackChunkName: "forgotPassword" */ "./components/ForgotPassword.vue"),
            props: true
        },
        {
            path: "/index",
            name: "index",
            component: () =>
                import ( /* webpackChunkName: "index" */ "./components/Index.vue"),
            props: true
        }
    ]
});