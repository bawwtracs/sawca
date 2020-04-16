<template>
  <div class="register">
    <el-form class="form" :model="registerForm" status-icon :rules="rules" ref="registerForm">
      <img src="../assets/pe.png" />
      <div class="tips">{{ $t("message.registerTips") }}</div>
      <el-form-item
        prop="email"
        :rules="[
          { required: true, message: $t('message.emptyEmail'), trigger: 'change' },
          { type: 'email', message: $t('message.emailError'), trigger:  'change' }
        ]"
      >
        <el-input :placeholder="$t('message.email')" v-model="registerForm.email"></el-input>
      </el-form-item>
      <el-form-item prop="username">
        <el-input type="text" :placeholder="$t('message.username')" v-model="registerForm.username"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          type="password"
          :placeholder="$t('message.password')"
          v-model="registerForm.password"
          show-password
        />
      </el-form-item>
      <el-form-item prop="checkPass">
        <el-input
          type="password"
          :placeholder="$t('message.checkPass')"
          v-model="registerForm.checkPass"
          show-password
        ></el-input>
      </el-form-item>
      <van-button
        class="submit"
        round
        block
        native-type="button"
        @click="submitForm('registerForm')"
      >{{ $t("message.register") }} ></van-button>
      <van-button
        class="reset"
        round
        block
        native-type="button"
        @click="resetForm('registerForm')"
      >{{ $t("message.reset") }}</van-button>
      <van-button
        class="cancel"
        round
        block
        native-type="button"
        @click="cancel()"
      >{{ $t("message.cancel") }}</van-button>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    return {
      registerForm: {
        email: "",
        username: "",
        password: "",
        checkPass: ""
      },
      rules: {
        password: [
          {
            validator: (rule, value, callback) => {
              if (value === "") {
                callback(new Error(this.$t("message.emptyPassword")));
              } else {
                if (this.registerForm.checkPass !== "") {
                  this.$refs.registerForm.validateField("checkPass");
                }
                callback();
              }
            },
            trigger: "change"
          }
        ],
        checkPass: [
          {
            validator: (rule, value, callback) => {
              if (value === "") {
                callback(new Error(this.$t("message.emptyCheckPass")));
              } else if (value !== this.registerForm.password) {
                callback(new Error(this.$t("message.checkPassError")));
              } else {
                callback();
              }
            },
            trigger: "change"
          }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // this.axios.post();
          let data = this.registerForm.clone();
          console.log(data);
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    cancel() {
      this.$router.go(-1);
    }
  }
};
</script>

<style lang="less">
.register {
  padding: 0 8pt;
  .tips {
    font-weight: bold;
    margin: 5pt 0;
  }
  .form {
    width: 100%;
    img {
      width: 100%;
    }
    input {
      border-radius: 50px;
    }
    .submit {
      background-image: linear-gradient(to left, #6ab5d0, #34ecbb);
      color: #fff;
      font-weight: bold;
    }
    .reset,
    .cancel {
      margin: 7pt 0;
    }
    .cancel {
      background: #f4f5f8;
      color: gray;
    }
  }
}
</style>
