<template>
  <div id="note" class="pdt-46">
    <van-nav-bar
      :fixed="true"
      :z-index="999"
      title="Note"
      left-text="Back"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <van-icon name="plus" slot="right" />
    </van-nav-bar>
    <van-swipe-cell v-for="note in notes" :key="note.id">
      <van-cell :border="false" :title="note.title" @click="show(note)"></van-cell>
      <template slot="right">
        <van-button
          style="height:100%"
          hairline
          size="small"
          type="danger"
          text="del"
          @click="del(note)"
        />
      </template>
    </van-swipe-cell>
    <van-popup v-model="showNote" position="bottom">
      <br />
      <div class="pd-hor-20">{{note.title}}</div>
      <van-divider />
      <div class="pd-hor-20">{{note.content}}</div>
      <van-divider />
    </van-popup>
    <van-popup v-model="showEditor" position="bottom">
      <van-cell-group :border="false">
        <van-field
          label="Title"
          v-model="note.title"
          input-align="center"
          placeholder="title"
          :clearable="true"
        />
        <van-field
          label="Content"
          v-model="note.content"
          rows="6"
          autosize
          type="textarea"
          input-align="center"
          placeholder="content"
          :clearable="true"
        />
      </van-cell-group>
      <br />
      <div style="text-align:center;">
        <van-button
          type="primary"
          plain
          @click="submit"
          :disabled="submiting"
          :loading="submiting"
          hairline
        >Submit</van-button>
      </div>
      <van-divider />
    </van-popup>
  </div>
</template>

<script>
export default {
  name: "Note",
  components: {},
  data() {
    return {
      notes: [],
      note: {},
      showNote: false,
      showEditor: false,
      submiting: false,
      accid: null,
    };
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
    onClickRight() {
      this.resetEditor();
      this.showEditor = true;
    },
    resetEditor() {
      this.note = {};
    },
    initNotes() {
      this.axios.get(this.api.notes(this.accid)).then((res) => {
        if (res.succ) {
          this.notes = this._.sortBy(res.data, (obj) => {
            return obj._id.time;
          });
          console.log(this.notes);
        }
      });
    },
    show(note) {
      this.showNote = true;
      this.note = note;
    },
    del(note) {
      let ObjectId = require("../common/util/idHex");
      this.axios
        .delete(this.api.note(ObjectId.hexString(note._id)))
        .then((res) => {
          if (res.succ) {
            this.initNotes();
          }
        });
    },
    submit() {
      this.submiting = true;
      let note = this.note;
      note.accid = this.accid;
      this.axios.post(this.api.note(), note).then((res) => {
        if (res.succ) {
          this.$toast("saved");
          this.initNotes();
        }
        this.submiting = false;
        this.showEditor = false;
      });
    },
  },
  beforeCreate() {},
  created() {
    this.accid = this.cache.get("account")._id;
    this.initNotes();
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
