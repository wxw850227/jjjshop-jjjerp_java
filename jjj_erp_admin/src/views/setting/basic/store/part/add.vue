<template>
  <el-dialog
    title="添加店铺"
    v-model="dialogVisible"
    @close="dialogFormVisible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :fullscreen="fullscreen"
    :show-close="false"
    align-center
    append-to="#right-content-box"
  >
    <template #header="{ close, titleId, titleClass }">
      <div class="my-header d-b-c">
        <div :id="titleId" :class="titleClass" class="flex-1">添加店铺</div>
        <el-icon @click="fullscreen = !fullscreen"><Plus /></el-icon>
        <el-icon @click="close"><Close /></el-icon>
      </div>
    </template>
    <div>
      <el-form size="small" :model="form" :rules="formRules" ref="form">
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="其他平台"
              prop="mfrs"
              :label-width="formLabelWidth"
            >
              <el-select
                v-model="form.mfrs"
                style="width: 100%"
                placeholder="选择平台"
              >
                <el-option
                  v-for="(item, index) in storeType"
                  :key="index"
                  :label="item.name"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="店铺昵称"
              prop="nickName"
              :label-width="formLabelWidth"
            >
              <el-input v-model="form.nickName" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="省市区"
              prop="regions"
              :label-width="formLabelWidth"
            >
              <el-cascader
                style="width: 100%"
                clearable
                v-model="form.regions"
                :options="areaData"
                :props="regionParams"
                @change="handleChange1"
              ></el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="详细地址"
              prop="address"
              :label-width="formLabelWidth"
            >
              <el-input v-model="form.address" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="店铺名称"
              prop="storeName"
              :label-width="formLabelWidth"
            >
              <el-input v-model="form.storeName" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="店铺网址"
              prop="website"
              :label-width="formLabelWidth"
            >
              <el-input v-model="form.website" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="固话"
              prop="fixPhone"
              :label-width="formLabelWidth"
            >
              <el-input v-model="form.fixPhone" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="联系人"
              prop="contacts"
              :label-width="formLabelWidth"
            >
              <el-input v-model="form.contacts" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row> </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="手机"
              prop="phone"
              :label-width="formLabelWidth"
            >
              <el-input v-model="form.phone" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              prop="accountId"
              label="结算账户"
              :label-width="formLabelWidth"
            >
              <el-select
                v-model="form.accountId"
                style="width: 100%"
                placeholder="请选择"
              >
                <el-option
                  v-for="(item, index) in accountList"
                  :key="index"
                  :label="item.accountName"
                  :value="item.accountId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-col :span="24">
          <el-form-item
            label="宣传标语"
            prop="description"
            :label-width="formLabelWidth"
          >
            <el-input
              type="textarea"
              v-model="form.description"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-form>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogFormVisible">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import SettingApi from "@/api/setting.js";
import FinanceApi from "@/api/finance.js";
export default {
  components: {},
  data() {
    return {
      form: {
        parentId: "0",
        name: "",
        sort: 100,
        imageId: "",
        type: 0,
        multiUnit: false, // 添加多单位字段
        barCode: "",
        skuOne: "",
        skuTwo: "",
        skuThree: "",
        categoryId: "",
        storeName: "",
        nickName: "",
        regions: [],
      },
      value1: "",
      fullscreen: false,
      config: {},
      unitList: [],
      categoryList: [],
      mfrsList: [],
      accountList: [],
      specOptions2: [], // 与 skuTwo 相关的选择值数组
      specOptions3: [], // 与 skuThree 相关的选择值数组
      options: [],
      options1: [],
      options2: [],
      options3: [],
      formRules: {
        mfrs: [
          {
            required: true,
            message: "请选择平台",
            trigger: "blur",
          },
        ],
        nickName: [
          {
            required: true,
            message: "请输入店铺昵称",
            trigger: "blur",
          },
        ],
        regions: [
          {
            required: true,
            message: "请选择地址",
            trigger: "blur",
          },
        ],
        storeName: [
          {
            required: true,
            message: "请输入店铺名称",
            trigger: "blur",
          },
        ],
        accountId: [
          {
            required: true,
            message: "请选择结算账户",
            trigger: "blur",
          },
        ],
      },
      /*左边长度*/
      formLabelWidth: "110px",
      /*是否显示*/
      dialogVisible: false,
      loading: false,
      /*是否上传图片*/
      isProductUpload: false,
      skuOneTitle: "属性1",
      skuTwoTitle: "属性2",
      skuThreeTitle: "属性3",
      regionParams: {
        label: "name",
        value: "id",
        children: "children",
        expandTrigger: "hover",
      },
      skuOneList: [],
      skuTwoList: [],
      skuThreeList: [],
      activeTab: "basic", // 默认显示基本信息标签页
      specNames: [], // 存储多规格选项名称
      specOptions: [], // 存储多规格选项的选择值
      manySkuSelected: 0,
      areaData: [],
      storeType: [],
    };
  },
  props: ["open_add", "addform"],
  created() {
    this.dialogVisible = this.open_add;
    this.getdata();
    this.getselect();
  },
  methods: {
    getselect() {
      FinanceApi.accountList({})
        .then((res) => {
          this.accountList = res.data.records;
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getdata() {
      SettingApi.regionList().then((res) => {
        this.areaData = res.data;
      });
      SettingApi.storeToAdd().then((res) => {
        this.storeType = res.data.storeType;
      });
    },

    handleChange1(val) {
      console.log(val, "val");
      const province = this.areaData.find((item) => item.id === val[0]);
      const city = province.children.find((item) => item.id === val[1]);
      const region = city.children.find((item) => item.id === val[2]);
      this.form.provinceId = province.id;
      this.form.cityId = city.id;
      this.form.regionId = region.id || "";
      this.form.province = province.name;
      this.form.city = city.name;
      this.form.region = region.name;
    },
    /*添加用户*/
    addUser() {
      let self = this;
      let params = self.form;
      console.log(params, "params");
      self.$refs.form.validate((valid) => {
        if (valid) {
          self.loading = true;
          SettingApi.storeAdd(params, true)
            .then((data) => {
              self.loading = false;
              ElMessage({
                message: "添加成功",
                type: "success",
              });
              self.dialogFormVisible(true);
            })
            .catch((error) => {
              self.loading = false;
            });
        }
      });
    },

    /*关闭弹窗*/
    dialogFormVisible(e) {
      if (e) {
        this.$emit("closeDialog", {
          type: "success",
          openDialog: false,
        });
      } else {
        this.$emit("closeDialog", {
          type: "error",
          openDialog: false,
        });
      }
    },
    /*上传*/
    openUpload() {
      this.isProductUpload = true;
    },
    deleteImg(index) {
      this.form.imageList.splice(index, 1);
    },
    /*获取图片*/
    returnImgsFunc(e) {
      console.log(this.form);
      if (e != null) {
        let imgs = this.form.imageList.concat(e);
        this.form.imageList = imgs;
        // 兼容后端
        for (let i = 0; i < imgs.length; i++) {
          if (typeof imageId == "undefined") {
            imgs[i].imageId = imgs[i].fileId;
          }
        }
      }
      this.isProductUpload = false;
      console.log(this.form.imageList, "this.form.imageList ");
    },
  },
};
</script>

<style scoped>
.img {
  margin-top: 10px;
}

.btns {
  display: flex;
  flex-direction: row;
  width: 109px;
  height: 36px;
  font-size: 14px;
  text-align: center;

  .thebtn1 {
    color: #ffffff;
    background: #43aa8b;
  }
}
</style>
