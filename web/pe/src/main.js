import Vue from 'vue';
import App from './App.vue';
import router from "./router";

Vue.config.productionTip = false

// localStorage
Vue.prototype.cache = window.localStorage;

// api
import api from "./common/rest/api";
Vue.prototype.api = api;

// less
import less from 'less'
Vue.use(less);

// i18n
import VueI18n from 'vue-i18n'
Vue.use(VueI18n)
const messages = {
    "en-US": require('./common/lang/en'),
    "zh-CN": require('./common/lang/zh')
}
const i18n = new VueI18n({
    locale: 'en-US',
    // locale: 'zh-CN',
    messages,
})
import { Locale } from 'vant';
import enUS from 'vant/lib/locale/lang/en-US';
Locale.use('en-US', enUS);
// import zhCN from 'vant/lib/locale/lang/zh-CN';
// Locale.use('zh-CN', zhCN);
import locale from 'element-ui/lib/locale'
import lang from 'element-ui/lib/locale/lang/en'
// import lang from 'element-ui/lib/locale/lang/zh-CN'
locale.use(lang)

// request
import axios from "axios";
axios.interceptors.response.use(res => {
    return res.data;
});
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);

// vant
import { Form } from 'vant';
Vue.use(Form);
import { Cell, CellGroup } from 'vant';
Vue.use(Cell);
Vue.use(CellGroup);
import { Button } from 'vant';
Vue.use(Button);
import { Field } from 'vant';
Vue.use(Field);
import { Toast } from 'vant';
Vue.use(Toast);
import { Tabbar, TabbarItem } from 'vant';
Vue.use(Tabbar);
Vue.use(TabbarItem);
import { Grid, GridItem } from 'vant';
Vue.use(Grid);
Vue.use(GridItem);
import { Image as VanImage } from 'vant';
Vue.use(VanImage);
import { Icon } from 'vant';
Vue.use(Icon);
import { Switch } from 'vant';
Vue.use(Switch);
import { ActionSheet } from 'vant';
Vue.use(ActionSheet);
import { Uploader } from 'vant';
Vue.use(Uploader);

// element-ui
import { Link } from 'element-ui';
Vue.use(Link);
import { Form as Form1 } from 'element-ui';
Vue.use(Form1);
import { FormItem } from 'element-ui';
Vue.use(FormItem);
import { Input } from 'element-ui';
Vue.use(Input);

new Vue({
    router,
    render: h => h(App),
    i18n
}).$mount('#app')