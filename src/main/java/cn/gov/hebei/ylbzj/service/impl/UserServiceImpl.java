package cn.gov.hebei.ylbzj.service.impl;

import cn.gov.hebei.ylbzj.dao.UserDao;
import cn.gov.hebei.ylbzj.entity.UserDO;
import cn.gov.hebei.ylbzj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDO get(Integer id) {
        return userDao.get(id);
    }

    @Override
    public List<UserDO> list(Map<String, Object> map) {
        return userDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userDao.count(map);
    }

    @Override
    public int save(UserDO user) {
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        return userDao.save(user);
    }

    @Override
    public int update(UserDO user) {
        return userDao.update(user);
    }

    @Override
    public int remove(Integer id) {
        return userDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return userDao.batchRemove(ids);
    }

    @Override
    public UserDO selectByName(String username) {
        return userDao.selectByName(username);
    }

}
