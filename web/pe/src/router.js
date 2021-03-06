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
                path: "/brainStorm",
                name: "brainStorm",
                component: () =>
                    import( /* webpackChunkName: "brainStorm" */ "./components/BrainStorm.vue"),
                props: true,
            },
            {
                path: "/note",
                name: "note",
                component: () =>
                    import( /* webpackChunkName: "note" */ "./components/Note.vue"),
                props: true,
            },
            {
                path: "/todoList",
                name: "todoList",
                component: () =>
                    import( /* webpackChunkName: "todoList" */ "./components/TodoList.vue"),
                props: true,
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