<template>
  <div id="index">
    <router-view />
    <van-tabbar>
      <van-tabbar-item to="/knowledge" icon="notes-o">{{ $t("message.knowledgeTab") }}</van-tabbar-item>
      <van-tabbar-item to="/task" icon="cluster-o">{{ $t("message.taskTab") }}</van-tabbar-item>
      <van-tabbar-item to="/statistics" icon="bar-chart-o">{{ $t("message.statisticsTab") }}</van-tabbar-item>
      <van-tabbar-item to="/setting" icon="setting-o">{{ $t("message.settingTab") }}</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  name: "Index",
  components: {},
  data() {
    return {};
  },
  methods: {
    onChange(index) {
      // this.active = index;
      console.log(index);
    }
  },
  beforeCreate() {
    let account = this.cache["account"];
    if (!account) {
      this.axios
        .get(this.api.userinfo())
        .then(response => {
          let res = response;
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
#index {
  height: 100%;
  width: 100%;
}
</style>
