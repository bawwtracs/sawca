"use strict";

var IndexDBApi = (function() {
  /**
   * 获取当前时间戳
   *
   * @returns timestamp
   */
  const timeStamp = function() {
    return new Date().getTime();
  };

  /**
   * 异常日志输出
   *
   * @param {*} msg
   */
  const showErrorMsg = function(msg) {
    console.error(`[indexdb-api]: ${msg}`);
  };

  /**
   * indexDB API
   * 将原生臃肿的使用方法封装起来，使分离业务逻辑代码，使繁琐的缓存操作更加清晰可读，增加存取性能、维护性，复用性。
   * 修改自仓库 https://github.com/Saul-Shen/indexdb-api.git
   * 修改内容：将模块化修改为简单的面向对象闭包封装，使适用于非模块化项目。
   * 封装于2019/09/30，最新0.23版本，最新commit为2019/01/02
   * 备注：
   * 1.适用于支持es6语法的浏览器环境。
   * 2.了解es6、Promise、async、await、IndexDB，可食用本插件。
   * 3.包含三个模块：database(数据库)、store(表)、data(数据)，从上往下整合。
   * @returns
   */
  var IndexDBApi = function IndexDBApi() {
    if (!self.indexedDB) {
      showErrorMsg("Your browser doesnt support indexedDB.");
      return false;
    }
    return this;
  };

  //连接池
  IndexDBApi.prototype.DBs = {};

  /**
   * 获取数据库连接
   *
   * @param {*} dbName
   * @param {*} [listener={}]
   * @param {*} [version=timeStamp()]
   * @returns
   */
  IndexDBApi.prototype.openDB = function(
    dbName,
    listener,
    version = timeStamp()
  ) {
    const onupgradeneeded = listener;
    this.closeDB(dbName);
    return new Promise((resolve, reject) => {
      const req = indexedDB.open(dbName, version);
      req.onsuccess = event => {
        this.DBs[dbName] = event.target.result;
        resolve(event.target.result);
      };
      req.onupgradeneeded = onupgradeneeded;
      req.onerror = reject;
    });
  };

  /**
   * 关闭连接
   *
   * @param {*} dbName
   * @returns
   */
  IndexDBApi.prototype.closeDB = function(dbName) {
    const curDB = this.DBs[dbName];
    if (curDB) {
      curDB.close();
      delete this.DBs[dbName];
      return Promise.resolve(true);
    } else {
      return Promise.resolve();
    }
  };

  /**
   * 删除数据库
   *
   * @param {*} dbName
   * @returns
   */
  IndexDBApi.prototype.deleteDB = async function(dbName) {
    await this.closeDB(dbName);
    return new Promise((resolve, reject) => {
      const dbConnect = indexedDB.deleteDatabase(dbName);
      dbConnect.onsuccess = resolve;
      dbConnect.onerror = reject;
    });
  };

  /**
   * 获取已连接数据库对象
   *
   * @param {*} dbName
   * @returns
   */
  IndexDBApi.prototype.getDB = function(dbName) {
    const curDB = this.DBs[dbName];
    if (curDB) {
      return Promise.resolve(curDB);
    } else {
      return Promise.reject(new Error(`please open ${dbName} DB first`));
    }
  };

  /**
   * 创建仓库
   * 指定version的store存在则return，不存在则按照传入参数配置创建store
   * 传入的store配置为原生api的配置
   *
   * @param {*} dbName
   * @param {*} storeName
   * @param {*} { keyOptions = {}, indexes = [] }
   * @param {*} [version=timeStamp()]
   */
  IndexDBApi.prototype.createStore = async function(
    dbName,
    storeName,
    { keyOptions = {}, indexes = [] },
    version = timeStamp()
  ) {
    let store;
    await this.openDB(
      dbName,
      {
        onupgradeneeded: event => {
          const db = event.target.result;
          if (db.objectStoreNames.contains(storeName)) {
            return;
          }
          store = db.createObjectStore(storeName, keyOptions);
          indexes.forEach(e =>
            store.createIndex(e.indexName, e.indexKey, e.indexOptions)
          );
        }
      },
      version
    ).catch(err => console.log(err));
  };

  /**
   * 获取仓库
   *
   * @param {*} dbName
   * @param {*} storeName
   * @returns
   */
  IndexDBApi.prototype.getStore = async function(dbName, storeName) {
    const curDB = await this.getDB(dbName);
    return new Promise((resolve, reject) => {
      if (curDB.objectStoreNames.contains(storeName)) {
        // you should create transaction before do anything to store
        const transaction = curDB.transaction(storeName, "readwrite");
        const store = transaction.objectStore(storeName);
        resolve(store);
      } else {
        reject(new Error("no this store"));
      }
    });
  };

  /**
   * 删除仓库
   *
   * @param {*} dbName
   * @param {*} storeName
   * @param {*} [version=timeStamp()]
   * @returns
   */
  IndexDBApi.prototype.deleteStore = async function(
    dbName,
    storeName,
    version = timeStamp()
  ) {
    await this.openDB(
      dbName,
      {
        onupgradeneeded: event => {
          const db = event.target.result;
          if (db.objectStoreNames.contains(storeName)) {
            db.deleteObjectStore(storeName);
          }
        }
      },
      version
    );
    return true;
  };

  /**
   * 获取仓库数据总量
   *
   * @param {*} dbName
   * @param {*} storeName
   * @returns
   */
  IndexDBApi.prototype.getStoreCount = async function(dbName, storeName) {
    const store = await this.getStore(dbName, storeName);
    return new Promise((resolve, reject) => {
      const req = store.count();
      req.onsuccess = event => {
        const count = event.target.result;
        resolve(count);
      };
      req.onerror = reject;
    });
  };

  /**
   * 清空仓库
   *
   * @param {*} dbName
   * @param {*} storeName
   * @returns
   */
  IndexDBApi.prototype.clearStore = async function(dbName, storeName) {
    const store = await this.getStore(dbName, storeName);
    if (store) {
      store.clear();
      return true;
    }
  };

  /**
   * 获取仓库所有数据
   *
   * @param {*} dbName
   * @param {*} storeName
   * @returns
   */
  IndexDBApi.prototype.getAllData = async function(dbName, storeName) {
    const store = await this.getStore(dbName, storeName);
    return new Promise((resolve, reject) => {
      const dataArr = [];
      const req = store.openCursor();
      req.onsuccess = event => {
        const cursor = event.target.result;
        if (cursor) {
          dataArr.push(cursor.value);
          cursor.continue();
        } else {
          resolve(dataArr);
        }
      };
      req.onerror = reject;
    });
  };

  /**
   * 根据索引查询单条数据
   *
   * @param {*} dbName
   * @param {*} storeName
   * @param {*} indexName
   * @param {*} value
   * @returns
   */
  IndexDBApi.prototype.getDataByIndex = async function(
    dbName,
    storeName,
    indexName,
    value
  ) {
    const store = await this.getStore(dbName, storeName);
    return new Promise((resolve, reject) => {
      const index = store.index(indexName);
      const req = index.get(value);
      req.onsuccess = event => {
        resolve(event.target.result);
      };
      req.onerror = reject;
    });
  };

  /**
   * 指定主键范围查询
   *
   * @param {*} dbName
   * @param {*} storeName
   * @param {*} start
   * @param {*} end
   * @returns
   */
  IndexDBApi.prototype.getRangeDataByPrimaryKey = async function(
    dbName,
    storeName,
    start,
    end
  ) {
    const store = await this.getStore(dbName, storeName);
    return new Promise((resolve, reject) => {
      const range = IDBKeyRange.bound(start, end);
      const dataArr = [];
      const req = store.openCursor(range);
      req.onsuccess = event => {
        const cursor = event.target.result;
        if (cursor) {
          dataArr.push(cursor.value);
          cursor.continue();
        } else {
          resolve(dataArr);
        }
      };
      req.onerror = reject;
    });
  };

  /**
   * 主键查询一条
   *
   * @param {*} dbName
   * @param {*} storeName
   * @param {*} key
   * @returns
   */
  IndexDBApi.prototype.getDataByPrimaryKey = async function(
    dbName,
    storeName,
    key
  ) {
    const store = await this.getStore(dbName, storeName);
    return new Promise((resolve, reject) => {
      const req = store.get(key);
      req.onsuccess = event => {
        resolve(event.target.result);
      };
      req.onerror = reject;
    });
  };

  /**
   * 新增
   *
   * @param {*} dbName
   * @param {*} storeName
   * @param {*} data
   * @returns
   */
  IndexDBApi.prototype.addOneData = async function(dbName, storeName, data) {
    const store = await this.getStore(dbName, storeName);
    return new Promise((resolve, reject) => {
      const req = store.add(data);
      req.onsuccess = event => {
        // event.target.result is the count of the data
        resolve(event.target.result);
      };
      req.onerror = reject;
    });
  };

  /**
   * 更新一个
   *
   * @param {*} dbName
   * @param {*} storeName
   * @param {*} data
   * @returns
   */
  IndexDBApi.prototype.putOneData = async function(dbName, storeName, data) {
    const store = await this.getStore(dbName, storeName);
    return new Promise((resolve, reject) => {
      const req = store.put(data);
      req.onsuccess = event => {
        resolve(event.target.result);
      };
      req.onerror = reject;
    });
  };

  /**
   * 删除id
   *
   * @param {*} dbName
   * @param {*} storeName
   * @param {*} primaryKeyValue
   * @returns
   */
  IndexDBApi.prototype.deleteDataByPrimaKey = async function(
    dbName,
    storeName,
    primaryKeyValue
  ) {
    const store = await this.getStore(dbName, storeName);
    if (store) {
      store.delete(primaryKeyValue);
      return true;
    }
  };

  return IndexDBApi;
})();
