<template> 
	<!-- :http-request="uploadSectionFile"
 :on-preview="handlePreview"
 :on-remove="handleRemove" -->
	<el-upload class="upload-demo" action="http://localhost:8081/api-file/aliyun/oss/upload" :show-file-list="false"
		:on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload"
		  list-type="picture">
		<el-button size="small" type="primary">点击上传</el-button>
		<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb
		<!-- 构建虚拟路径 -->
		<img v-if="imageUrl" :src="imageUrl" class="el-upload-list__item-thumbnail" >
		<!-- <i v-else class="el-icon-plus avatar-uploader-icon"></i> -->
		</div>
		
	</el-upload>
</template>
<script>
	export default {
		data() {
			return {
				imageUrl:""
			};
		},
		methods: {
			 //文件上传成功
			handleAvatarSuccess(res, file) {
				if(res.code==200){
					this.$message.success("图片上传成功！")
					this.imageUrl =res.data.filePath;
				}else{
					this.$message.error('图片上传失败!');
				}
				
			},
			//限制用户上传的图片格式和大小
			beforeAvatarUpload(file) {
				const isLt2M = file.size / 1024 / 1024 < 10;
				if (!isLt2M) {
					this.$message.error('上传头像图片大小不能超过 10MB!');
				}
				return isLt2M;
			}
		}
	}
</script>
<!-- <template> 
  <div>
    <el-upload
      action="http://yjlive160322.oss-cn-beijing.aliyuncs.com"
      :data="dataObj"
      list-type="picture"
      :multiple="false" :show-file-list="showFileList"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :on-remove="handleRemove"
      :on-success="handleUploadSuccess"
      :on-preview="handlePreview">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过10MB</div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="fileList[0].url" alt="">
    </el-dialog>
  </div>
</template>
<script>
  import {policy} from '@/api/oss'

  export default {
    name: 'singleUpload',
    props: {
      value: String
    },
    computed: {
      imageUrl() {
        return this.value;
      },
      imageName() {
        if (this.value != null && this.value !== '') {
          return this.value.substr(this.value.lastIndexOf("/") + 1);
        } else {
          return null;
        }
      },
      fileList() {
        return [{
          name: this.imageName,
          url: this.imageUrl
        }]
      },
      showFileList: {
        get: function () {
          return this.value !== null && this.value !== ''&& this.value!==undefined;
        },
        set: function (newValue) {
        }
      }
    },
    data() {
      return {
        dataObj: {
          policy: '',
          signature: '',
          key: '',
          ossaccessKeyId: '',
          dir: '',
          host: ''
        },
        dialogVisible: false
      };
    },
    methods: {
      emitInput(val) {
        this.$emit('input', val)
      },
      handleRemove(file, fileList) {
        this.emitInput('');
      },
      handlePreview(file) {
        this.dialogVisible = true;
      },
      beforeUpload(file) {
        let _self = this;
        return new Promise((resolve, reject) => {
          policy().then(response => {
            _self.dataObj.policy = response.data.policy;
            _self.dataObj.signature = response.data.signature;
            _self.dataObj.ossaccessKeyId = response.data.accessKeyId;
            _self.dataObj.key = response.data.dir + '/${filename}';
            _self.dataObj.dir = response.data.dir;
            _self.dataObj.host = response.data.host;
            resolve(true)
          }).catch(err => {
            console.log(err)
            reject(false)
          })
        })
      },
      handleUploadSuccess(res, file) {
        this.showFileList = true;
        this.fileList.pop();
        this.fileList.push({name: file.name, url: this.dataObj.host + '/' + this.dataObj.dir + '/' + file.name});
        this.emitInput(this.fileList[0].url);
      }
    }
  }
</script>
<style>

</style>


 -->
