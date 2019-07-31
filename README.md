# aidataprobe2
maidian

配置
kafka_brokers = ip1:9092,ip2:9092,ip3:9092
is_running_httpserver = true
http_port = 18010
is_order_server = false
is_running_tcpserver = true
tcp_port = 21001
tcp_request_module = 0x10000030
tracer_port = 34333
virtual_service_port = 11013
service_time_threshold = 360
sys_error_monitor = https://oapi.dingtalk.com/robot/send?access_token=8732bbd4866ad6051b8e8428e5cdcaf58959cca9b4d4a0d43649545260c89bcf
sys_service_monitor = https://oapi.dingtalk.com/robot/send?access_token=e900a731e4fac1dd877ca435fef4d64f518bd07052511035e5d1774d4d40a46f
dingding_switch = 0
