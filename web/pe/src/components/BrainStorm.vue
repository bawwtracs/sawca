<template>
  <div id="brainStorm">
    <van-nav-bar
      :fixed="true"
      :z-index="999"
      title="BrainStorm"
      left-text="Back"
      left-arrow
      @click-left="onClickLeft"
    ></van-nav-bar>
  </div>
</template>

<script>
export default {
  name: "BrainStorm",
  components: {},
  data() {
    return {};
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
  },
  beforeCreate() {},
  created() {},
};
</script>

<style lang="less">
</style>
