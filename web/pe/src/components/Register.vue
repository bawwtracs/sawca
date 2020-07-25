<template>
  <div id="register">
    <el-form class="form" :model="registerForm" status-icon :rules="rules" ref="registerForm">
      <img src="../assets/pe.png" />
      <div class="tips">{{ $t("message.registerTips") }}</div>
      <el-form-item prop="email">
        <el-input type="email" :placeholder="$t('message.email')" v-model="registerForm.email"></el-input>
      </el-form-item>
      <el-form-item prop="username" class="username">
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
      <van-button
        class="btn btn-primary"
        round
        block
        native-type="button"
        @click="submitForm('registerForm')"
      >{{ $t("message.register") }} ></van-button>
      <van-button
        class="btn"
        round
        block
        native-type="button"
        @click="resetForm('registerForm')"
      >{{ $t("message.reset") }}</van-button>
      <van-button
        class="btn btn-cancel"
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
      interval: 1500,
      currentEmail: "",
      emailSet: {},
      currentUsername: "",
      usernameSet: {},
      registerForm: {
        email: "",
        username: "",
        password: ""
      },
      rules: {
        email: [
          {
            required: true,
            message: this.$t("message.emptyEmail"),
            trigger: "blur"
          },
          {
            type: "email",
            message: this.$t("message.emailError"),
            trigger: ["blur", "change"]
          },
          {
            validator: (rule, value, callback) => {
              this.currentEmail = value;
              setTimeout(() => {
                if (value && value == this.currentEmail) {
                  if (this.emailSet[value] === "allow") {
                    callback();
                  } else if (this.emailSet[value] === "deny") {
                    callback(
                      new Error(this.$t("message.emailHasBeenRegistered"))
                    );
                  } else {
                    this.axios
                      .get(this.api.checkEmailAvail(value))
                      .then(response => {
                        if (response.succ) {
                          if (!response.data) {
                            this.emailSet[value] = "deny";
                            callback(
                              new Error(
                                this.$t("message.emailHasBeenRegistered")
                              )
                            );
                          } else {
                            this.emailSet[value] = "allow";
                            callback();
                          }
                        } else {
                          callback(
                            new Error(this.$t("message.remoteUnkonwError"))
                          );
                        }
                      })
                      .catch(() => {
                        callback(new Error(this.$t("message.errorNetwork")));
                      });
                  }
                }
              }, this.interval);
            },
            trigger: ["blur", "change"]
          }
        ],
        username: [
          {
            required: true,
            message: this.$t("message.emptyUsername"),
            trigger: "blur"
          },
          {
            validator: (rule, value, callback) => {
              if (value.length < 3 || value.length > 16) {
                callback(new Error(this.$t("message.usernameLengthError")));
              } else if (!/^[a-z0-9._-]/gim.test(value)) {
                callback(new Error(this.$t("message.usernameCharError")));
              } else {
                this.currentUsername = value;
                setTimeout(() => {
                  if (value == this.currentUsername) {
                    if (this.usernameSet[value] === "allow") {
                      callback();
                    } else if (this.usernameSet[value] === "deny") {
                      callback(
                        new Error(this.$t("message.usernameHasBeenRegistered"))
                      );
                    } else {
                      this.axios
                        .get(this.api.checkUsernameAvail(value))
                        .then(response => {
                          if (response.succ) {
                            if (!response.data) {
                              this.usernameSet[value] = "deny";
                              callback(
                                new Error(
                                  this.$t("message.usernameHasBeenRegistered")
                                )
                              );
                            } else {
                              this.usernameSet[value] = "allow";
                              callback();
                            }
                          } else {
                            callback(
                              new Error(this.$t("message.remoteUnkonwError"))
                            );
                          }
                        })
                        .catch(() => {
                          callback(new Error(this.$t("message.errorNetwork")));
                        });
                    }
                  }
                }, this.interval);
              }
            },
            trigger: ["blur", "change"]
          }
        ],
        password: [
          {
            required: true,
            message: this.$t("message.emptyPassword"),
            trigger: "blur"
          },
          {
            validator: (rule, value, callback) => {
              let length = value.length;
              if (length >= 8 && length <= 30) {
                callback();
              } else {
                callback(new Error(this.$t("message.passwordError")));
              }
            },
            trigger: ["blur", "change"]
          }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let MD5 = require("md5.js");
          let data = { ...this.registerForm };
          data.password = new MD5().update(data.password).digest("hex");
          this.axios
            .post(this.api.register(), data)
            .then(response => {
              if (response.succ) {
                this.$router.push("/index");
              } else {
                this.$toast.fail(response.msg);
              }
            })
            .catch(() => {
              this.$toast.fail(this.$t("message.errorNetwork"));
            });
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
#register {
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
  }
}
</style>
