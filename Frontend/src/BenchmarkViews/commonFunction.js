import { request } from '../request'
import pinia from '../store/store';
import { usePaginationStore } from '../store/paginationStore'
import { useBenchmarkModelsStore } from '../store/BenchmarkModelsStore'
import { useModuleStore } from '../store/module';
import { ElLoading } from 'element-plus'


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
  moduleStore.CurrentModule = 'Benchmark Search'
  PaginationStore.pagenum = 1
  PaginationStore.pagesize = 25
  moduleStore.CurrentModuleDetails = PaginationStore.searchkeywords

  BenchmarkModels.models.length = 0
  BenchmarkModels.searchKeyWords = PaginationStore.searchkeywords

  try {
    const res = await request({
      url: 'benchmark/search',
      method: 'POST',
      data: {
        pagenum: PaginationStore.pagenum,
        pagesize: PaginationStore.pagesize,
        searchkeywords: PaginationStore.searchkeywords,
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
    console.log("搜索错误",error)
    loadingInstance.close();


  }
}

const clearSearch = async () => {
  let loadingInstance = ElLoading.service({ fullscreen: true });
  await listAllBenchmarkModels();
  loadingInstance.close();

}

export { listAllBenchmarkModels, searchModel, clearSearch }
