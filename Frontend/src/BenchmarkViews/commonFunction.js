import { request } from '../request'
import pinia from '../store/store';
import { usePaginationStore } from '../store/paginationStore'
import { useBenchmarkModelsStore } from '../store/BenchmarkModelsStore'
import { useModuleStore } from '../store/module';
import { usecurrentBenchmarkModelStore } from '../store/currentBenchmarkModelStore'
import { ElLoading } from 'element-plus'
import JSZip from "jszip";
import FileSaver from "file-saver";

const currentBenchmarkModel = usecurrentBenchmarkModelStore(pinia)

const moduleStore = useModuleStore(pinia)
const PaginationStore = usePaginationStore(pinia)
const BenchmarkModels = useBenchmarkModelsStore(pinia)


const listAllBenchmarkModels = async () => {
  moduleStore.CurrentModule = 'Benchmark Models'
  moduleStore.CurrentModuleDetails = ''
  moduleStore.CurrentRoute = 'Benchmark_Models'
  PaginationStore.pagenum = 1
  PaginationStore.pagesize = 25
  PaginationStore.searchkeywords = ''
  PaginationStore.column = ''
  BenchmarkModels.models.length = 0
  // 计算偏移量
  const paginationOffset = (PaginationStore.pagenum - 1) * PaginationStore.pagesize;
  // 更新全局偏移量
  BenchmarkModels.paginationOffset = paginationOffset;
  try {
    const listAllBenchmarkModelsRes = await request({
      url: 'benchmark/listBenchmarkModels',
      method: 'POST',
      data: {
        pagenum: PaginationStore.pagenum,
        pagesize: PaginationStore.pagesize
      }
    })
    // console.log("listAllBenchmarkModelsRes", listAllBenchmarkModelsRes);
    BenchmarkModels.models.length = 0;
    BenchmarkModels.searchKeyWords = '';
    BenchmarkModels.models.push(...listAllBenchmarkModelsRes.res.records);
    BenchmarkModels.total = listAllBenchmarkModelsRes.total;
    PaginationStore.total = listAllBenchmarkModelsRes.total;
  } catch (error) {
    console.error("错误是", error);
  }
}

const searchModel = async () => {
  let loadingInstance = ElLoading.service({ fullscreen: true });

  PaginationStore.pagenum = 1
  PaginationStore.pagesize = 25
  moduleStore.CurrentModuleDetails = PaginationStore.searchkeywords

  BenchmarkModels.searchKeyWords = PaginationStore.searchkeywords

    try {

      const res = await request({
        url: 'benchmark/search',
        method: 'POST',
        data: {
          pagenum: PaginationStore.pagenum,
          pagesize: PaginationStore.pagesize,
          searchkeywords: PaginationStore.searchkeywords,
          column: PaginationStore.column
        }
      });

      // console.log("res", res);
      BenchmarkModels.models.length = 0;
      BenchmarkModels.models.push(...res.res.records);
      PaginationStore.pagenum = res.res.current;
      PaginationStore.total = res.res.total;

      BenchmarkModels.total = res.res.total;

      // 计算偏移量
      const paginationOffset = (PaginationStore.pagenum - 1) * PaginationStore.pagesize;
      // console.log("计算偏移量偏移量",paginationOffset)
      // 更新全局偏移量
      BenchmarkModels.paginationOffset = paginationOffset;


      loadingInstance.close();

    } catch (error) {
      console.log("搜索错误", error)
      loadingInstance.close();


    }

  
}



const clearSearch = async () => {
  let loadingInstance = ElLoading.service({ fullscreen: true });
  await listAllBenchmarkModels();
  loadingInstance.close();

}

const listModelInfo = async (column,searchkeywords)=>{
  let loadingInstance = ElLoading.service({ fullscreen: true });

    try {
      const res = await request({
        url: 'benchmark/search',
        method: 'POST',
        data: {
          pagenum: PaginationStore.pagenum,
          pagesize: PaginationStore.pagesize,
          searchkeywords,
          column
        }
      });
      // console.log("res.res.records",res.res.records)
      currentBenchmarkModel.model.modelname=res.res.records[0].modelname
      currentBenchmarkModel.model.modeltype=res.res.records[0].modeltype
      currentBenchmarkModel.model.benchmarkset=res.res.records[0].benchmarkset
      currentBenchmarkModel.model.modeldescription=res.res.records[0].modeldescription
      currentBenchmarkModel.model.reference=res.res.records[0].reference
      currentBenchmarkModel.model.referenceyear=res.res.records[0].referenceyear
      currentBenchmarkModel.model.field=res.res.records[0].field
      currentBenchmarkModel.model.parameter=res.res.records[0].parameter
      currentBenchmarkModel.model.maximumvalue=res.res.records[0].maximumvalue
      currentBenchmarkModel.model.cons=res.res.records[0].cons
      currentBenchmarkModel.model.constrainedparameter=res.res.records[0].constrainedparameter
      currentBenchmarkModel.model.raw=res.res.records[0].raw
      currentBenchmarkModel.model.valid=res.res.records[0].valid
      currentBenchmarkModel.model.proportion=res.res.records[0].proportion
      currentBenchmarkModel.model.modelid=res.res.records[0].modelid

      loadingInstance.close();

    } catch (error) {
      console.log("搜索错误", error)

      loadingInstance.close();

    }



}

const getResearchFieldColor = (field) => {
  switch (field) {
    case 'Model':
      return 'badge bg-success';
    case 'Generation':
      return 'badge bg-primary';
    case 'Optimization':
      return 'badge bg-warning';
    case 'Diagnosis':
      return 'badge bg-danger';
    case 'Evaluation':
      return 'badge bg-dark';
    case 'Application':
      return 'badge bg-info';
    case 'Other':
      return 'badge bg-secondary';
  }
}

// 下载文件并打包成ZIP
const downloadAndZipFiles = async (fileUrls,modelName) => {
  // 创建一个新的JSZip实例
  let zip = new JSZip();

  // 循环遍历文件地址数组
  for (let i = 0; i < fileUrls.length; i++) {
      let url = fileUrls[i];
      let filename = url.substring(url.lastIndexOf('/') + 1);

      // 使用fetch获取文件内容
      let response = await fetch(url);
      let blob = await response.blob();

      // 将文件添加到ZIP中
      zip.file(filename, blob);
  }

  // 生成ZIP文件并将其下载
  zip.generateAsync({ type: 'blob' })
      .then((content) => {
        let a = document.createElement('a');
          a.href = URL.createObjectURL(content);
          a.download = modelName +'.zip'; // 设置ZIP文件的下载名称
          a.click();
      });
};


export { listAllBenchmarkModels, searchModel, clearSearch, getResearchFieldColor,listModelInfo,downloadAndZipFiles}
