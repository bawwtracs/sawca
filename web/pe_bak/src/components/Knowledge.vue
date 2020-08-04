<template>
  <div id="knowledge">
    <div class="top">
      <div class="wel">{{wel}}</div>
      <div class="date">{{date}}</div>
    </div>
    <van-divider />
    <van-grid :gutter="16" :center="false" :border="false" :column-num="2">
      <van-grid-item to="/todoList" @click="updateLastClick('todoList')">
        <knowledge-icon color="blue"></knowledge-icon>
        <div class="knowledge-class">ToDoList</div>
        <div class="last-time">Last at {{toDoListTime}}</div>
      </van-grid-item>
      <van-grid-item to="/origin" @click="updateLastClick('vocabulary')">
        <knowledge-icon color="green"></knowledge-icon>
        <div class="knowledge-class">Vocabulary</div>
        <div class="last-time">Last at {{vocabularyTime}}</div>
      </van-grid-item>
    </van-grid>
  </div>
</template>

<script>
import knowledgeIcon from "@/components/KnowledgeIcon.vue";
export default {
  name: "Knowledge",
  components: {
    knowledgeIcon,
  },
  data() {
    return {
      wel: "",
      date: "",
      toDoListTime: "",
      vocabularyTime: "",
    };
  },
  methods: {
    updateLastClick(name) {
      let opearte = this.cache.get("operate");
      let now = new Date().toUTCString();
      opearte[name].lastTime = now;
      this.cache.put("operate", opearte);
      switch (name) {
        case "vocabulary":
          this.vocabularyTime = now;
          break;
        case "todoList":
          this.toDoListTime = now;
          break;
      }
    },
  },
  beforeCreate() {},
  created() {
    let account = this.cache.get("account");
    this.wel = `Hello, ${account.username}`;
    let operate = this.cache.get("operate");
    if (operate.todoList && operate.todoList.lastTime) {
      this.toDoListTime = operate.todoList.lastTime;
    }
    if (operate.vocabulary && operate.vocabulary.lastTime) {
      this.vocabularyTime = operate.vocabulary.lastTime;
    }
    this.date = new Date().toLocaleDateString();
  },
};
</script>

<style lang="less">
#knowledge {
  width: 100%;
  margin-bottom: 50px;
  .top {
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
}
.knowledge-class {
  margin-top: 5px;
  font-size: 1.1rem;
  font-weight: bold;
}
.last-time {
  font-size: 0.8rem;
  color: #bebebe;
}
</style>
