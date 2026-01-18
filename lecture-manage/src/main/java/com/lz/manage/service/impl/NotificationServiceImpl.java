package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.NotificationMapper;
import com.lz.manage.model.domain.Notification;
import com.lz.manage.model.dto.notification.NotificationQuery;
import com.lz.manage.model.enums.IsReadEnum;
import com.lz.manage.model.vo.notification.NotificationVo;
import com.lz.manage.service.INotificationService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 通知信息Service业务层处理
 *
 * @author YY
 * @date 2026-01-14
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements INotificationService {

    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询通知信息
     *
     * @param id 通知信息主键
     * @return 通知信息
     */
    @Override
    public Notification selectNotificationById(Long id) {
        return notificationMapper.selectNotificationById(id);
    }

    /**
     * 查询通知信息列表
     *
     * @param notification 通知信息
     * @return 通知信息
     */
    @Override
    public List<Notification> selectNotificationList(Notification notification) {
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId()) && !SecurityUtils.hasRole("manage")) {
            notification.setUserId(SecurityUtils.getUserId());
        }
        List<Notification> notifications = notificationMapper.selectNotificationList(notification);
        for (Notification info : notifications) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
        }
        return notifications;
    }

    /**
     * 新增通知信息
     *
     * @param notification 通知信息
     * @return 结果
     */
    @Override
    public int insertNotification(Notification notification) {
        notification.setCreateTime(DateUtils.getNowDate());
        return notificationMapper.insertNotification(notification);
    }

    /**
     * 修改通知信息
     *
     * @param notification 通知信息
     * @return 结果
     */
    @Override
    public int updateNotification(Notification notification) {
        //如果是已读
        if (notification.getReadFlag().equals(IsReadEnum.IS_READ_1.getValue())) {
            notification.setReadTime(DateUtils.getNowDate());
        }
        return notificationMapper.updateNotification(notification);
    }

    /**
     * 批量删除通知信息
     *
     * @param ids 需要删除的通知信息主键
     * @return 结果
     */
    @Override
    public int deleteNotificationByIds(Long[] ids) {
        return notificationMapper.deleteNotificationByIds(ids);
    }

    /**
     * 删除通知信息信息
     *
     * @param id 通知信息主键
     * @return 结果
     */
    @Override
    public int deleteNotificationById(Long id) {
        return notificationMapper.deleteNotificationById(id);
    }

    //endregion
    @Override
    public QueryWrapper<Notification> getQueryWrapper(NotificationQuery notificationQuery) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = notificationQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = notificationQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long userId = notificationQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        String title = notificationQuery.getTitle();
        queryWrapper.like(StringUtils.isNotEmpty(title), "title", title);

        String readFlag = notificationQuery.getReadFlag();
        queryWrapper.eq(StringUtils.isNotEmpty(readFlag), "read_flag", readFlag);

        Date createTime = notificationQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<NotificationVo> convertVoList(List<Notification> notificationList) {
        if (StringUtils.isEmpty(notificationList)) {
            return Collections.emptyList();
        }
        return notificationList.stream().map(NotificationVo::objToVo).collect(Collectors.toList());
    }

}
