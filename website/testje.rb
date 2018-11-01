# Receive and print an uplink message; return a downlink message
# Usage: TTN_REGION=eu TTN_APP_ID=test TTN_ACCESS_KEY=ttn-account-v2.XXXXXXX ttn_up_down.rb

require 'rubygems'
require 'mqtt'
require 'json'
require 'base64'

ttn_app_id=ENV['70B3D57ED0013EDC']
ttn_app_access_key=ENV['ttn-account-v2.609820B02340A427E7E7D47BCB093A22']
ttn_region=ENV['eu']

ttn_dev_id="zender"

# connect:
MQTT::Client.connect(
  :host     => "#{ttn_region}.thethings.network",
  :port     => 1883,
  :username => ttn_app_id,
  :password => ttn_app_access_key
) do |c|
  
  # subscribe to uplink:
  c.get("#{ttn_app_id}/devices/#{ttn_dev_id}/up") do |topic, message|
    
    # do stuff with the uplink:
    data = JSON.parse message
    payload_up = Base64.decode64(data["payload_raw"]).unpack("C*")
    puts "#{topic}: #{payload_up}"
    
    # send downlink:
    payload_down = [0x01, 0x02, 0x03, 0x04]
    downlink = {
      :payload_raw => Base64.encode64(payload_down.pack("C*")).strip,
      :port        => 1
    }
    c.publish("#{ttn_app_id}/devices/#{ttn_dev_id}/down", downlink.to_json)

  end
end



# const char *appEui = "70B3D57ED0013EDC";
# const char *appKey = "609820B02340A427E7E7D47BCB093A22";


{"app_id":"thingtest",
  "dev_id":"zender",
  "hardware_serial":"0004A30B001BC0BA",
  "port":1,
  "counter":0,
  "is_retry":true,
  "payload_raw":"SEVMUAA=",
  "payload_fields":{"message":"HELP"},
"metadata":{"time":"2018-10-31T19:41:55.838464622Z",
  "frequency":867.9,
  "modulation":"LORA",
  "data_rate":"SF7BW125",
  "coding_rate":"4/5",
  "gateways":[{"gtw_id":"eui-b827ebfffef35e71",
    "timestamp":1099458300,
    "time":"2018-10-31T19:41:55.819154Z",
    "channel":7,
    "rssi":-118,
    "snr":0.5,
    "rf_chain":0,
    "latitude":53.20393,
    "longitude":6.52751,
    "altitude":8}]},
  "downlink_url":"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo"}


# {"app_id":"thingtest",
#   "dev_id":"zender",
#   "hardware_serial":"0004A30B001BC0BA",
#   "port":1,
  "counter":1,
  "payload_raw":"bG9sAA==",
  "payload_fields":{"message":"lol\u0000"},
"metadata":{"time":"2018-10-31T23:41:25.80991632Z",
  "frequency":867.5,
  # "modulation":"LORA",
  # "data_rate":"SF7BW125",
  # "coding_rate":"4/5",
  # "gateways":[{"gtw_id":"eui-b827ebfffef35e71",
    "timestamp":2584530524,
    "time":"2018-10-31T23:41:25.789563Z",
    "channel":5,
    "rssi":-119,
    "snr":-5.8,
    # "rf_chain":0,
    # "latitude":53.20393,
    # "longitude":6.52751,
    # "altitude":8}]},
"downlink_url":"https://integrations.thethingsnetwork.org/ttn-eu/api/v2/down/thingtest/posttest?key=ttn-account-v2.cDXMIDC5jZJrJuX1aKbmpFUtWeMDg9XK7z4VMwnlLQo"}