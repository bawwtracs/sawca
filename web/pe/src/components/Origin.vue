<template>
  <div>
    <van-nav-bar
      :fixed="true"
      :z-index="999"
      title="Vocabulary"
      left-text="Back"
      left-arrow
      @click-left="onClickLeft"
    ></van-nav-bar>
    <van-tabs style="padding-top:46px;" color="rgb(25, 137, 250)" v-model="active" @click="switchTab">
      <van-tab title="Origin" name="list">
        <van-index-bar :index-list="indexAdapter">
          <template v-for="(indexItem, key) in originAdapter">
            <van-index-anchor :key="key" :index="key" />
            <van-swipe-cell v-for="originItem in originAdapter[key]" :key="originItem.id">
              <van-cell :border="false" :title="originItem.name" @click="toWords(originItem.id)" />
              <template slot="right">
                <van-button
                  style="height:100%"
                  hairline
                  size="small"
                  type="primary"
                  text="edit"
                  @click="edit(originItem)"
                />
                <van-button
                  style="height:100%"
                  hairline
                  size="small"
                  type="danger"
                  text="del"
                  @click="del(originItem)"
                />
              </template>
            </van-swipe-cell>
          </template>
        </van-index-bar>
      </van-tab>
      <van-tab title="New / Edit" name="edit">
        <br />
        <van-field readonly clickable label="Type" :value="type" @click="showTypePicker = true" />
        <van-popup v-model="showTypePicker" position="bottom">
          <van-picker
            show-toolbar
            :columns="originTypes"
            @cancel="showTypePicker = false"
            @confirm="confirmType"
          />
        </van-popup>
        <van-cell-group>
          <van-field v-model="name" rows="2" autosize label="Name" type="textarea" />
        </van-cell-group>
        <van-cell-group>
          <van-field v-model="source" rows="2" autosize label="Source" type="textarea" />
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
        <br />
      </van-tab>
    </van-tabs>
  </div>
</template>

<script>
import { Dialog } from "vant";
export default {
  name: "Origin",
  props: {},
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
    switchTab() {
      this.currentId = false;
      this.name = "";
      this.type = "";
      this.source = "";
    },
    toWords(originId) {
      this.$router.push(`/word/originId/${originId}`);
    },
    edit(origin) {
      this.currentId = origin.id;
      this.name = origin.name;
      this.type = origin.type;
      this.source = origin.source;
      this.active = "edit";
    },
    del(origin) {
      Dialog.confirm({
        title: "Sure?",
        message: `${origin.name}`,
      })
        .then(() => {
          this.axios.delete(this.api.origin(origin.id)).then((response) => {
            this.origins = this.origins.filter((obj) => {
              return obj.id != response.data;
            });
          });
        })
        .catch(() => {
          // on cancel
        });
    },
    confirmType(value) {
      this.type = value;
      this.showTypePicker = false;
    },
    submit() {
      let type = this.type;
      let name = this.name;
      let source = this.source;
      if (type && name) {
        this.submiting = true;
        if (!this.currentId) {
          this.axios
            .post(this.api.origin(), {
              type,
              name,
              source,
            })
            .then((response) => {
              this.submiting = false;
              this.origins.push(response.data);
              this.active = "list";
            });
        } else if (this.currentId) {
          this.axios
            .put(this.api.origin(this.currentId), {
              type,
              name,
              source,
            })
            .then((response) => {
              this.submiting = false;
              this.origins = this.origins.map((obj) => {
                if (obj.id == response.data.id) {
                  return response.data;
                } else {
                  return obj;
                }
              });
              this.active = "list";
            });
        }
      }
    },
    getOrigins() {
      this.axios.get(this.api.origins()).then((response) => {
        this.origins = response.data;
      });
    },
  },
  computed: {
    originAdapter() {
      let pinyin = require("pinyin");
      let letterSet = {};
      for (let i = 0; i < this.origins.length; i++) {
        let firstChar = this.origins[i].name.charAt(0);
        if (firstChar) {
          let firstLetter = pinyin(firstChar, {
            style: pinyin.STYLE_FIRST_LETTER,
          })[0]
            .toString()
            .toUpperCase();
          if (!letterSet[firstLetter]) {
            letterSet[firstLetter] = [];
          }
          letterSet[firstLetter].push(this.origins[i]);
        }
      }
      return letterSet;
    },
    indexAdapter() {
      let pinyin = require("pinyin");
      let letterSet = new Set();
      for (let i = 0; i < this.origins.length; i++) {
        let firstChar = this.origins[i].name.charAt(0);
        if (firstChar) {
          let firstLetter = pinyin(firstChar, {
            style: pinyin.STYLE_FIRST_LETTER,
          })[0]
            .toString()
            .toUpperCase();
          letterSet.add(firstLetter);
        }
      }
      return Array.from(letterSet);
    },
  },
  created() {
    this.getOrigins();
  },
  data() {
    return {
      indexList: [],
      currentId: false,
      origins: [],
      active: "list",
      type: "",
      showTypePicker: false,
      originTypes: ["Book", "Site"],
      name: "",
      source: "",
      submiting: false,
    };
  },
};
</script>

