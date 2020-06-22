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
            import( /* webpackChunkName: "login" */ "./components/Login.vue"),
        props: true
    },
    {
        path: "/register",
        name: "register",
        component: () =>
            import( /* webpackChunkName: "register" */ "./components/Register.vue"),
        props: true
    },
    {
        path: "/forgotpassward",
        name: "forgotPassword",
        component: () =>
            import( /* webpackChunkName: "forgotPassword" */ "./components/ForgotPassword.vue"),
        props: true
    },
    {
        path: "/index",
        name: "index",
        redirect: "setting",
        component: () =>
            import( /* webpackChunkName: "index" */ "./components/Index.vue"),
        props: true,
        children: [
            {
                path: "/todo",
                name: "todo",
                component: () =>
                    import( /* webpackChunkName: "todo" */ "./components/Todo.vue"),
                props: true,
            },
            {
                path: "/knowledge",
                name: "knowledge",
                component: () =>
                    import( /* webpackChunkName: "knowledge" */ "./components/Knowledge.vue"),
                props: true,
            },
            {
                path: "/task",
                name: "task",
                component: () =>
                    import( /* webpackChunkName: "task" */ "./components/Task.vue"),
                props: true,
            },
            {
                path: "/statistics",
                name: "statistics",
                component: () =>
                    import( /* webpackChunkName: "statistics" */ "./components/Statistics.vue"),
                props: true,
            },
            {
                path: "/setting",
                name: "setting",
                component: () =>
                    import( /* webpackChunkName: "setting" */ "./components/Setting.vue"),
                props: true,
            }
        ]
    },
    ]
});