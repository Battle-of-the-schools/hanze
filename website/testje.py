import time
import ttn

app_id = "zender"
access_key = "ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo"



def uplink_callback(msg, client):
  print("Received uplink from ",
   msg.dev_id)
  print(msg)

handler = ttn.HandlerClient(app_id,
 access_key)

# using mqtt client
mqtt_client = handler.data()
mqtt_client.set_uplink_callback(uplink_callback)
mqtt_client.connect()
time.sleep(60)
mqtt_client.close()

# using application manager client
app_client =  handler.application()
my_app = app_client.get()
print(my_app)
my_devices = app_client.devices()
print(my_devices)



# {"created":"@1541034923.603480430",
#     "description":"Failed to create subchannel",
#     "file":"src/core/ext/filters/client_channel/client_channel.cc",
#     "file_line":2721,
#     "referenced_errors":[
#         {"created":"@1541034923.603476740",
#         "description":"Pick Cancelled",
#         "file":"src/core/ext/filters/client_channel/lb_policy/pick_first/pick_first.cc",
#         "file_line":241,
#         "referenced_errors":[
#             {"created":"@1541034923.603295802",
#             "description":"Connect Failed",
#             "file":"src/core/ext/filters/client_channel/subchannel.cc",
#             "file_line":689,
#             "grpc_status":14,
#             "referenced_errors":[
#                 {"created":"@1541034923.603212485",
#                 "description":"Failed to connect to remote host: FD Shutdown",
#                 "file":"src/core/lib/iomgr/lockfree_event.cc",
#                 "file_line":194,
#                 "os_error":"Timeout occurred",
#                 "referenced_errors":[
#                     {"created":"@1541034923.603198456",
#                     "description":"connect() timed out",
#                     "file":"src/core/lib/iomgr/tcp_client_posix.cc",
#                     "file_line":117}],
#                     "target_address":"ipv4:52.178.215.58:1900"}]}]}]}
