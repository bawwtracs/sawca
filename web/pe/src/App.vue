<template>
  <div id="app" :class="theme">
    <router-view
      @switchTheme="switchTheme"
      @switchLang="switchLang"
      @switchTimeZone="switchTimeZone"
    />
  </div>
</template>

<script>
import "./assets/less/global.less";
export default {
  name: "App",
  data() {
    return {
      theme: "",
    };
  },
  methods: {
    initOperate() {
      let opearte = this.cache.get("operate");
      if (!opearte) {
        opearte = {
          vocabulary: {},
          todoList: {},
          brainStorm: {},
          note: {},
        };
        this.cache.put("operate", opearte);
      }
    },
    initSetting() {
      let setting = this.cache.get("setting");
      if (!setting) {
        setting = {
          lang: "en-US",
          timeZone: "UTC",
          theme: "colorful",
          showNotification: true,
          syncAutomatically: true,
        };
        this.cache.put("setting", setting);
      }
      this.switchLang(setting.lang);
      this.switchTheme(setting.theme);
    },
    switchTheme(name) {
      this.theme = name.toLowerCase();
      let setting = this.cache.get("setting");
      setting.theme = name;
      this.cache.put("setting", setting);
    },
    switchLang(name) {
      this.$i18n.locale = name;
      let vant = require("vant");
      let vnatLang = require(`vant/lib/locale/lang/${name}`);
      vant.Locale.use(name, vnatLang.default);
      let elementLocale = require("element-ui/lib/locale");
      let elLang;
      if (name === "en-US") {
        elLang = require("element-ui/lib/locale/lang/en");
      } else if (name === "zh-CN") {
        elLang = require("element-ui/lib/locale/lang/zh-CN");
      }
      elementLocale.use(elLang.default);
      let setting = this.cache.get("setting");
      setting.lang = name;
      this.cache.put("setting", setting);
    },
    switchTimeZone(name) {
      console.log(name);
    },
  },
  beforeCreate() {},
  created() {
    this.initOperate();
    this.initSetting();
  },
};
</script>


<style lang="less">
</style>
