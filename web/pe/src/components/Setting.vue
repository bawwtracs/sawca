<template>
  <div id="setting">
    <van-cell center titleClass="custom-title" value-class="head-wrapper" size="large" clickable>
      <template #title>
        <van-icon name="bars" size="24" />
      </template>
      <template #default>
        <van-uploader>
          <van-image class="head" :src="require('../assets/head.jpg')" />
        </van-uploader>
      </template>
    </van-cell>
    <van-cell-group class="custom-group-title" title="General">
      <van-cell center titleClass="custom-title" size="large">
        <template #title>
          <van-image class="custom-icon" :src="require('../assets/icon_32/055-recycling.png')" />
          <span>{{ $t("message.setting.syncAutomatically") }}</span>
        </template>
        <template #right-icon>
          <van-switch v-model="setting.syncAutomatically" size="24px" />
        </template>
      </van-cell>
      <van-cell
        :value="setting.lang"
        center
        titleClass="custom-title"
        size="large"
        is-link
        @click="showLangs = true"
      >
        <template #title>
          <van-image class="custom-icon" :src="require('../assets/icon_32/034-earth.png')" />
          <span>{{ $t("message.setting.language") }}</span>
        </template>
      </van-cell>
      <van-action-sheet
        v-model="showLangs"
        :actions="langs"
        @select="selectLang"
        close-on-click-action
      />
      <van-cell
        :value="setting.timeZone"
        center
        titleClass="custom-title"
        size="large"
        is-link
        @click="showTimeZones = true"
      >
        <template #title>
          <van-image class="custom-icon" :src="require('../assets/icon_32/099-time-zone.png')" />
          <span>{{ $t("message.setting.timeZone") }}</span>
        </template>
      </van-cell>
      <van-action-sheet
        v-model="showTimeZones"
        :actions="timeZones"
        @select="selectTimeZone"
        close-on-click-action
      />
      <van-cell
        :value="setting.theme"
        center
        titleClass="custom-title"
        size="large"
        is-link
        @click="showThemes = true"
      >
        <template #title>
          <van-image
            fit="contain"
            class="custom-icon"
            :src="require('../assets/icon_32/076-infinity.png')"
          />
          <span>{{ $t("message.setting.theme") }}</span>
        </template>
      </van-cell>
      <van-action-sheet
        v-model="showThemes"
        :actions="themes"
        @select="selectTheme"
        close-on-click-action
      />
      <van-cell center titleClass="custom-title" size="large">
        <template #title>
          <van-image class="custom-icon" :src="require('../assets/icon_32/098-notification.png')" />
          <span>{{ $t("message.setting.showNotification") }}</span>
        </template>
        <template #right-icon>
          <van-switch v-model="setting.showNotification" size="24px" />
        </template>
      </van-cell>
    </van-cell-group>
    <van-cell-group class="custom-group-title" title="productivity">
      <van-cell center titleClass="custom-title" size="large" is-link>
        <template #title>
          <van-image class="custom-icon" :src="require('../assets/icon_32/059-rat.png')" />
          <span>{{ $t("message.setting.setGoal") }}</span>
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
      setting: null,
      showLangs: false,
      langs: [{ name: "en-US" }, { name: "zh-CN" }],
      showThemes: false,
      themes: [
        { name: "Colorful" }
        //  { name: "Dark" }
      ],
      showTimeZones: false,
      timeZones: [{ name: "UTC" }, { name: "GMT +8" }]
    };
  },
  methods: {
    selectLang(item) {
      this.showLangs = false;
      this.setting.lang = item.name;
      this.$emit("switchLang", this.setting.lang);
    },
    selectTheme(item) {
      this.showThemes = false;
      this.setting.theme = item.name;
      this.$emit("switchTheme", this.setting.theme);
    },
    selectTimeZone(item) {
      this.showTimeZones = false;
      this.setting.timeZone = item.name;
      //change Time Zone
    }
  },
  beforeCreate() {},
  created() {
    this.setting = JSON.parse(this.cache["setting"]);
  }
};
</script>

<style lang="less">
#setting {
  height: 100%;
  .head-wrapper {
    display: flex;
    flex-flow: row nowrap;
    justify-content: flex-end;
    .head {
      width: 40px;
      height: 40px;
      border: solid 1px;
      box-shadow: 0px 0px 7px rgba(190, 190, 190, 1);
      border-radius: 5px;
    }
  }
}
.van-cell-group__title {
  font-weight: bold;
  color: rgba(0, 0, 0, 1);
}
.van-cell:not(:last-child)::after {
  display: none;
}
.custom-icon {
  overflow: hidden;
  border-radius: 50%;
  padding: 3px;
  box-shadow: 0px 0px 7px rgba(190, 190, 190, 1);
  width: 36px;
  height: 36px;
  display: flex;
  justify-content: center;
  align-items: center;
  .van-image__img {
    width: 60%;
    height: 60%;
  }
}
.custom-title {
  display: flex;
  flex-flow: row nowrap;
  justify-content: flex-start;
  align-items: center;
  .van-image {
    margin-right: 20px;
  }
  text-shadow: 0px 0px 2px rgba(190, 190, 190, 1);
}
</style>
