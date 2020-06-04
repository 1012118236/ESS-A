package com.ning.service.service.impl;

import com.ning.service.entity.EmployeeES;
import com.ning.service.service.IEmployeeSearchService;
import com.ning.service.vo.ResData;
import org.springframework.stereotype.Service;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/7/25 14:47
 */
@Service("employeeSearchServiceImpl")
public class EmployeeSearchServiceImpl implements IEmployeeSearchService {

    //@Autowired
    //private EmployeeSearchDao employeeSearchDao;

    /**
     * 查询标题
     * @param title
     * @return
     */
    @Override
    public ResData searchTitle(String title) {
//        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
//                .must(QueryBuilders.matchQuery("title",title));
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
//        Page<EmployeeES> search = employeeSearchDao.search(searchQuery);
//        List<EmployeeES> content = search.getContent();
  //      return new ResData(200,content,"成功");
        return null;
    }

    /**
     * 增加文章
     * @param employeeES
     */
    @Override
    public void add(EmployeeES employeeES){

        //employeeSearchDao.save(employeeES);
    }
}
