package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Notification;
import com.lz.manage.model.vo.notification.NotificationVo;
import com.lz.manage.model.dto.notification.NotificationQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 通知信息Service接口
 * 
 * @author YY
 * @date 2026-01-14
 */
public interface INotificationService extends IService<Notification>
{
    //region mybatis代码
    /**
     * 查询通知信息
     * 
     * @param id 通知信息主键
     * @return 通知信息
     */
    public Notification selectNotificationById(Long id);

    /**
     * 查询通知信息列表
     * 
     * @param notification 通知信息
     * @return 通知信息集合
     */
    public List<Notification> selectNotificationList(Notification notification);

    /**
     * 新增通知信息
     * 
     * @param notification 通知信息
     * @return 结果
     */
    public int insertNotification(Notification notification);

    /**
     * 修改通知信息
     * 
     * @param notification 通知信息
     * @return 结果
     */
    public int updateNotification(Notification notification);

    /**
     * 批量删除通知信息
     * 
     * @param ids 需要删除的通知信息主键集合
     * @return 结果
     */
    public int deleteNotificationByIds(Long[] ids);

    /**
     * 删除通知信息信息
     * 
     * @param id 通知信息主键
     * @return 结果
     */
    public int deleteNotificationById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param notificationQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Notification> getQueryWrapper(NotificationQuery notificationQuery);

    /**
     * 转换vo
     *
     * @param notificationList Notification集合
     * @return NotificationVO集合
     */
    List<NotificationVo> convertVoList(List<Notification> notificationList);
}
