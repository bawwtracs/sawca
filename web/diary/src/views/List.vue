<template>
  <div class="list">
    <div class="item" v-for="item in list.data" :key="item.id" @click="detail(item.id)">
      <div class="date">{{ item.date }}</div>
      <div class="line">{{ item.id }}</div>
      <div class="line">{{ item.title }}</div>
      <div class="line">{{ item.keyword }}</div>
      <div class="line">{{ item.content }}</div>
    </div>
    <router-link to="/create" tag="div" class="create"></router-link>
  </div>
</template>

<style lang="less">
.list {
  display: flex;
  flex-flow: column nowrap;
  justify-content: center;
  align-items: center;
  text-align: center;
  .item {
    width: 100%;
    margin: 7px 0;
    text-align: left;
    .date {
      text-align: center;
      font-size: 17pt;
      font-weight: bold;
      background: #e6e6e6;
    }
    .line {
      margin: 7px 0;
    }
    &::after {
      content: "";
      position: absolute;
      width: 100%;
      height: 1px;
      background: #e6e6e6;
    }
  }
}
.create {
  position: fixed;
  width: 36pt;
  height: 36pt;
  background: url("../assets/new.png") no-repeat;
  background-size: cover;
  bottom: 16pt;
  right: 16pt;
}
</style>

<script>
export default {
  data: () => {
    return {
      list: []
    };
  },
  created() {
    this.axios
      .get(`${this.api.diaries}?offset=0&limit=20&sort=-_id`)
      .then(response => {
        this.list = response.data;
      });
  },
  methods: {
    detail(id) {
      this.$router.push({ path: `/detail/${id}` });
    }
  }
};
</script>
