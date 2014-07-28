package com.taobao.tae.Mshopping.demo.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by xinyuan on 14/7/9.
 */
public class ItemOrderModel implements Serializable {

    //收获地址
    private Address address;
    //商品信息
    private ItemInfo itemInfo;
    //配送方式
    private DeliveryMethod deliveryMethod;
    //服务
    private Service service;
    //卖家留言
    private LeaveMessage leaveMessage;
    //商品优惠
    private ItemPromotion itemPromotion;
    //商品小计
    private ItemPay itemPay;
    //商品总计
    private OrderPay orderPay;
    //库存
    private Quantity quantity;
    //代付
    private AgencyPay agencyPay;
    //淘金币
    private TbGold tbGold;
    //匿名购买
    private Anonymous anonymous;
    //天猫点券
    private CouponCard couponCard;

    private String linkage;

    private Map<String, String> rootStructureMap;
    private Map<String, String> orderStructureMap;
    private Map<String, String> itemStructureMap;

    public String getCreateOrderRequiredJson() {
        JSONObject requiredJsonObject = new JSONObject();
        try {
            JSONObject dataJsonObject = new JSONObject();
            if (deliveryMethod != null && deliveryMethod.getSubmit()) {
                String deliveryJson = deliveryMethod.toJson();
                dataJsonObject.put(orderStructureMap.get("deliveryMethod"), new JSONObject(deliveryJson));
            }

            if (service != null && service.getSubmit()) {
                String serviceJson = service.toJson();
                dataJsonObject.put(orderStructureMap.get("service"), new JSONObject(serviceJson));
            }

            if (quantity != null && quantity.getSubmit()) {
                String quantityJson = quantity.toJson();
                dataJsonObject.put(itemStructureMap.get("quantity"), new JSONObject(quantityJson));
            }

            if (leaveMessage != null && leaveMessage.getSubmit()) {
                String memoJson = leaveMessage.toJson();
                dataJsonObject.put(orderStructureMap.get("memo"), new JSONObject(memoJson));
            }

            if (agencyPay != null && agencyPay.getSubmit()) {
                String agencyJson = agencyPay.toJson();
                dataJsonObject.put(rootStructureMap.get("agencyPay"), new JSONObject(agencyJson));
            }

            if (tbGold != null && tbGold.getSubmit()) {
                String tbGoldJson = tbGold.toJson();
                dataJsonObject.put(rootStructureMap.get("tbGold"), new JSONObject(tbGoldJson));
            }

            if (address != null && address.getSubmit()) {
                String addressJson = address.toJson();
                dataJsonObject.put(rootStructureMap.get("address"), new JSONObject(addressJson));
            }

            if (anonymous != null && anonymous.getSubmit()) {
                String anonymousJson = anonymous.toJson();
                dataJsonObject.put(rootStructureMap.get("anonymous"), new JSONObject(anonymousJson));
            }

            if (couponCard != null && couponCard.getSubmit()) {
                String couponCardJson = couponCard.toJson();
                dataJsonObject.put(rootStructureMap.get("couponCard"), new JSONObject(couponCardJson));
            }

            requiredJsonObject.put("data", dataJsonObject);
            requiredJsonObject.put("linkage", new JSONObject(linkage));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requiredJsonObject.toString();
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LeaveMessage getLeaveMessage() {
        return leaveMessage;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setLeaveMessage(LeaveMessage leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    public ItemPay getItemPay() {
        return itemPay;
    }

    public void setItemPay(ItemPay itemPay) {
        this.itemPay = itemPay;
    }

    public OrderPay getOrderPay() {
        return orderPay;
    }

    public void setOrderPay(OrderPay orderPay) {
        this.orderPay = orderPay;
    }


    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    public ItemPromotion getItemPromotion() {
        return itemPromotion;
    }

    public void setItemPromotion(ItemPromotion itemPromotion) {
        this.itemPromotion = itemPromotion;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public AgencyPay getAgencyPay() {
        return agencyPay;
    }

    public void setAgencyPay(AgencyPay agencyPay) {
        this.agencyPay = agencyPay;
    }

    public TbGold getTbGold() {
        return tbGold;
    }

    public void setTbGold(TbGold tbGold) {
        this.tbGold = tbGold;
    }

    public String getLinkage() {
        return linkage;
    }

    public void setLinkage(String linkage) {
        this.linkage = linkage;
    }


    public Map<String, String> getRootStructureMap() {
        return rootStructureMap;
    }

    public void setRootStructureMap(Map<String, String> rootStructureMap) {
        this.rootStructureMap = rootStructureMap;
    }

    public Map<String, String> getOrderStructureMap() {
        return orderStructureMap;
    }

    public void setOrderStructureMap(Map<String, String> orderStructureMap) {
        this.orderStructureMap = orderStructureMap;
    }

    public Map<String, String> getItemStructureMap() {
        return itemStructureMap;
    }

    public void setItemStructureMap(Map<String, String> itemStructureMap) {
        this.itemStructureMap = itemStructureMap;
    }

    public Anonymous getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Anonymous anonymous) {
        this.anonymous = anonymous;
    }

    public CouponCard getCouponCard() {
        return couponCard;
    }

    public void setCouponCard(CouponCard couponCard) {
        this.couponCard = couponCard;
    }
}
