import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";

Vue.use(VueAxios, axios);
axios.defaults.timeout = 5000;
axios.defaults.baseURL = "http://localhost";
