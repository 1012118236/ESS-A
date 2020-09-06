package com.ning.service.service.impl;

import com.ning.service.entity.Message;
import com.ning.service.mapper.MessageMapper;
import com.ning.service.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shenjiang
 * @since 2020-09-05
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
