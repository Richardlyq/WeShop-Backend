package com.atguigu.spzx.manager.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @ClassName ExcelListener
 * @Description
 * @Author Richard
 * @Date 2023-12-21 10:54
 **/

/*
 * @description: 为什么Listener 不能让spring管理?
 * 在读取excel的时候，会回调com.alibaba.excel.read.listener.ReadListener#invoke的方法，
 * 而spring如果管理Listener会导致Listener 变成了单例，
 * 在有并发读取文件的情况下都会回调同一个Listener，
 * 就无法区分是哪个文件读取出来的了。
 * @author: liyuqi
 * @date:  10:57 2023/12/21
 * @param:
 * @return:
 **/
public class ExcelListener<T> implements ReadListener<T> {

    /**
     每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    //获取mapper对象,因为不能用Spring管理，所以只能通过构造方法的形式传入
    private CategoryMapper categoryMapper;
    public ExcelListener(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }

    // 每解析一行数据就会调用一次该方法
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        CategoryExcelVo data = (CategoryExcelVo) t;
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if(cachedDataList.size() >= BATCH_COUNT ){
            saveData();
            //存储完成清理list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //如果数据量太少，进入不了invoke方法中的saveData方法，则在解析完excel后调用该方法进行保存数据
        saveData();
    }

    private void saveData() {
        categoryMapper.batchInsert((List<CategoryExcelVo>)cachedDataList);
    }
}
