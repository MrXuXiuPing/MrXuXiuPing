//package com.mallplus.provider.mapper;
//
//import com.mallplus.provider.request.OrderRecord;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
///**
// * 下单mapper
// */
//@Mapper
//public interface SeckillMapper {
//
//    /**
//     * 下单时商品库存减一
//     * @param goodsId
//     * @return
//     */
//    public int reduceGoodsStockById(@Param("goodsId") String goodsId);
//
//    /**
//     * 插入下单记录
//     * @param orderRecord
//     */
//    public void insertOrderRecord(OrderRecord orderRecord);
//
//    /**
//     * 根据订单id查询该订单是否是未支付状态
//     * @param orderId
//     * @return
//     */
//    public OrderRecord selectNoPayOrderById(String orderId);
//
//    /**
//     * 更新支付状态
//     * @param
//     */
//    public void updatePayStatus(OrderRecord orderRecord);
//}
