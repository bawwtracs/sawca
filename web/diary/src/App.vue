<template>
  <div id="app">
    <div class="secret">If you want to keep a secret, you must also hide it from yourself.</div>
    <br />
    <router-view />
  </div>
</template>

<style>
#app {
  font-family: "Times New Roman", Georgia, Serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  display: flex;
  flex-flow: column nowrap;
  justify-content: flex-start;
  align-items: center;
  height: 100%;
  width: 100%;
  position: relative;
  font-size: 14pt;
}

.secret {
  font-size: 17pt;
  font-weight: bold;
  text-align: center;
}


</style>
