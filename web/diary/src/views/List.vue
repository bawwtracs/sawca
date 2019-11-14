<template>
  <div class="list">
    <van-list v-model="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
      <van-cell v-for="item in list" :key="item" :title="item"></van-cell>
    </van-list>
    <!-- <div class="item" v-for="item in list" :key="item.id" @click="detail(item.id)">
      <div class="line">{{ item.id }}</div>
      <div class="line">{{ item.title }}</div>
      <div class="line">{{ item.content }}</div>
      <div class="line">{{ item.time }}</div>
    </div>
    <router-link to="/diary" tag="div" class="diary"></router-link>-->
  </div>
</template>

<script>
import Vue from "vue";
import { List } from "vant";
import { Cell } from "vant";

Vue.use(List);
Vue.use(Cell);
export default {
  data: () => {
    return {
      list: [],
      loading: false,
      finished: false
    };
  },
  created() {
    // this.axios
    //   .get(`${this.api.diaries}?offset=0&limit=20&sort=-_id`)
    //   .then(response => {
    //     this.list = response.data.diaries;
    //   });
  },
  methods: {
    detail(id) {
      this.$router.push({ path: `/diary/${id}` });
    },
    onLoad() {
      // 异步更新数据
      setTimeout(() => {
        for (let i = 0; i < 20; i++) {
          this.list.push(this.list.length + 1);
        }
        // 加载状态结束
        this.loading = false;

        // 数据全部加载完成
        if (this.list.length >= 80) {
          this.finished = true;
        }
      }, 500);
    }
  }
};
</script>
