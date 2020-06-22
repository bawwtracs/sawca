<template>
  <div>
    <van-cell-group title="General">
      <van-cell value="EN" size="large" is-link @click="showLangs = true">
        <template #title>
          <!-- <van-icon name="brush-o" size="24" /> -->
          <span class="custom-title">Language</span>
        </template>
      </van-cell>
      <van-action-sheet
        v-model="showLangs"
        :actions="langs"
        @select="selectLang"
        close-on-click-action
      />
      <van-cell value="Default" size="large" is-link>
        <template #title>
          <!-- <van-icon name="brush-o" size="24" /> -->
          <span class="custom-title">Theme</span>
        </template>
      </van-cell>

      <van-cell size="large">
        <template #title>
          <!-- <van-icon name="brush-o" size="24" /> -->
          <span class="custom-title">Show Notification</span>
        </template>
        <template #right-icon>
          <van-switch v-model="checked" size="24px" inactive-color="#bebebe" />
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
      checked: false,
      showLangs: false,
      langs: [{ name: "En" }, { name: "ZH" }, { name: "JP" }]
    };
  },
  methods: {
    selectLang(item) {
      this.showLangPicker = false;
      console.log(item);
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
