<template>
  <div :class="login">
    <div class="wel">{{ $t("message.wel") }}</div>
    <van-form class="form" @submit="onLogin">
      <img class="logo" src="../assets/pe.png" alt />
      <div class="advice">{{ $t("message.advice") }}</div>
      <van-cell-group class="username">
        <van-field
          type="text"
          name="username"
          v-model="username"
          :placeholder="$t('message.username')"
        />
      </van-cell-group>
      <van-cell-group class="password">
        <van-field
          type="password"
          name="password"
          v-model="password"
          :placeholder="$t('message.password')"
        />
      </van-cell-group>
      <van-button class="submit" round block native-type="submit">{{ $t("message.login") }} ></van-button>
      <van-button class="visit" round block native-type="button">{{ $t("message.guest") }} ></van-button>
    </van-form>
    <div class="link">
      <el-link type="primary">{{ $t("message.register") }}</el-link>
      <b>&emsp;|&emsp;</b>
      <el-link type="primary">{{ $t("message.forgotPassword") }}</el-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "ForgotPassword",
  data() {
    return {
    };
  },
  methods: {
  },
  created() {
  }
};
</script>

<style lang="less">
</style>
