package com.gtx.sell.service.impl;

import com.gtx.sell.converter.OrderMaster2OrderDTOConverter;
import com.gtx.sell.dao.OrderDetail;
import com.gtx.sell.dao.OrderMaster;
import com.gtx.sell.dao.ProductInfo;
import com.gtx.sell.dto.CartDTO;
import com.gtx.sell.dto.OrderDTO;
import com.gtx.sell.enums.OrderStatusEnum;
import com.gtx.sell.enums.PayStatusEnum;
import com.gtx.sell.enums.ResultEnum;
import com.gtx.sell.exception.SellException;
import com.gtx.sell.repository.OrderDetailRepository;
import com.gtx.sell.repository.OrderMasterRepository;
import com.gtx.sell.service.*;
import com.gtx.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessage pushMessage;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        // 创建订单
        // 查询商品 数量 价格
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());

            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            // 计算订单总价

            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);


            // 订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);

        }
        // 写入订单数据库 OrderMaster OrderDetail

        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster); // 出现了拷贝覆盖掉的问题 null也会

        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);


        // 减少库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
            new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);

        // 发送websocket消息
        webSocket.sendMessage("有新的订单");

        return orderDTO;

    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);

        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();

        BeanUtils.copyProperties(orderMaster, orderDTO);

        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyeropenId, Pageable pageable)   {

        Page<OrderMaster> orderMasterList = orderMasterRepository.findByBuyerOpenid(buyeropenId, pageable);

        List<OrderDTO> orderDTOList =  OrderMaster2OrderDTOConverter.convert(orderMasterList.getContent());

        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterList.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        // 判断订单状态
        OrderMaster orderMaster = new OrderMaster();




        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("[取消订单]状态不正确 orderId = {}, orderStatus = {}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode()); // 仅仅在ordermaster中修改是不够的 需要copy到dto
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result =  orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("[取消订单]更新失败， OrderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[取消订单]订单中无商品详情 orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(
                e -> new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);


        // 如果已经支付 退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // 退款是无法进行的 没有key
            // 这边顾及到了支付后的退款操作
            // payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[订单完结] 订单状态错误 orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster result = orderMasterRepository.save(orderMaster);

        if (result == null) {
            log.error("[订单完结] 订单状态更改错误 orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        // 推送微信模板消息

        pushMessage.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[订单支付] 订单不是新订单 orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        // 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("[订单支付] 订单已经支付过了 orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        // 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

        OrderMaster result = orderMasterRepository.save(orderMaster);

        if (result == null) {
            log.error("[订单支付] 订单支付状态更改错误 orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;

    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasters.getContent());

        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasters.getTotalElements());
    }
}
