<template>
  <div id="app">
    <Origin msg="Origin" />
  </div>
</template>

<script>
import Origin from "./components/Origin.vue";

export default {
  name: "app",
  components: {
    Origin
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
