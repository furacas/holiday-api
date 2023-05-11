# 节假日api

## 介绍
这是一个节假日查询的api服务，可以查询指定日期的节假日信息，也可以查询最近的一个节假日信息，也可以查询最近一年的节假日信息。

## 安装

### [推荐] docker
```shell
docker run -d --name holiday-api -p 8080:8080 furacas/holiday-api
```

## 接口文档

### 根据日期查询假期信息
接口地址: `/api/holiday/info/{date}`   
请求方式: `GET`

#### 请求参数
|  参数   | 含义  |
|  ----  | ----  |
| date  | 为查询的日期，非必填，如果不填代表当前日期  |
#### 返回参数
|  参数   | 含义  |
|  ----  | ----  |
| status  | 1: 工作日 2: 补班 3: 假期 4: 周末  |
| rest  | 查询的日期距离今天有多少天  |
| name  | 假期名称  |

#### 请求示例
http://127.0.0.1:8080/api/holiday/info/2022-1-1  
http://127.0.0.1:8080/api/holiday/info

#### 返回结果示例
```json
{
    "code": 0,
    "data": {
        "year": 2022,
        "month": 1,
        "day": 1,
        "week": 6,
        "status": 3,
        "name": "元旦",
        "rest": 9
    }
}
```


### 获取最近的一个假期信息
接口地址: `/api/holiday/next/holiday/info`   
请求方式: `GET`

#### 返回参数
|  参数   | 含义  |
|  ----  | ----  |
| status  | 1: 工作日 2: 补班 3: 假期 4: 周末  |
| rest  | 查询的日期距离今天有多少天  |
| name  | 假期名称  |

#### 请求示例
http://127.0.0.1:8080/api/holiday/next/holiday/info

#### 返回结果示例
```json
{
    "code": 0,
    "data": {
        "year": 2022,
        "month": 1,
        "day": 1,
        "week": 6,
        "status": 3,
        "name": "元旦",
        "rest": 9
    }
}
```

### 获取最近的一年的假期信息
接口地址: `/api/holiday/next/year/holiday/info`   
请求方式: `GET`

#### 返回参数
|  参数   | 含义  |
|  ----  | ----  |
| status  | 1: 工作日 2: 补班 3: 假期 4: 周末  |
| rest  | 查询的日期距离今天有多少天  |
| name  | 假期名称  |

#### 请求示例
http://127.0.0.1:8080/api/holiday/next/year/holiday/info

#### 返回结果示例
```json
{
    "code": 0,
    "data": [
        {
            "year": 2022,
            "month": 2,
            "day": 1,
            "week": 2,
            "status": 3,
            "name": "春节",
            "rest": 22
        },
        {
            "year": 2022,
            "month": 4,
            "day": 5,
            "week": 2,
            "status": 3,
            "name": "清明",
            "rest": 85
        },
        {
            "year": 2022,
            "month": 5,
            "day": 1,
            "week": 7,
            "status": 3,
            "name": "劳动节",
            "rest": 111
        },
        {
            "year": 2022,
            "month": 6,
            "day": 3,
            "week": 5,
            "status": 3,
            "name": "端午节",
            "rest": 144
        },
        {
            "year": 2022,
            "month": 9,
            "day": 10,
            "week": 6,
            "status": 3,
            "name": "中秋节",
            "rest": 243
        },
        {
            "year": 2022,
            "month": 10,
            "day": 1,
            "week": 6,
            "status": 3,
            "name": "国庆节",
            "rest": 264
        },
        {
            "year": 2023,
            "month": 1,
            "day": 1,
            "week": 7,
            "status": 4,
            "name": "元旦",
            "rest": 356
        }
    ]
}
```

## 打赏
如果觉得这个项目对你有帮助，可以请作者喝杯咖啡。

<table>
  <tr>
    <td align="center">
      <img src="https://www.furacas.com/img/alipay.jpeg" alt="支付宝" width="200">
    </td>
    <td align="center">
      <img src="https://www.furacas.com/img/wepay.jpeg" alt="微信" width="200">
    </td>
  </tr>
</table>