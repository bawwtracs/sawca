<template>
  <div id="index">
    <div class="index-top">
      <div class="wel">Good morning, Tom</div>
      <div class="date">Tuesday, 23 June</div>
    </div>
    <router-view />
    <van-tabbar v-model="active" @change="onChange">
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
    return {
      active: 0
    };
  },
  methods: {
    onChange(index) {
      this.active = index;
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
  background-color: white;
}
.index-top {
  padding: 16px 0;
  .wel {
    padding: 0 24px;
    font-size: 1.2rem;
    font-weight: bold;
  }
  .date {
    padding: 0 24px;
    color: #bebebe;
    margin: 8px 0;
  }
}
</style>
