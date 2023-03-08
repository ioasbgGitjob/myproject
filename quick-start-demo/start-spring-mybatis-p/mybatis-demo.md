spring-boot  2.7.4  
jdk 17

包含内容:
1. 切库
2. 统一租户id
3. 自动填充字段

```sql

CREATE TABLE `test` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `age` int DEFAULT NULL,
  `tenant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_name_age` (`name`,`age`)
) ENGINE=InnoDB AUTO_INCREMENT=1633423450733502466 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

```