package com.self.servicegroup;

public interface Constants {
    //配置spring.cloud.nacos.discovery.metadata里的服务分组key
    enum MetadataKey {
        SERVICE_GROUP("service-group"), NORMAL_SERVER_IP_REG("normalServerIpReg");
        private String key;

        MetadataKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    //请求头里的服务分组key
    String SERVICE_GROUP_HEADER_KEY = "serviceGroup";

}
