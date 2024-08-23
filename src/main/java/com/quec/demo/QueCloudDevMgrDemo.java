package com.quec.demo;

import com.alibaba.fastjson.JSONObject;
import com.quec.client.MgrClient;
import com.quec.config.InitClientProfile;
import com.quec.model.BasicResultResponse;
import com.quec.model.device.request.DeviceBasicRequest;
import com.quec.model.device.request.DeviceDataHistoryRequest;
import com.quec.model.device.request.DeviceEventDataRequest;
import com.quec.model.device.request.DeviceGatewayOnlineSublistRequest;
import com.quec.model.device.request.DeviceGatewayProductRequest;
import com.quec.model.device.request.DeviceGatewaySublistRequest;
import com.quec.model.device.request.DeviceGatewayUnbindRequest;
import com.quec.model.device.request.DeviceListRequest;
import com.quec.model.device.response.DeviceDataHistoryResponse;
import com.quec.model.device.response.DeviceDetailResponse;
import com.quec.model.device.response.DeviceEventDataResponse;
import com.quec.model.device.response.DeviceGatewayOnlineSublistResponse;
import com.quec.model.device.response.DeviceGatewayProductResponse;
import com.quec.model.device.response.DeviceGatewaySublistResponse;
import com.quec.model.device.response.DeviceGatewayUnbindResponse;
import com.quec.model.device.response.DeviceListResponse;
import com.quec.model.device.response.DeviceLocationLatestResponse;
import com.quec.model.device.response.DeviceResourceResponse;
import com.quec.model.product.request.DetailProductRequest;
import com.quec.model.product.request.ProductListRequest;
import com.quec.model.product.response.ProductInfoResponse;
import com.quec.model.product.response.ProductListResponse;
import com.quec.model.queue.request.*;
import com.quec.model.queue.response.QueueCreateResponse;
import com.quec.model.queue.response.QueueDetailResponse;
import com.quec.model.queue.response.QueueListResponse;
import com.quec.model.queue.response.SubscribeCreateResponse;
import com.quec.model.queue.response.SubscribeDetailResponse;
import com.quec.model.queue.response.SubscribeListResponse;
import com.quec.model.shadow.request.DeviceDmReadDataRequest;
import com.quec.model.shadow.request.DeviceRawSendDataRequest;
import com.quec.model.shadow.request.DeviceRawSendDataRequestbody;
import com.quec.model.shadow.response.DeviceDmReadDataResponse;
import com.quec.model.tsl.request.ObtainTslTslJsonRequest;
import com.quec.model.tsl.response.ObtainTslTslJsonResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class QueCloudDevMgrDemo {

    public void mgrDemo() throws Exception {
        // accessKey和accessSecre是DMP平台用户创建accessKey和accessSecret
        InitClientProfile initClientProfile = new InitClientProfile(
                "${accessKey}",
                "${accessSecret}",
                "${endpoint}");
        // 获取MgrClient对象。建议可以使用单例模式。此对象中包含项目、产品、设备相关的sdk
        MgrClient mgrClient = new MgrClient(initClientProfile);

        // 获取产品详情
        DetailProductRequest detailProductRequest = new DetailProductRequest("${productKey}");
        ProductInfoResponse productInfoResponse = mgrClient.getProductDetail(detailProductRequest);
        log.info("获取产品详情返回结果:{}", JSONObject.toJSONString(productInfoResponse));

        // 获取产品列表
        ProductListRequest productListRequest = new ProductListRequest();
        ProductListResponse productListResponse = mgrClient.getProductList(productListRequest);
        log.info("获取产品列表返回结果:{}", JSONObject.toJSONString(productListResponse));

        // 获取物模型
        ObtainTslTslJsonRequest obtainTslTslJsonRequest = new ObtainTslTslJsonRequest("${productKey}");
        ObtainTslTslJsonResponse obtainTslTslJsonResponse = mgrClient.getTslJson(obtainTslTslJsonRequest);
        log.info("获取物模型返回结果:{}", JSONObject.toJSONString(obtainTslTslJsonResponse));

        // 获取设备列表
        DeviceListRequest deviceListRequest = new DeviceListRequest("${productKey}");
        DeviceListResponse deviceListResponse = mgrClient.getDeviceList(deviceListRequest);
        log.info("获取设备列表返回结果:{}", JSONObject.toJSONString(deviceListResponse));

        // 获取设备详情
        DeviceBasicRequest deviceBasicRequest = new DeviceBasicRequest("${productKey}","${deviceKey}");
        DeviceDetailResponse deviceDetailResponse = mgrClient.getDeviceDetail(deviceBasicRequest);
        log.info("获取设备详情返回结果:{}", JSONObject.toJSONString(deviceDetailResponse));

        // 获取子设备所属网关产品信息
        DeviceGatewayProductRequest gatewayProductRequest = new DeviceGatewayProductRequest();
        gatewayProductRequest.setProductKey("${productKey}");
        gatewayProductRequest.setDeviceKey("${deviceKey}");
        DeviceGatewayProductResponse productResponse = mgrClient.getDeviceGatewayProduct(gatewayProductRequest);
        log.info("获取子设备所属网关产品信息返回结果:{}", JSONObject.toJSONString(productResponse));

        // 查询网关设备关联的子设备列表
        DeviceGatewaySublistRequest gatewaySublistRequest = new DeviceGatewaySublistRequest();
        gatewaySublistRequest.setProductKey("${productKey}");
        gatewaySublistRequest.setDeviceKey("${deviceKey}");
        DeviceGatewaySublistResponse gatewaySublistResponse = mgrClient.getGatewayDeviceSublist(gatewaySublistRequest);
        log.info("查询网关设备关联的子设备列表返回结果:{}", JSONObject.toJSONString(gatewaySublistResponse));

        // 查询网关设备下处于连接状态的子设备
        DeviceGatewayOnlineSublistRequest onlineSublistRequest = new DeviceGatewayOnlineSublistRequest();
        onlineSublistRequest.setProductKey("${productKey}");
        onlineSublistRequest.setDeviceKey("${deviceKey}");
        DeviceGatewayOnlineSublistResponse onlineSublistResponse = mgrClient.getGatewayOnlineDeviceSublist(onlineSublistRequest);
        log.info("查询网关设备下处于连接状态的子设备列表返回结果:{}", JSONObject.toJSONString(onlineSublistResponse));

        // 网关子设备解绑
        DeviceGatewayUnbindRequest gatewayUnbindRequest = new DeviceGatewayUnbindRequest();
        gatewayUnbindRequest.setProductKey("${productKey}");
        gatewayUnbindRequest.setDeviceKey("${deviceKey}");
        DeviceGatewayUnbindResponse unbindResponse = mgrClient.gatewayDeviceUnbind(gatewayUnbindRequest);
        log.info("根据子设备pk&dk解绑和网关的关联关系返回结果:{}", JSONObject.toJSONString(unbindResponse));

        // 获取设备资源
        DeviceBasicRequest deviceBasicRequest2 = new DeviceBasicRequest("${productKey}","${deviceKey}");
        DeviceResourceResponse deviceResourceResponse = mgrClient.deviceResource(deviceBasicRequest2);
        log.info("获取设备资源返回结果:{}", JSONObject.toJSONString(deviceResourceResponse));

        /**
         * 设备发送下行数据.data为发送下行数据的具体内容.operate=GET,数据格式为"[“key1","key2",…]"(key为物模型标识符)。operate=DOWN,数据格式为”[{key1:value1},{key2:value2}]"(key为物模型标识符).
         * 示例:
         * 属性bool/int/float/double/enum/date/text
         * "[{\"key\":\"value\"}]"
         * 属性array
         * "[{\"key\":[{\"id\":\"value1\"},{\"id\":\"value2\"}]}]"（id为0）
         * 属性struct
         * "[{\"key\":[{\"key1\":\"value1\"},{\"key2\":\"value2\"}]}]"
         * 属性array含有struct
         * "[{\"key\":[{\"id\":[{\"key1\":\"value1\"}]},{\"id\":[{\"key2\":\"value2\"}]}]}]"（id为0）
         * 服务调用bool/int/float/double/enum/date/text
         * "[{\"key\":[{\"key1\":\"value1\"},{\"key2\":\"value2\"},{\"key3\":\"value3\"}]}]"
         * 服务调用array
         * "[{\"key\":[{\"key1\":[{\"id\":\"value1\"},{\"id\":\"value1\"}]}]}]"（id为0）
         * 服务调用struct
         * "[{\"key\":[{\"key1\":[{\"key2\":\"value2\"},{\"key3\":\"value3\"}]}]}]"
         * 服务调用array，且array含有struct
         * "[{\"key\":[{\"key1\":[{\"id\":[{\"key2\":\"value2\"}]},{\"id\":[{\"key3\":\"value3\"}]}]}]}]"(id固定为0)
         */
        //设备读取物模型属性数据
        //设备读取物模型属性数据.data为发送下行数据的具体内容.数据格式为"["key1","key2",…]"(key为物模型标识符).
        List<String> strings = new ArrayList<>();
        strings.add("${deviceKey}");
        DeviceDmReadDataRequest deviceDmReadDataRequest = new DeviceDmReadDataRequest(strings,"${productKey1}","${data}");
        DeviceDmReadDataResponse deviceDmReadDataResponse = mgrClient.deviceDmReadData(deviceDmReadDataRequest);
        log.info("设备读取物模型属性数据返回结果:{}", JSONObject.toJSONString(deviceDmReadDataResponse));

        //设备发送下行物模型数据
        /**data数据格式为"[{key1:value1},{key2:value2}]"(key为物模型标识符).
         示例:
         属性bool/int/float/double/enum/date/text
         "[{\"key\":\"value\"}]"
         属性array
         "[{\"key\":[{\"id\":\"value1\"},{\"id\":\"value2\"}]}]"（id为0）
         属性struct
         "[{\"key\":[{\"key1\":\"value1\"},{\"key2\":\"value2\"}]}]"
         属性array含有struct
         "[{\"key\":[{\"id\":[{\"key1\":\"value1\"}]},{\"id\":[{\"key2\":\"value2\"}]}]}]"（id为0）
         服务调用bool/int/float/double/enum/date/text
         "[{\"key\":[{\"key1\":\"value1\"},{\"key2\":\"value2\"},{\"key3\":\"value3\"}]}]"
         服务调用array
         "[{\"key\":[{\"key1\":[{\"id\":\"value1\"},{\"id\":\"value1\"}]}]}]"（id为0）
         服务调用struct
         "[{\"key\":[{\"key1\":[{\"key2\":\"value2\"},{\"key3\":\"value3\"}]}]}]"
         服务调用array，且array含有struct
         "[{\"key\":[{\"key1\":[{\"id\":[{\"key2\":\"value2\"}]},{\"id\":[{\"key3\":\"value3\"}]}]}]}]"(id固定为0)*/
        List<String> list = new ArrayList<>();
        list.add("${deviceKey}");
        DeviceDmReadDataRequest deviceDmReadDataRequest1 = new DeviceDmReadDataRequest(list,"${productKey}","${data}");
        DeviceDmReadDataResponse basicResultResponse = mgrClient.deviceDmWriteData(deviceDmReadDataRequest1);
        log.info("设备发送下行物模型数据返回结果:{}", JSONObject.toJSONString(basicResultResponse));

        //设备发送下行物模型服务数据
        /**
         * data数据格式为"[{key1:value1},{key2:value2}]"(key为物模型标识符).
         * 示例:
         * 服务调用bool/int/float/double/enum/date/text
         * "[{\"key\":[{\"key1\":\"value1\"},{\"key2\":\"value2\"},{\"key3\":\"value3\"}]}]"
         * 服务调用array
         * "[{\"key\":[{\"key1\":[{\"id\":\"value1\"},{\"id\":\"value1\"}]}]}]"（id为0）
         * 服务调用struct
         * "[{\"key\":[{\"key1\":[{\"key2\":\"value2\"},{\"key3\":\"value3\"}]}]}]"
         * 服务调用array，且array含有struct
         * "[{\"key\":[{\"key1\":[{\"id\":[{\"key2\":\"value2\"}]},{\"id\":[{\"key3\":\"value3\"}]}]}]}]"(id固定为0)*/
        List<String> str = new ArrayList<>();
        str.add("${deviceKey}");
        DeviceDmReadDataRequest deviceDmReadDataRequest2 = new DeviceDmReadDataRequest(str,"${productKey}","${data}");
        DeviceDmReadDataResponse deviceDmReadDataResponse2 = mgrClient.deviceDmSendServiceData(deviceDmReadDataRequest2);
        log.info("设备发送下行物模型服务数据返回结果:{}", JSONObject.toJSONString(deviceDmReadDataResponse2));

        //设备发送下行透传数据, data为发送下行数据的具体内容.
        DeviceRawSendDataRequest deviceRawSendDataRequest = new DeviceRawSendDataRequest();
        DeviceRawSendDataRequestbody b = new DeviceRawSendDataRequestbody("${productKey}","{deviceKey}");
        List<DeviceRawSendDataRequestbody> string = new ArrayList<>();
        string.add(b);
        deviceRawSendDataRequest.setData("${data}");
        deviceRawSendDataRequest.setEncode("Text");
        deviceRawSendDataRequest.setDevices(string);
        DeviceDmReadDataResponse deviceDmReadDataResponse1 = mgrClient.deviceRawSendData(deviceRawSendDataRequest);
        log.info("设备发送下行透传数据返回结果:{}", JSONObject.toJSONString(deviceDmReadDataResponse1));

        // 获取设备历史上下行信息查询
        DeviceDataHistoryRequest deviceDataHistoryRequest = new DeviceDataHistoryRequest("${productKey}","${deviceKey}");
        DeviceDataHistoryResponse deviceDataHistoryResponse = mgrClient.getDeviceDataHistory(deviceDataHistoryRequest);
        log.info("获取设备历史上下行信息查询返回结果:{}", JSONObject.toJSONString(deviceDataHistoryResponse));

        // 获取设备事件日志信息查询
        DeviceEventDataRequest deviceEventDataRequest = new DeviceEventDataRequest("${productKey}","${deviceKey}");
        DeviceEventDataResponse deviceEventDataResponse = mgrClient.getDeviceEventData(deviceEventDataRequest);
        log.info("获取设备事件日志信息查询返回结果:{}", JSONObject.toJSONString(deviceEventDataResponse));

        // 获取设备最新定位数据查询
        DeviceBasicRequest deviceBasicRequest1 = new DeviceBasicRequest("${productKey}","${deviceKey}");
        DeviceLocationLatestResponse deviceLocationLatestResponse = mgrClient.getDeviceLocationLatest(deviceBasicRequest1);
        log.info("获取设备最新定位数据查询返回结果:{}", JSONObject.toJSONString(deviceLocationLatestResponse));

        // 队列列表
        QueueListRequest queueListRequest = new QueueListRequest();
        QueueListResponse queueListResponse = mgrClient.getQueueList(queueListRequest);
        log.info("队列列表返回结果:{}", JSONObject.toJSONString(queueListResponse));

        // 创建队列
        QueueCreateRequest createRequest = new QueueCreateRequest("${queueName}");
        QueueCreateResponse queceCreateResponse = mgrClient.createQueue(createRequest);
        log.info("创建队列返回结果:{}", JSONObject.toJSONString(queceCreateResponse));

        // 队列详情
        QueueDetailRequest queceDetailRequest = new QueueDetailRequest("${queueId}");
        QueueDetailResponse queceDetailResponse = mgrClient.getQueueDetail(queceDetailRequest);
        log.info("队列详情返回结果:{}", JSONObject.toJSONString(queceDetailResponse));

        // 删除队列
        QueueDetailRequest queceDetailRequest1 = new QueueDetailRequest("${queueId}");
        BasicResultResponse basicResultResponse1 = mgrClient.deleteQueue(queceDetailRequest1);
        log.info("删除队列返回结果:{}", JSONObject.toJSONString(basicResultResponse1));

        // 清理队列
//        QueueClearRequest clearRequest = new QueueClearRequest();
//        clearRequest.setQueueId("${queueId}");
//        BasicResultResponse basicResultResponse2 = mgrClient.clearQueue(clearRequest);
//        log.info("清理队列返回结果:{}", JSONObject.toJSONString(basicResultResponse2));

        // 创建订阅
        List<Integer> msgTypes = new ArrayList<Integer>();
        /**
         *  msgTypes 消息类型：
         *
         *  透传产品支持以下类型：
         *  1-设备上下线事件
         *  2-设备和模组状态
         *  3-设备上行数据
         *  4-设备下行数据
         *  5-设备命令响应
         *  11-设备定位下行信息
         *  12-设备定位原始信息
         *  13-设备定位信息
         *  14-设备绑定变更信息
         *  15-设备信息变更
         *
         *  物模型产品支持以下类型：
         *  1-设备上下线事件
         *  2-设备和模组状态
         *  5-设备命令响应
         *  6-物模型属性信息
         *  7-物模型事件上报-信息
         *  8-物模型事件上报-告警
         *  9-物模型事件上报-故障
         *  10-物模型服务调用日志
         *  11-设备定位下行信息
         *  12-设备定位原始信息
         *  13-设备定位信息
         *  14-设备绑定变更信息
         *  15-设备信息变更
         */
        msgTypes.add(1);
        // dataLevel 1产品 2设备
        SubscribeCreateRequest subscribeCreateRequest = new SubscribeCreateRequest("${subscribeName}",1,"${productKey}","${queueName}",msgTypes);
        SubscribeCreateResponse subscribeCreateResponse = mgrClient.createSubscribe(subscribeCreateRequest);
        log.info("创建订阅返回结果:{}", JSONObject.toJSONString(subscribeCreateResponse));

        //创建SaaS用户订阅
        List<Integer> msgTypes_SaaSUser = new ArrayList<Integer>();
        // msgTypes 消息类型：101-产品信息变更 102-设备信息变更 103-物模型发布信息变更 104-产品授权信息
        msgTypes_SaaSUser.add(101);
        SaasSubscribeCreateRequest saasSubscribeCreateRequest = new SaasSubscribeCreateRequest("${subscribeName}","${queueName}", msgTypes_SaaSUser);
        SubscribeCreateResponse saaSUser_SubscribeCreateResponse = mgrClient.createSaaSSubscribe(saasSubscribeCreateRequest);
        log.info("创建SaaS用户订阅:{}", JSONObject.toJSONString(saaSUser_SubscribeCreateResponse));

        // 订阅列表
        SubscribeListRequest subscribeListRequest = new SubscribeListRequest();
        SubscribeListResponse subscribeListResponse = mgrClient.getSubscribeList(subscribeListRequest);
        log.info("订阅列表返回结果:{}", JSONObject.toJSONString(subscribeListResponse));

        // 订阅详情
        SubscribeIdRequest subscribeIdRequest = new SubscribeIdRequest("${subscribeId}");
        SubscribeDetailResponse subscribeDetailResponse = mgrClient.getSubscribeDetail(subscribeIdRequest);
        log.info("订阅详情返回结果:{}", JSONObject.toJSONString(subscribeDetailResponse));

        // 启动订阅
        SubscribeIdRequest subscribeIdRequest1 = new SubscribeIdRequest("${subscribeId}");
        BasicResultResponse startSubscribeResponse = mgrClient.startSubscribe(subscribeIdRequest1);
        log.info("启动订阅返回结果:{}", JSONObject.toJSONString(startSubscribeResponse));

        // 删除订阅
        SubscribeIdRequest subscribeIdRequest3 = new SubscribeIdRequest("${subscribeId}");
        BasicResultResponse basicResultResponse3 = mgrClient.deleteSubscribe(subscribeIdRequest3);
        log.info("删除订阅返回结果:{}", JSONObject.toJSONString(basicResultResponse3));

        // 停止订阅
        SubscribeIdRequest subscribeIdRequest2 = new SubscribeIdRequest("${subscribeId}");
        BasicResultResponse basicResultResponse2 = mgrClient.stopSubscribe(subscribeIdRequest2);
        log.info("停止订阅返回结果:{}", JSONObject.toJSONString(basicResultResponse2));
    }

}
