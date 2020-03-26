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
import util from "../util";

export default {
  name: "Login",
  data() {
    return {
      login: "hello",
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
          let res = response.data;
          if (res.succ) {
            let _id = res.data._id;
            console.log(util.hexString(_id));
          } else {
            this.$toast.fail(res.msg);
          }
        })
        .catch(() => {});
    }
  },
  created() {
    setTimeout(() => {
      this.login = "login";
    }, 500);
  }
};
</script>

<style lang="less">
.hello {
  height: 100%;
  width: 100%;
  background-image: linear-gradient(#6ab5d0, #34ecbb);
  display: flex;
  flex-flow: column nowrap;
  justify-content: center;
  align-items: center;
  * {
    display: none;
  }
  .wel {
    display: block;
    color: #fff;
    font-size: 36pt;
    font-family: "Comic Sans MS", cursive, sans-serif;
  }
}

.login {
  background: #f4f5f8;
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
  .submit {
    background-image: linear-gradient(to left, #6ab5d0, #34ecbb);
    color: #fff;
    font-weight: bold;
  }
  .visit {
    margin: 7pt 0;
    font-weight: bold;
    background: #f4f5f8;
    color: gray;
  }
  .link {
    margin-bottom: 10pt;
  }
}
</style>
