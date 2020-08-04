<template>
  <div id="login" :class="login">
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
      <van-button
        class="btn btn-primary"
        round
        block
        native-type="submit"
      >{{ $t("message.login") }} ></van-button>
      <van-button class="btn" round block native-type="button">{{ $t("message.guest") }} ></van-button>
    </van-form>
    <div class="link">
      <el-link @click="toRegister" type="primary">{{ $t("message.register") }}</el-link>
      <b>&emsp;|&emsp;</b>
      <el-link type="primary">{{ $t("message.forgotPassword") }}</el-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      login: "login-loading",
      username: "",
      password: "",
      advice: ""
    };
  },
  methods: {
    onLogin(values) {
      let MD5 = require("md5.js");
      values.password = new MD5().update(values.password).digest("hex");
      this.axios
        .post(this.api.login(), values)
        .then(response => {
          if (response.succ) {
            let account = response.data;
            let ObjectId = require("../common/util/idHex");
            account._id = ObjectId.hexString(account._id);
            this.cache["account"] = JSON.stringify(account);
            this.$router.push("/index");
          } else {
            this.$toast.fail(response.msg);
          }
        })
        .catch(() => {});
    },
    toRegister() {
      this.$router.push("/register");
    }
  },
  created() {
    this.login = "login-loading";
    setTimeout(() => {
      this.login = "login";
    }, 1500);
    // this.login = "login";
  }
};
</script>

<style lang="less">
#login {
  height: 100%;
}

.login-loading {
  display: flex;
  flex-flow: column nowrap;
  justify-content: center;
  align-items: center;

  * {
    display: none;
  }

  .wel {
    display: block;
    font-size: 36pt;
    font-family: "Comic Sans MS", cursive, sans-serif;
  }
}

.login {
  box-sizing: border-box;
  padding: 0 8pt;
  width: 100%;
  height: 100%;
  display: flex;
  flex-flow: column nowrap;
  justify-content: space-between;
  align-items: center;
  .wel {
    display: none;
  }
  .logo,
  .form {
    width: 100%;
  }
  .advice {
    font-weight: bold;
    margin: 15pt 7pt;
  }
  .username,
  .password {
    margin: 10pt 0;
    font-weight: bold;
    border-radius: 50px;
    div {
      border-radius: 50px;
    }
  }
  .link {
    margin-bottom: 10pt;
  }
}
</style>
