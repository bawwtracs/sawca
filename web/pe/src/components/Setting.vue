<template>
  <div>
    <van-cell-group title="General">
      <van-cell size="large">
        <template #title>
          <!-- <van-icon name="brush-o" size="24" /> -->
          <span class="custom-title">{{ $t("message.setting.syncAutomatically") }}</span>
        </template>
        <template #right-icon>
          <van-switch v-model="syncAutomatically" size="24px" inactive-color="#bebebe" />
        </template>
      </van-cell>

      <van-cell :value="currentLang" size="large" is-link @click="showLangs = true">
        <template #title>
          <!-- <van-icon name="brush-o" size="24" /> -->
          <span class="custom-title">{{ $t("message.setting.language") }}</span>
        </template>
      </van-cell>
      <van-action-sheet
        v-model="showLangs"
        :actions="langs"
        @select="selectLang"
        close-on-click-action
      />

      <van-cell :value="currentTimeZone" size="large" is-link @click="showTimeZones = true">
        <template #title>
          <!-- <van-icon name="brush-o" size="24" /> -->
          <span class="custom-title">{{ $t("message.setting.timeZone") }}</span>
        </template>
      </van-cell>
      <van-action-sheet
        v-model="showTimeZones"
        :actions="timeZones"
        @select="selectTimeZone"
        close-on-click-action
      />

      <van-cell :value="currentTheme" size="large" is-link @click="showThemes = true">
        <template #title>
          <!-- <van-icon name="brush-o" size="24" /> -->
          <span class="custom-title">{{ $t("message.setting.themeTitle") }}</span>
        </template>
      </van-cell>
      <van-action-sheet
        v-model="showThemes"
        :actions="themes"
        @select="selectTheme"
        close-on-click-action
      />

      <van-cell size="large">
        <template #title>
          <!-- <van-icon name="brush-o" size="24" /> -->
          <span class="custom-title">{{ $t("message.setting.showNotification") }}</span>
        </template>
        <template #right-icon>
          <van-switch v-model="showNotification" size="24px" inactive-color="#bebebe" />
        </template>
      </van-cell>
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: "Setting",
  data() {
    return {
      syncAutomatically: true,
      showNotification: false,

      showLangs: false,
      langs: [{ name: "en-US" }, { name: "zh-CN" }],
      currentLang: "en-US",

      showThemes: false,
      themes: [
        { name: this.$t("message.setting.theme.default") },
        { name: this.$t("message.setting.theme.dark") },
        { name: this.$t("message.setting.theme.colorful") }
      ],
      currentTheme: this.$t("message.setting.theme.default"),

      showTimeZones: false,
      timeZones: [{ name: "GMT +8" }, { name: "UTC" }],
      currentTimeZone: "GMT +8"
    };
  },
  methods: {
    selectLang(item) {
      this.showLangs = false;
      this.currentLang = item.name;
      this.$i18n.locale = this.currentLang;
      let vant = require("vant");
      let vnatLang = require(`vant/lib/locale/lang/${this.currentLang}`);
      vant.Locale.use(this.currentLang, vnatLang);
      let elementLocale = require("element-ui/lib/locale");
      let elLang;
      if (this.currentLang === "en-Us") {
        elLang = require("element-ui/lib/locale/lang/en");
      } else if (this.currentLang === "zh-CN") {
        elLang = require("element-ui/lib/locale/lang/zh-CN");
      }
      elementLocale.use(elLang);
    },
    selectTheme(item) {
      this.showThemes = false;
      this.currentTheme = item.name;
      //change Theme
    },
    selectTimeZone(item) {
      this.showTimeZones = false;
      this.currentTimeZone = item.name;
      //change Time Zone
    }
  },
  beforeCreate() {},
  created() {}
};
</script>

<style lang="less">
.van-cell:not(:last-child)::after {
  display: none;
}
</style>
