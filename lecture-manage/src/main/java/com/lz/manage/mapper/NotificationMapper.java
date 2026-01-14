package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.Notification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 通知信息Mapper接口
 * 
 * @author YY
 * @date 2026-01-14
 */
public interface NotificationMapper extends BaseMapper<Notification>
{
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
     * 删除通知信息
     * 
     * @param id 通知信息主键
     * @return 结果
     */
    public int deleteNotificationById(Long id);

    /**
     * 批量删除通知信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNotificationByIds(Long[] ids);
}
