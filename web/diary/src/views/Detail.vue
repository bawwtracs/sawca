<template>
  <div class="detail">
    <h1>{{ diary.id }}</h1>
  </div>
</template>

<script>
export default {
  data() {
    return {
      diary: {}
    };
  },
  props: {
    id: {
      type: String,
      default: ""
    }
  },
  created() {
    if (this.id) {
      this.axios.get(`${this.api.diary}/${this.id}`).then(response => {
        this.diary = response.data;
      });
    }
  }
};
</script>
