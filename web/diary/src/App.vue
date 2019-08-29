<template>
  <div id="app">
    If you want to keep a secret, you must also hide it from yourself.
    <div id="nav">
      <router-link to="/list">List</router-link>|
      <router-link to="/detail/safewa">Detail</router-link>
    </div>
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
}
</style>
