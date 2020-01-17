<template>
  <div>
    <van-nav-bar
      :fixed="true"
      :z-index="999"
      title="Words"
      left-text="Back"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <van-icon name="plus" slot="right" />
    </van-nav-bar>
    <van-index-bar
      :index-list="indexAdapter"
      style="padding-top:46px;"
      :sticky="true"
      :sticky-offset-top="46"
    >
      <template v-for="(indexItem, key) in wordAdapter">
        <van-index-anchor :key="key" :index="key" />
        <van-swipe-cell v-for="wordItem in wordAdapter[key]" :key="wordItem.id">
          <van-cell :border="false" :title="wordItem.spelling" @click="show(wordItem)" />
          <template slot="right">
            <van-button
              style="height:100%"
              hairline
              size="small"
              type="danger"
              text="del"
              @click="del(wordItem)"
            />
          </template>
        </van-swipe-cell>
      </template>
    </van-index-bar>

    <van-popup style="text-align:center;" class="pd-20" v-model="showWord" position="bottom">
      {{spelling}} .{{lang}}
      <van-divider />
      <pre>{{representationsAdapter}}</pre>
    </van-popup>

    <van-popup v-model="showEditor" position="bottom">
      <van-cell-group>
        <van-field
          label="Lang"
          v-model="lang"
          input-align="center"
          placeholder="input langauage"
          :clearable="true"
        />
        <van-field
          label="Spelling"
          v-model="spelling"
          input-align="center"
          placeholder="input word"
          :clearable="true"
        />
        <van-field
          v-model="representations"
          rows="2"
          autosize
          type="textarea"
          input-align="center"
          placeholder="input representations here"
          :clearable="true"
        />
      </van-cell-group>
      <br />
      <div style="text-align:center;">
        <van-button type="warning" plain @click="reset" hairline>Reset</van-button>
        <van-button
          type="primary"
          plain
          @click="submit"
          :disabled="submiting"
          :loading="submiting"
          hairline
        >Submit</van-button>
        <van-divider />
      </div>
    </van-popup>
  </div>
</template>

<script>
import { Dialog } from "vant";

const _ = require("lodash");

export default {
  name: "Word",
  props: {
    originId: {
      type: String,
      default: ""
    }
  },
  methods: {
    show(word) {
      this.showWord = true;
      this.spelling = word.spelling;
      this.representations = word.representations;
      this.lang = word.lang;
    },
    hide() {
      this.showWord = false;
    },
    getWords() {
      this.axios
        .get(`${this.api.words()}?originId=${this.originId}`)
        .then(response => {
          this.words = response.data;
        });
    },
    del(word) {
      Dialog.confirm({
        title: "Sure?",
        message: `${word.spelling}`
      })
        .then(() => {
          this.axios.delete(this.api.word(word.id)).then(response => {
            this.words = this.words.filter(obj => {
              return obj.id != response.data;
            });
          });
        })
        .catch(() => {
          // on cancel
        });
    },
    reset() {
      this.lang = "en";
      this.spelling = "";
      this.representations = "";
    },
    submit() {
      let lang = this.lang;
      let spelling = this.spelling;
      let representations = this.representations;
      let originId = this.originId;
      if (spelling && representations) {
        this.submiting = true;
        this.axios
          .post(this.api.word(), {
            spelling,
            representations,
            lang,
            originId
          })
          .then(response => {
            this.submiting = false;
            this.words.push(response.data);
            this.showEditor = false;
          });
      }
    },
    onClickLeft() {
      this.$router.go(-1);
    },
    onClickRight() {
      this.reset();
      this.showEditor = true;
    }
  },
  computed: {
    wordAdapter() {
      let words = _.sortBy(this.words, obj => {
        return obj.spelling.toUpperCase();
      });
      let letterSet = {};
      for (let i = 0; i < words.length; i++) {
        let firstLetter = words[i].spelling
          .charAt(0)
          .toString()
          .toUpperCase();
        if (!letterSet[firstLetter]) {
          letterSet[firstLetter] = [];
        }
        letterSet[firstLetter].push(words[i]);
      }
      return letterSet;
    },
    indexAdapter() {
      let letterSet = new Set();
      for (let i = 0; i < this.words.length; i++) {
        let firstLetter = this.words[i].spelling
          .charAt(0)
          .toString()
          .toUpperCase();
        letterSet.add(firstLetter);
      }
      return _.sortBy(Array.from(letterSet), obj => {
        return obj.toUpperCase();
      });
    },
    representationsAdapter() {
      return this.representations
        .replace("adj.", "\nadj.")
        .replace("n.", "\nn.");
    }
  },
  created() {
    this.getWords();
  },
  data() {
    return {
      lang: "en",
      spelling: "",
      representations: "",
      showEditor: false,
      showWord: false,
      words: [],
      submiting: false
    };
  }
};
</script>

<style>
.pd-20 {
  padding: 20px 0;
}
</style>