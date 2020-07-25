<template>
  <div id="app" :class="theme">
    <router-view @switchTheme="switchTheme" @switchLang="switchLang" />
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      theme: "",
    };
  },
  methods: {
    initSetting() {
      let setting = this.getSetting();
      this.switchLang(setting.lang);
      this.switchTheme(setting.theme);
    },
    getSetting() {
      let setting = this.cache["setting"];
      if (!setting) {
        setting = JSON.stringify({
          lang: "en-US",
          timeZone: "UTC",
          theme: "colorful",
          showNotification: true,
          syncAutomatically: true,
        });
        this.cache["setting"] = setting;
      }
      return JSON.parse(setting);
    },
    saveSetting(options) {
      let setting = this.getSetting();
      Object.assign(setting, options);
      this.cache["setting"] = JSON.stringify(setting);
    },
    switchTheme(name) {
      this.theme = name.toLowerCase();
      this.saveSetting({ theme: name });
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
      this.saveSetting({ lang: name });
    },
    switchTimeZone(name) {
      console.log(name);
    },
  },
  beforeCreate() {},
  created() {
    this.initSetting();
  },
};
</script>


<style>
#app {
  height: 100%;
}
</style>
