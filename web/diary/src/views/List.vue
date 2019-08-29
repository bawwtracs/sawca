<template>
  <div class="list">
    <br />
    <div class="item" v-for="item in list.data" :key="item.id">
      <div class="title">
        <b>title</b>
        {{item.title}}
      </div>
      <div class="title">
        <b>keyword</b>
        {{item.keyword}}
      </div>
      <div class="title">
        <b>content</b>
        {{item.content}}
      </div>
      <div class="date">
        <b>date</b>
        {{item.date}}
      </div>
      <br />
      <br />
    </div>
  </div>
</template>

<style lang="less">
.list {
  display: flex;
  flex-flow: column nowrap;
  justify-content: center;
  align-items: center;
  text-align: center;
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
  }
};
</script>