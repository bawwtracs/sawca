<template>
  <div id="index">
    <router-view
      @switchTheme="switchTheme"
      @switchLang="switchLang"
      @switchTimeZone="switchTimeZone"
    />
    <van-tabbar v-model="active">
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
      active: 0,
    };
  },
  methods: {
    switchTheme(name) {
      this.$emit("switchTheme", name);
    },
    switchLang(name) {
      this.$emit("switchLang", name);
    },
    switchTimeZone(name) {
      this.$emit("switchTimeZone", name);
    },
  },
  beforeCreate() {
    let account = this.cache.get("account");
    if (!account) {
      this.axios
        .get(this.api.userinfo())
        .then((response) => {
          let res = response;
          if (!res.succ) {
            this.$toast({
              type: "fail",
              message: res.msg,
              onOpened: () => {
                this.$router.push("/login");
              },
            });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }
  },
  created() {
    let routerName = this.$router.history.current.name;
    switch (routerName) {
      case "knowledge":
        this.active = 0;
        break;
      case "task":
        this.active = 1;
        break;
      case "statistics":
        this.active = 2;
        break;
      case "setting":
        this.active = 3;
        break;
    }
  },
};
</script>

<style lang="less">
</style>
