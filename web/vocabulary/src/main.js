import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

// api
import api from "./api";
Vue.prototype.api = api;

// request
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);

// Vant
import { Locale } from "vant";
import enUS from "vant/lib/locale/lang/en-US";
Locale.use("en-US", enUS);
import { Icon } from "vant";
Vue.use(Icon);
import { NavBar } from "vant";
Vue.use(NavBar);
import { Tab, Tabs } from "vant";
Vue.use(Tab).use(Tabs);
import { Cell, CellGroup } from "vant";
Vue.use(Cell).use(CellGroup);
import { SwipeCell } from "vant";
Vue.use(SwipeCell);
import { IndexBar, IndexAnchor } from "vant";
Vue.use(IndexBar).use(IndexAnchor);
import { Field } from "vant";
Vue.use(Field);
import { Popup } from "vant";
Vue.use(Popup);
import { Picker } from "vant";
Vue.use(Picker);
import { Button } from "vant";
Vue.use(Button);
import { Divider } from "vant";
Vue.use(Divider);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
