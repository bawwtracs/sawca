<template>
  <div id="todo" class="pdt-46">
    <van-nav-bar
      :fixed="true"
      :z-index="999"
      title="Todo List"
      left-text="Back"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <van-icon name="plus" slot="right" />
    </van-nav-bar>
    <van-swipe-cell v-for="todoItem in todoItems" :key="todoItem.id">
      <van-cell :border="false" :title="todoItem.title" @click="show(todoItem)">
        <template #right-icon>
          <van-count-down :time="calcCountDown(todoItem.time)" />
        </template>
      </van-cell>
      <template slot="right">
        <van-button
          style="height:100%"
          hairline
          size="small"
          type="danger"
          text="del"
          @click="del(todoItem)"
        />
      </template>
    </van-swipe-cell>
    <van-popup v-model="showTodo" position="bottom">
      <br />
      <div class="pd-hor-20">{{todoItem.time}} &emsp; {{todoItem.title}}</div>
      <van-divider />
      <div class="pd-hor-20">{{todoItem.content}}</div>
      <van-divider />
    </van-popup>
    <van-popup v-model="showEditor" position="bottom">
      <van-cell-group>
        <van-datetime-picker
          title="time"
          v-model="todoItem.time"
          type="time"
          :filter="filter"
          @confirm="submit"
        />
        <van-field
          label="Title"
          v-model="todoItem.title"
          input-align="center"
          placeholder="title"
          :clearable="true"
        />
        <van-field
          label="Content"
          v-model="todoItem.content"
          rows="2"
          autosize
          type="textarea"
          input-align="center"
          placeholder="content"
          :clearable="true"
        />
      </van-cell-group>
    </van-popup>
  </div>
</template>

<script>
export default {
  name: "Todo",
  components: {},
  data() {
    return {
      todoItems: [],
      showTodo: false,
      showEditor: false,
      submiting: false,
      todoItem: {},
      accid: null,
    };
  },
  methods: {
    filter(type, options) {
      if (type === "minute") {
        return options.filter((option) => option % 5 === 0);
      }
      return options;
    },
    calcCountDown(timeStr) {
      let time = timeStr.split(":");
      let now = new Date();
      let hours = now.getHours();
      let minues = now.getMinutes();
      let hourSub = time[0] - hours;
      let minSub = time[1] - minues;
      if (minSub < 0) {
        minSub += 60;
        hourSub -= 1;
      }
      return 3600000 * hourSub + 60000 * minSub;
    },
    resetEditor() {
      this.todoItem = {};
    },
    onClickLeft() {
      this.$router.go(-1);
    },
    onClickRight() {
      this.resetEditor();
      this.showEditor = true;
    },
    show(todo) {
      this.showTodo = true;
      this.todoItem = todo;
    },
    del(todo) {
      let ObjectId = require("../common/util/idHex");
      this.axios
        .delete(this.api.removeTodo(ObjectId.hexString(todo._id)))
        .then((res) => {
          if (res.succ) {
            this.initTodoList();
          }
        });
    },
    initTodoList() {
      this.axios.get(this.api.todoList(this.accid)).then((res) => {
        if (res.succ) {
          this.todoItems = res.data;
        }
      });
    },
    submit(value) {
      this.submiting = false;
      this.submiting = true;
      let todoItem = this.todoItem;
      todoItem.time = value;
      todoItem.accid = this.accid;
      this.axios.post(this.api.createTodo(), todoItem).then((res) => {
        if (res.succ) {
          this.$toast("saved");
          this.initTodoList();
        }
        this.showEditor = false;
      });
    },
  },
  beforeCreate() {},
  created() {
    this.accid = this.cache.get("account")._id;
    this.initTodoList();
  },
};
</script>

<style lang="less">
.pdt-46 {
  padding-top: 46px;
}
.pd-hor-20 {
  padding: 0 20px;
}
</style>
