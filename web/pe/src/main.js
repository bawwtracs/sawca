import Vue from 'vue'
import App from './App.vue'


Vue.config.productionTip = false

import less from 'less'
Vue.use(less);

//i18n
import VueI18n from 'vue-i18n'
Vue.use(VueI18n)
const messages = {
    // "ja-JP": require('./common/lang/jp'),
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

// api
import api from "./api";
Vue.prototype.api = api;

// request
import axios from "axios";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios);

//vant
import { Form } from 'vant';
Vue.use(Form);
import { CellGroup } from 'vant';
Vue.use(CellGroup);
import { Cell } from 'vant';
Vue.use(Cell);
import { Button } from 'vant';
Vue.use(Button);
import { Field } from 'vant';
Vue.use(Field);
import { Toast } from 'vant';
Vue.use(Toast);

//element-ui
import { Link } from 'element-ui';
Vue.use(Link);

new Vue({
    render: h => h(App),
    i18n
}).$mount('#app')