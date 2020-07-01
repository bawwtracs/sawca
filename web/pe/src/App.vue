<template>
  <div id="app" :class="theme">
    <router-view @switchTheme="switchTheme"></router-view>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      theme: "colorful"
    };
  },
  methods: {
    switchTheme(name) {
      this.theme = name;
    }
  },
  beforeCreate() {},
  created() {}
};
</script>


<style>
#app {
  height: 100%;
}
</style>
