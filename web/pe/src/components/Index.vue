<template>
  <div>this is index.</div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {};
  },
  methods: {},
  beforeCreate() {
    let account = this.cache["account"];
    if (!account) {
      this.axios
        .get(this.api.userinfo())
        .then(response => {
          let res = response.data;
          if (res.succ) {
            let account = res.data;
            let ObjectId = require("../common/util/idHex");
            account._id = ObjectId.hexString(account._id);
            this.cache["account"] = JSON.stringify(account);
          } else {
            this.$toast({
              type: "fail",
              message: res.msg,
              onOpened: () => {
                this.$router.push("/login");
              }
            });
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {}
};
</script>

<style lang="less">
</style>
