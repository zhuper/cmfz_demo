package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.Star;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /***
     * 根据id带分页查询
     */
    @Override
    public Map<String, Object> selectUsersByStarId(Integer page, Integer rows, String starId) {
        User user = new User();
        user.setStar_id(starId);
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<User> list = userDao.selectByRowBounds(user, rowBounds);
        int count = userDao.selectCount(user);
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", list);
        map.put("total", count % rows == 0 ? count / rows : count / rows + 1);
        map.put("records", count);
        return map;
    }

    @Override
    public String save(User user) {
        user.setId(UUID.randomUUID().toString());
        int i = userDao.insert(user);

        if (i == 1) {
            return user.getId();
        }

        throw new RuntimeException("添加失败");
    }


    @Override
    public void delete(String id, HttpServletRequest request) {
        User user = userDao.selectByPrimaryKey(id);

        int i = userDao.deleteByPrimaryKey(id);
        if (i == 0) {
            throw new RuntimeException("删除失败");

        } else {
            //删除成功
            String photo = user.getPhoto();
            File file = new File(request.getServletContext().getRealPath("/user/img/"), photo);
            boolean b = file.delete();
            if (b == false) {
                throw new RuntimeException("删除封面失败");
            }
        }
    }

    @Override
    public void update(User user) {
        if ("".equals(user.getPhoto())){
            user.setPhoto(null);
        }

        try {
            userDao.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {
        User user =new User();
        RowBounds rowbounds = new RowBounds((page-1)*rows,rows);
        List<User> list = userDao.selectByRowBounds(user, rowbounds);
        //查询数量
        int count = userDao.selectCount(user);
        Map<String,Object>map =new HashMap<>();
        map.put("page",page);
        //每页显示多少行装进list
        map.put("rows",list);
        //总共有几页
        map.put("total",count%rows==0?count/rows:count/rows+1);
        //总共有多少条数据
        map.put("records",count);
        return map;
    }
    /**
     * 查趋势图
     * @param sex
     * @return
     */
    @Override
    public Integer[] getTrend(String sex) {
        Map<String,Integer>map = new HashMap<>();
        //获得月份装进数组
        Integer[] month ={1,2,3,4,5,6,7,8,9,10,11,12};
        //遍历
        for (int i = 0; i <month.length ; i++) {
            userDao.getDbType(sex).forEach(u->{
                //如果获取的月份不为空 就把数据装进map集合
                if (u.getMonth()!=null) {
                    map.put(u.getMonth(), u.getCount());
                }
            });
            //如果为空 就把月份对应的数量设置为空
            map.put(month[i]+"月",0);
            }
                Integer[] c = new Integer[12];
        for (int i = 0; i <c.length ; i++) {
            //根据月份获取到数量
            Integer count = map.get(month[i] + "月");

            //把数量赋值给遍历的数组
                    c[i]=count;

             }
        return c;
        }



    @Override
    public List<User> export(HttpServletRequest request) {

        List<User> list = userDao.selectAll();

        list.forEach(user -> {
            String realPath = request.getSession().getServletContext().getRealPath("/user/img");
            user.setPhoto(realPath + "/" + user.getPhoto());
        });
            return list;
    }
}




